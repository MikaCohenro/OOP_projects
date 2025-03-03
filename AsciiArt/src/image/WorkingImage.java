package image;

import ascii_art.Constants;

import java.awt.*;
import java.io.IOException;

/**
 * WorkingImage extends the functionality of the Image class to specifically cater to the needs of
 * converting images into ASCII art. It provides methods to pad the image to dimensions that are a
 * power of 2, partition the image into sub-images, and calculate the brightness of each sub-image.
 */
public class WorkingImage extends Image {
    private static final double RED_SCALE = 0.2126;
    private static final double GREEN_SCALE = 0.7152;
    private static final double BLUE_SCALE = 0.0722;
    private final Color[][] pixelArray;
    private static final int SPLIT_GAP = 2;
    private static final int LOG_BASE = 2;
    private static final int WHITE_PIXEL_VAL = 255;
    private final int width;
    private final int height;

    /**
     * Constructs a WorkingImage from a file, initializing its dimensions to the nearest power of 2.
     *
     * @param filename The path to the image file.
     * @throws IOException If there is an error reading the file.
     */
    public WorkingImage(String filename) throws IOException {
        super(filename);
        this.width = calculateDimension(super.getWidth());
        this.height = calculateDimension(super.getHeight());
        this.pixelArray = new Color[height][];
        for (int row = 0; row < height; row++) {
            pixelArray[row] = new Color[width];
        }
    }

    /**
     * Constructs a WorkingImage from a pixel array, with specified width and height, adjusting the
     * dimensions to the nearest power of 2.
     *
     * @param pixelArray The array of Color objects representing the image.
     * @param width      The width of the image.
     * @param height     The height of the image.
     */
    public WorkingImage(Color[][] pixelArray, int width, int height) {
        super(pixelArray, width, height);
        this.width = calculateDimension(width);
        this.height = calculateDimension(height);
        this.pixelArray = pixelArray;
    }

    /**
     * Pads the image with white pixels to ensure that its dimensions are powers of 2, facilitating
     * uniform sub-image division.
     */
    public void paddingImage() {
        int heightGap = (height - super.getHeight()) / SPLIT_GAP;
        int widthGap = (width - super.getWidth()) / SPLIT_GAP;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < heightGap || j < widthGap || i >= height - heightGap || j >= width - widthGap) {
                    pixelArray[i][j] = new Color(Constants.WHITE_COLOR,
                                                    Constants.WHITE_COLOR,Constants.WHITE_COLOR);
                } else {
                    pixelArray[i][j] = super.getPixel(i - heightGap, j - widthGap);
                }
            }
        }
    }

    /**
     * Gets the width of the image.
     *
     * @return The width of the image in pixels.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image.
     *
     * @return The height of the image in pixels.
     */
    @Override
    public int getHeight() {
        return height;
    }
    /**
     * Divides the image into smaller sub-images based on the specified resolution.
     *
     * @param resolution The number of divisions along each dimension.
     * @return A 2D array of WorkingImage objects representing the sub-images.
     */
    public WorkingImage[][] toSubImages(int resolution) {
        int subImageSize = width / resolution;
        int heightResolution = height / subImageSize;
        WorkingImage[][] subImagesArray = new WorkingImage[heightResolution][];
        for (int i = 0; i < heightResolution; i++) {
            subImagesArray[i] = new WorkingImage[resolution];
        }
        for (int i = 0; i < heightResolution; i++) {
            for (int j = 0; j < resolution; j++) {
                createSubImage(i, j, subImageSize, subImagesArray);
            }
        }
        return subImagesArray;
    }

    private void createSubImage(int row, int col, int subImageSize, WorkingImage[][] subImagesArray) {
        Color[][] subImagePixels = new Color[subImageSize][];
        for (int i = 0; i < subImageSize; i++) {
            subImagePixels[i] = new Color[subImageSize];
        }
        for (int i = 0; i < subImageSize; i++) {
            System.arraycopy(pixelArray[row * subImageSize + i], col * subImageSize, subImagePixels[i],
                    0, subImageSize);
        }
        subImagesArray[row][col] = new WorkingImage(subImagePixels, subImageSize, subImageSize);
    }

    /**
     * Calculates the average brightness of the image based on a weighted grayscale conversion.
     *
     * @return The average brightness of the image.
     */
    public double brightnessCalculation() {
        double grays = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = pixelArray[i][j];
                double gray = color.getRed() * RED_SCALE +
                        color.getGreen() * GREEN_SCALE +
                        color.getBlue() * BLUE_SCALE;
                grays += gray;
            }
        }
        return grays / (WHITE_PIXEL_VAL * height * width);
    }


    /**
     * Helper method to calculate the nearest power of 2 for a given dimension.
     *
     * @param dimension The original dimension (width or height).
     * @return The adjusted dimension, rounded up to the nearest power of 2.
     */
    private int calculateDimension(int dimension) {
        return (int) Math.pow(2, (int) Math.ceil(Math.log(dimension) / Math.log(LOG_BASE)));
    }
}