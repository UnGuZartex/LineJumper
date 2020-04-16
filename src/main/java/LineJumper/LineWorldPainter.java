package LineJumper;

import Images.ImageLibrary;

import java.awt.*;

public class LineWorldPainter {

    private static int tilesOnScreen = 5;
    private static int playerTilePosition = 2;
    private double tileWidth, tileHeight;

    public void paint(Graphics g, ImageLibrary library, boolean[] line, Player player) {
        calculateWorldProperties(g, library);
        drawWorld(g, library, line, player);
    }

    private void calculateWorldProperties(Graphics g, ImageLibrary library) {
        Image referenceImage = library.getImage("grass");
        int imageHeight = referenceImage.getHeight(null);
        int imageWidth = referenceImage.getWidth(null);

        double tileSizeMultiplier = Math.min(g.getClipBounds().getWidth() / (double) (imageWidth * tilesOnScreen),
                g.getClipBounds().getHeight() / (double) (imageHeight * 2));
        tileWidth = imageWidth * tileSizeMultiplier;
        tileHeight = imageHeight * tileSizeMultiplier;
    }

    private void drawWorld(Graphics g, ImageLibrary library, boolean[] line, Player player) {

        // Draw player
        g.drawImage(library.getImage("player"), (int) (g.getClipBounds().getX() + tileWidth * playerTilePosition),
                (int) g.getClipBounds().getY(), null);

        // Draw rest of the world
        for (int i = 0; i < line.length; i++) {
            Image tileImage = line[i] ? library.getImage("grass") : library.getImage("pit");
            int distanceFromPlayer = player.getPosition() - i;

            g.drawImage(tileImage, (int) (g.getClipBounds().getX() + tileWidth * (playerTilePosition + distanceFromPlayer)),
                    (int) (g.getClipBounds().getY() + tileHeight),null);
        }
    }
}
