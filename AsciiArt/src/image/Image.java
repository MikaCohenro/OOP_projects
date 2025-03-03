package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
public class Image {
    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    /**
     * Constructs an `Image` object from a given image file.
     *
     * @param filename The path to the image file.
     * @throws IOException If an error occurs while reading the image file.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j]=new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an `Image` object from a given 2D array of colors, width, and height.
     *
     * @param pixelArray A 2D array of `Color` objects representing the image pixels.
     * @param width The width of the image in pixels.
     * @param height The height of the image in pixels.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the image.
     *
     * @return The width of the image in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image.
     *
     * @return The height of the image in pixels.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the color of a specific pixel in the image.
     *
     * @param x The X coordinate of the pixel (0-based indexing).
     * @param y The Y coordinate of the pixel (0-based indexing).
     * @return The `Color` object representing the pixel at the specified coordinates.
     * @throws IndexOutOfBoundsException If the provided coordinates are outside the image boundaries.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the image to a specified file name with the JPEG format.
     *
     * @param fileName The desired name for the output image file (excluding the ".jpeg" extension).
     */
    public void saveImage(String fileName){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName+".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
