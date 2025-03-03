package ascii_art;

import image.Image;
import image.WorkingImage;
import image_char_matching.SubImgCharMatcher;


/**
 * The AsciiArtAlgorithm class is designed to convert an image into ASCII art by dividing the image
 * into sub-images based on a specified resolution, calculating the brightness of each sub-image,
 * and then matching each sub-image with a corresponding character that represents its brightness level.
 */
public class AsciiArtAlgorithm {
    private final WorkingImage image;
    private final SubImgCharMatcher subImgCharMatcher;
    private final int resolution;

    /**
     * Constructs an AsciiArtAlgorithm instance with a specified image, character matcher, and resolution.
     *
     * @param image The image to be converted into ASCII art.
     * @param subImgCharMatcher The character matcher that maps sub-image brightness to ASCII characters.
     * @param resolution The resolution of the ASCII art, defined as the number of characters
     *                   per row and column in the final output.
     */
    public AsciiArtAlgorithm(WorkingImage image, SubImgCharMatcher subImgCharMatcher, int resolution) {
        this.subImgCharMatcher = subImgCharMatcher;
        this.image = image;
        this.resolution = resolution;
    }


    /**
     * Converts the provided image to ASCII art by dividing the image into sub-images based on the
     * resolution, calculating the brightness of each sub-image, and mapping each to a corresponding
     * ASCII character.
     *
     * @return A 2D char array representing the ASCII art.
     */
    public char [][] run() {
        image.paddingImage();
        WorkingImage[][] dividedImage = image.toSubImages(resolution);
        int height = dividedImage.length, width = dividedImage[0].length;
        char [][] charImage = new char[height][];
        for (int i = 0; i < height; i++) {
            charImage[i] = new char[width];
        }
        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                // Maps each sub-image to an ASCII character based on its brightness.
                charImage[i][j] = subImgCharMatcher.getCharByImageBrightness(
                        dividedImage[i][j].brightnessCalculation());
            }
        }
        return charImage;
    }
}
