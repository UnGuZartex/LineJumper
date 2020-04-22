package LineJumper;

import Images.ImageLibrary;

import java.awt.*;

public class LineWorldPainter {

    private static int tilesOnScreen = 5;
    private int tileWidth, tileHeight;
    private Color backgroundColor = new Color(84, 214, 240);

    public void paint(Graphics g, ImageLibrary library, boolean[] line, Player player) {
        calculateWorldProperties(g, library);
        drawBackground(g);
        drawWorld(g, library, line, player);
        drawIcons(g, library, player);
    }

    private void calculateWorldProperties(Graphics g, ImageLibrary library) {
        Image referenceImage = library.getImage("grass");
        int imageHeight = referenceImage.getHeight(null);
        int imageWidth = referenceImage.getWidth(null);

        double tileSizeMultiplier = Math.min(g.getClipBounds().getWidth() / (double) (imageWidth * tilesOnScreen),
                g.getClipBounds().getHeight() / (double) (imageHeight * 2));
        tileWidth = (int) (imageWidth * tileSizeMultiplier);
        tileHeight = (int) (imageHeight * tileSizeMultiplier);
    }

    private void drawBackground(Graphics g) {
        g.setColor(backgroundColor);
        Rectangle clipRect = g.getClipBounds();
        g.fillRect(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
        g.setColor(Color.black);
    }

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

    private void drawIcons(Graphics g, ImageLibrary library, Player player) {
        int xDelta = (int) (tileWidth * 0.4);
        int yDelta = 50;
        int realIconSize = library.getImage("dirt").getWidth(null);
        int gameIconSize = (int) ((Math.ceil(tileWidth / (double) realIconSize) * realIconSize) * 0.5);
        int xPos = (int) (g.getClipBounds().getX() + xDelta);
        int yPos = (int) (g.getClipBounds().getY() + yDelta);

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
