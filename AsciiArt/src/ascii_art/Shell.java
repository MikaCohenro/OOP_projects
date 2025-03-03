package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.WorkingImage;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import java.util.*;


/**
 * The Shell class provides an interactive command-line interface for users to generate ASCII art
 * from images. It supports various commands to control the characters used, the resolution of the
 * output, the image source, and the output format (console or HTML).
 */
public class Shell {
    private TreeSet<Character> charSet;
    private SubImgCharMatcher subImgCharMatcher;
    private WorkingImage image;
    private int res;
    private AsciiOutput output;

    /**
     * Initializes a new Shell instance with default settings.
     */
    public Shell() {
        charSet = new TreeSet<>();
        for (char c: Constants.DEFAULT_CHAR_SET){
            charSet.add(c);
        }
        try {
            image = new WorkingImage(Constants.DEFAULT_FILE_NAME);
        } catch (IOException e) {
            System.out.println(Constants.IMG_FILE_ERROR);
        }
        res = Constants.DEFAULT_RES_SIZE;
        output = new ConsoleAsciiOutput();
        subImgCharMatcher = new SubImgCharMatcher(Constants.DEFAULT_CHAR_SET);
    }

    /**
     * Starts the interactive command-line interface for the ASCII art generator. Listens for user
     * input and executes corresponding commands until the 'exit' command is entered.
     */
    public void run(){
        Map<String, CommandHandler> commandHandlers = initializeCommandHandlers();
        while (true){
            System.out.print(Constants.REQ_INPUT_MES);
            String userInput = KeyboardInput.readLine();
            String[] dividedInput = userInput.split(Constants.SPACE);
            String command = dividedInput[0];
            if (command.equals(Constants.EXIT)) break;
            commandHandlers.getOrDefault(command, this::handleIncorrectCommand).execute(dividedInput);
        }
    }

    /**
     * Initializes and returns a map of commands to their corresponding handler methods.
     *
     * @return A Map object where each key is a String representing a command,
     * and each value is a CommandHandler.
     */
    private Map<String, CommandHandler> initializeCommandHandlers() {
        Map<String, CommandHandler> handlers = new HashMap<>();
        handlers.put(Constants.CHARS, this::showCharSet);
        handlers.put(Constants.ADD, this::addChar);
        handlers.put(Constants.REMOVE, this::removeChar);
        handlers.put(Constants.RESOLUTION, this::changeRes);
        handlers.put(Constants.IMAGE, this::changeImg);
        handlers.put(Constants.OUTPUT, this::changeOutput);
        handlers.put(Constants.ASCII_ART, this::runAlgorithm);
        return handlers;
    }

    private void showCharSet(String[] dividedInput) {
        for (char c: charSet) System.out.print(c + " ");
        System.out.println();
    }


    private interface CommandHandler {
        void execute(String[] dividedInput);
    }

    private void handleIncorrectCommand(String[] dividedInput) {
        System.out.println(Constants.INCORRECT_COMMAND_ERROR);
    }

    private void runAlgorithm(String[] dividedInput){
        if (charSet.isEmpty()){
            System.out.println(Constants.EMPTY_CHAR_SET_ERROR);
            return;
        }
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, subImgCharMatcher, res);
        output.out(asciiArtAlgorithm.run());
    }
    private void changeOutput(String[] dividedInput){
        if (dividedInput.length!=2){
            System.out.println(Constants.OUTPUT_FORMAT_ERROR);
            return;
        }
        switch (dividedInput[1]){
            case Constants.HTML_OUT:
                output = new HtmlAsciiOutput(Constants.OUTPUT_PATH, Constants.FONT);
                break;
            case Constants.CONSOLE_OUT:
                output = new ConsoleAsciiOutput();
                break;
            default:
                System.out.println(Constants.OUTPUT_FORMAT_ERROR);
        }
    }

    private void changeImg(String[] dividedInput){
        try {
            image = new WorkingImage(dividedInput[1]);
        } catch (IOException e) {
            System.out.println(Constants.IMG_FILE_ERROR);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println(Constants.INCORRECT_COMMAND_ERROR);
        }
    }
    private void changeRes(String[] dividedInput){
        if (dividedInput.length == 2 &&
                (dividedInput[1].equals(Constants.UP) || dividedInput[1].equals(Constants.DOWN))){
            if ((dividedInput[1].equals(Constants.UP))&&(resValidation(res*2))){
                res *= 2;
                System.out.println(Constants.RES_MSG_SUCCESS + res+ ".");
            } else if ((dividedInput[1].equals(Constants.DOWN))&&(resValidation(res/2))) {
                res /= 2;
                System.out.println(Constants.RES_MSG_SUCCESS + res+ ".");
            }
            else{
                System.out.println(Constants.RES_BOUND_ERROR);
            }
        }
        else {
            System.out.println(Constants.RES_FORMAT_ERROR);
        }
    }
    boolean resValidation(int res){
        int minCharsInRow = Math.max(1, image.getWidth()/image.getHeight());
        int maxCharsInRow = image.getWidth();
        return (res<=maxCharsInRow)&&(res>=minCharsInRow);
    }

    private void addChar(String[] dividedInput){
        String command = dividedInput[1];
        if (dividedInput.length!=2){
            System.out.println(Constants.ADD_FORMAT_ERROR);
            return;
        }
        switch (command){
            case Constants.ALL_CMD:
                addAllAsciiChars();
                break;
            case Constants.SPACE_CMD:
                addSpace();
                break;
            default:
                defaultAdd(command);
        }
    }

    private void defaultAdd(String command) {
        if (command.length() == 1){
            addSingleChar(command.charAt(0));
        }
        else if (command.length() == 3 && command.charAt(1) == '-'){
            addRange(command);
            }
        else {
            System.out.println(Constants.ADD_FORMAT_ERROR);
        }
    }

    private void addAllAsciiChars() {
        for (int i = Constants.ASCII_FIRST; i < Constants.ASCII_LAST; i++) {
            addSingleChar((char) i);
        }
    }

    private void addSpace() {
        charSet.add(' ');
        subImgCharMatcher.addChar(' ');
    }

    private void addSingleChar(char c) {
        if (!charSet.contains(c)){
            charSet.add(c);
            subImgCharMatcher.addChar(c);
        }
    }
    private void addRange(String command) {
        int firstLetter = Math.min(command.charAt(0), command.charAt(2));
        int secondLetter = Math.max(command.charAt(0), command.charAt(2));
        for (int i = firstLetter; i <= secondLetter; i++) {
            charSet.add((char) i);
            subImgCharMatcher.addChar((char) i);
        }
    }
    private void removeChar(String[] dividedInput){
        String command = dividedInput[1];
        if (dividedInput.length!=2){
            System.out.println(Constants.REMOVE_FORMAT_ERROR);
            return;
        }
        switch (command){
            case Constants.ALL_CMD:
                removeAllAsciiChars();
                break;
            case Constants.SPACE_CMD:
                removeSpace();
                break;
            default:
                defaultRemove(command);
        }
    }

    private void defaultRemove(String command) {
        if (command.length() == 1){
            removeSingleChar(command.charAt(0));
        }
        else if (command.length() == 3 && command.charAt(1) == '-'){
            removeRange(command);
        }
        else {
            System.out.println(Constants.REMOVE_FORMAT_ERROR);
        }
    }

    private void removeAllAsciiChars() {
        charSet = new TreeSet<>();
        subImgCharMatcher = new SubImgCharMatcher(new char[]{});
    }
    private void removeSpace() {
        if (charSet.contains(' ')){
            charSet.remove(' ');
            subImgCharMatcher.removeChar(' ');
        }
    }
    private void removeSingleChar(char c) {
        if (charSet.contains(c)){
            charSet.remove(c);
            subImgCharMatcher.removeChar( c);
        }
    }
    private void removeRange(String command) {
        int firstLetter = Math.min(command.charAt(0), command.charAt(2));
        int secondLetter = Math.max(command.charAt(0), command.charAt(2));
        for (int i = firstLetter; i <= secondLetter; i++) {
            if (charSet.contains((char) i)){
                charSet.remove((char) i);
                subImgCharMatcher.removeChar((char) i);
            }

        }
    }

    /**
     * Main method to start the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Shell().run();
    }
}
