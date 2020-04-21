package LineJumper;

import Images.ImageLibrary;

import java.awt.*;

public class LineWorldPainter {

    private static int tilesOnScreen = 5;
    private int tileWidth, tileHeight;

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
        g.setColor(Color.blue);
        Rectangle clipRect = g.getClipBounds();
        g.drawRect(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
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
        int xDelta = 50;
        int yDelta = 50;
        int realIconSize = library.getImage("dirt").getWidth(null);
        int gameIconSize = (int) ((tileWidth / realIconSize * realIconSize) * 0.5);
        int xPos = (int) (g.getClipBounds().getX() + xDelta);
        int yPos = (int) (g.getClipBounds().getY() + yDelta);

        // Draw dirt icon and amount of dirt left
        g.drawImage(library.getImage("dirt"), xPos, yPos, gameIconSize, gameIconSize, null);

        g.drawString("x " + player.getAmountOfDirt(), xPos + gameIconSize + 10, yPos);

        // Draw jump icon and jump length
        g.drawImage(library.getImage("jumpIcon"), xPos,
                (int) (yPos + gameIconSize * 1.5), gameIconSize, gameIconSize, null);
    }
}
