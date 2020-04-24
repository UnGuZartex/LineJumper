package LineJumper;

import Images.ImageLibrary;

import java.awt.*;

/**
 * A class to paint the line world.
 *
 * @author Alpha-team
 */
public class LineWorldPainter {

    /**
     * Variables referring to the title width and height.
     */
    private int tileWidth, tileHeight;
    /**
     * Variable referring to the colour of the background.
     */
    private final Color backgroundColor = new Color(84, 214, 240);

    private static final int TILES_ON_SCREEN = 5;

    /**
     * Paint the the line world with given line and player.
     *
     * @param g The graphics object to paint with.
     * @param library The image library for images.
     * @param line The line to paint.
     * @param player The player to paint.
     *
     * @effect The world properties are calculated.
     * @effect The background is drawn.
     * @effect The world is drawn.
     * @effect The icons are drawn.
     */
    public void paint(Graphics g, ImageLibrary library, boolean[] line, Player player) {
        calculateWorldProperties(g, library);
        drawBackground(g);
        drawWorld(g, library, line, player);
        drawIcons(g, library, player);
    }

    /**
     * Calculate the world properties using the graphics object and image library.
     *
     * @param g The graphics to use to set the properties.
     * @param library The image library with images to base the properties on.
     *
     * @post The tile width and height is set such that everything of the game world
     *       will be visible and only TILES_ON_SCREEN will be on the screen.
     */
    private void calculateWorldProperties(Graphics g, ImageLibrary library) {
        Image referenceImage = library.getImage("grass");
        int imageHeight = referenceImage.getHeight(null);
        int imageWidth = referenceImage.getWidth(null);

        double tileSizeMultiplier = Math.min(g.getClipBounds().getWidth() / (double) (imageWidth * TILES_ON_SCREEN),
                g.getClipBounds().getHeight() / (double) (imageHeight * 2));
        tileWidth = (int) (imageWidth * tileSizeMultiplier);
        tileHeight = (int) (imageHeight * tileSizeMultiplier);
    }

    /**
     * Draw the background.
     *
     * @param g The graphics to draw with.
     *
     * @effect A rectangle is drawn in the background colour.
     * @effect The colour of the graphics is set to black.
     */
    private void drawBackground(Graphics g) {
        g.setColor(backgroundColor);
        Rectangle clipRect = g.getClipBounds();
        g.fillRect(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
        g.setColor(Color.black);
    }

    /**
     * Draw the game world.
     *
     * @param g The graphics to draw with.
     * @param library The images to use.
     * @param line The line to draw.
     * @param player The player to draw.
     *
     * @effect The player is drawn on the correct position, either falling or standing, depending
     *         on its position on the line.
     * @effect The rest of the line is drawn, grass and pits depending on whether or not
     *         the given line has a walkable position and a goal at the end of the line.
     *         There are only TILES_ON_SCREEN tiles visible.
     */
    private void drawWorld(Graphics g, ImageLibrary library, boolean[] line, Player player) {

        // Draw player
        if (player.getPosition() < line.length && !line[player.getPosition()]) {
            g.drawImage(library.getImage("player_falling"), (int) (g.getClipBounds().getX()),
                    (int) (g.getClipBounds().getY() + g.getClipBounds().getHeight() - tileHeight), tileWidth, tileHeight, null);
        }
        else {
            g.drawImage(library.getImage("player"), (int) (g.getClipBounds().getX()),
                    (int) (g.getClipBounds().getY() + g.getClipBounds().getHeight() - tileHeight * 2), tileWidth, tileHeight, null);
        }

        // Draw rest of the world
        for (int i = 0; i < line.length; i++) {

            int distanceFromPlayer = i - Math.min(player.getPosition(), line.length - 1);
            Image tileImage = null;

            if (line[i]) {
                tileImage = library.getImage("grass");
            }
            else if (i > 0 && i < line.length) {
                if (line[i - 1] && !line[i + 1]) {
                    tileImage = library.getImage("pit_left");
                }
                else if (!line[i - 1] && line[i + 1]) {
                    tileImage = library.getImage("pit_right");
                }
                else if (line[i - 1] && line[i + 1]) {
                    tileImage = library.getImage("pit_full");
                }
            }

            // Draw tile
            g.drawImage(tileImage, (int) (g.getClipBounds().getX() + tileWidth * distanceFromPlayer),
                    (int) (g.getClipBounds().getY() + g.getClipBounds().getHeight() - tileHeight), tileWidth, tileHeight,null);

            // Draw goal
            if (i == line.length - 1) {
                int xValue = (int) (g.getClipBounds().getX() + tileWidth * distanceFromPlayer);
                g.drawImage(library.getImage("goal"), xValue,
                        (int) (g.getClipBounds().getY() + g.getClipBounds().getHeight() - tileHeight * 2), tileWidth, tileHeight,null);

                // Draw extra tile
                g.drawImage(library.getImage("pit_left"), xValue + tileWidth,
                        (int) (g.getClipBounds().getY() + g.getClipBounds().getHeight() - tileHeight), tileWidth, tileHeight,null);
            }
        }
    }

    /**
     * Draw the different icons on the screen.
     *
     * @param g The graphics to draw with.
     * @param library The images to use.
     * @param player The player of which the properties will be drawn.
     *
     * @effect The font of the given graphics is set to a bigger font.
     * @effect The amount of dirt is drawn with a dirt icon.
     * @effect The jump length is drawn with a jump icon.
     *
     * @post The font of the given graphics will be the same as when the
     *       graphics was given to this method.
     */
    private void drawIcons(Graphics g, ImageLibrary library, Player player) {
        int delta = (int) (tileWidth * 0.3);
        int realIconSize = library.getImage("dirt").getWidth(null);
        int gameIconSize = (int) ((Math.ceil(tileWidth / (double) realIconSize) * realIconSize) * 0.5);
        int xPos = (int) (g.getClipBounds().getX() + delta);
        int yPos = (int) (g.getClipBounds().getY() + delta);

        // Set bigger font
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);
        g.setFont(newFont);

        // Draw dirt icon and amount of dirt left
        g.drawImage(library.getImage("dirt"), xPos, yPos, gameIconSize, gameIconSize, null);
        g.drawString("x " + player.getAmountOfDirt(), xPos + gameIconSize + 10,
                yPos + (gameIconSize - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent());

        // Draw jump icon and jump length
        yPos = (int) (yPos + gameIconSize * 1.5);
        g.drawImage(library.getImage("jumpIcon"), xPos,
                yPos, gameIconSize, gameIconSize, null);
        g.drawString(String.valueOf(player.getPlayerJumpLength()), xPos + gameIconSize + 10,
                yPos + (gameIconSize - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent());

        g.setFont(currentFont);
    }
}
