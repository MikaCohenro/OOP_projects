package ascii_art;

/**
 * This class defines various constants used throughout the `ex3` project.
 * They provide default values, error messages, and command keywords for user interaction.
 */
public class Constants {
    /**
     * Stores the default image file name.
     * Value: "cat.jpeg"
     */
    public static final String DEFAULT_FILE_NAME = "cat.jpeg";

    /**
     * Sets the default image resolution.
     * Value: 128
     */
    public static final int DEFAULT_RES_SIZE = 128;

    /**
     * Defines the default character set used for generating ASCII art.
     * Value: {'0','1','2','3','4','5','6','7','8','9'}
     */
    public static final char[] DEFAULT_CHAR_SET = {'0','1','2','3','4','5','6','7','8','9'};

    /**
     * Provides the prompt message displayed before requesting user input.
     * Value: ">>> "
     */
    public static final String REQ_INPUT_MES = ">>> ";

    /**
     * Message displayed when resolution is successfully set.
     * Value: "Resolution set to " (followed by the new resolution)
     */
    public static final String RES_MSG_SUCCESS = "Resolution set to ";
    /**
     * Error message shown when resolution change fails due to exceeding boundaries.
     * Value: "Did not change resolution due to exceeding boundaries."
     */
    public static final String RES_BOUND_ERROR = "Did not change resolution due to exceeding boundaries.";

    /**
     * Error message displayed when resolution change fails due to incorrect format.
     * Value: "Did not change resolution due to incorrect format."
     */
    public static final String RES_FORMAT_ERROR = "Did not change resolution due to incorrect format.";
    /**
     * Error message shown when adding characters to the set fails due to incorrect format.
     * Value: "Did not add due to incorrect format."
     */
    public static final String ADD_FORMAT_ERROR = "Did not add due to incorrect format.";

    /**
     * Error message displayed when removing characters from the set fails due to incorrect format.
     * Value: "Did not remove due to incorrect format."
     */
    public static final String REMOVE_FORMAT_ERROR = "Did not remove due to incorrect format.";
    // consistency

    /**
     * General error message for issues related to the image file.
     * Value: "Did not execute due to problem with image file."
     */
    public static final String IMG_FILE_ERROR = "Did not execute due to problem with image file.";


    /**
     * Error message displayed when changing output method fails due to incorrect format.
     * Value: "Did not change output method due to incorrect format."
     */
    public static final String OUTPUT_FORMAT_ERROR = "Did not change output method due to incorrect format.";

    /**
     * Error message shown when the character set is empty.
     * Value: "Did not execute. Charset is empty."
     */
    public static final String EMPTY_CHAR_SET_ERROR = "Did not execute. Charset is empty.";

    /**
     * General error message displayed for unrecognized commands.
     * Value: "Did not execute due to incorrect command."
     */
    public static final String INCORRECT_COMMAND_ERROR = "Did not execute due to incorrect command.";

    // ... (rest of the constants with their respective documentation)

    /**
     * command used to exit the program.
     * Value: "exit"
     */
    public static final String EXIT = "exit";
    /**
     * command used to show current chars in set
     * Value: "chars"
     */
    public final static String CHARS = "chars";
    /**
     * command used to add char to current charset
     * Value: "add"
     */
    public final static String ADD = "add";
    /**
     * command used to remove char to current charset
     * Value: "remove"
     */
    public final static String REMOVE = "remove";
    /**
     * command used to change resolution
     * Value: "res"
     */
    public final static String RESOLUTION = "res";
    /**
     * command used to change image
     * Value: "image"
     */
    public final static String IMAGE = "image";
    /**
     * command used to change output type - console or html
     * Value: "output"
     */
    public final static String OUTPUT = "output";
    /**
     * command used to out the image
     * Value: "asciiArt"
     */
    public final static String ASCII_ART = "asciiArt";
    /**
     * second word part of command used to change resolution up
     * Value: "up"
     */
    public final static String UP = "up";
    /**
     * second word part of command used to change resolution down
     * Value: "down"
     */
    public final static String DOWN = "down";
    /**
     * second word part of command used to add/remove all ascii chars to current charset
     * Value: "all"
     */
    public final static String ALL_CMD = "all";
    /**
     * second word part of command used to add/remove space char to current charset
     * Value: "space"
     */
    public final static String SPACE_CMD = "space";

    /**
     * second word part of command used to change output type
     * Value: "html"
     */

    public final static String HTML_OUT = "html";
    /**
     * second word part of command used to change output type
     * Value: "console"
     */
    public final static String CONSOLE_OUT = "console";
    /**
     * Font used for HTML output.
     * Value: "Courier New"
     */
    public static final String FONT = "Courier New";

    /**
     * Default path for HTML output file.
     * Value: "out.html"
     */
    public static final String OUTPUT_PATH = "out.html";

    /**
     * String representing a space character.
     * Value: " "
     */
    public static final String SPACE = " ";

    /**
     * Last printable character in the ASCII table.
     * Value: 127
     */
    public static final int ASCII_LAST = 127;

    /**
     * First printable character in the ASCII table.
     * Value: 32
     */
    public static final int ASCII_FIRST = 32;
    /**
     * Integer representing color white .
     * Value: 255
     */
    public static final int WHITE_COLOR = 255;

}


