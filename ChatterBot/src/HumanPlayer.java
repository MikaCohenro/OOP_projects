/**
 * Represents a human player in a Tic-Tac-Toe game.
 */
public class HumanPlayer implements Player {

    private static final String GET_INPUT_MESSAGE = "Player %s, type coordinates: \n";
    private static final String INVALID_MESSAGE = "Invalid mark position, please choose a different " +
            "position.\nInvalid coordinates, type again: ";
    private static final String OCCUPIED_MESSAGE =  "Mark position is already occupied.\n" +
            "Invalid coordinates, type again: ";
    private KeyboardInput keyboardInput;

    /**
     * Constructs a HumanPlayer instance.
     * Initializes the keyboard input for reading player moves.
     */
    public HumanPlayer() {
        this.keyboardInput = KeyboardInput.getObject();
    }

    /**
     * Plays a turn for the human player, taking input from the user and updating the game board.
     *
     * @param board The game board.
     * @param mark  The mark of the player (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        String str_mark = (mark == Mark.X) ? "X" : "O";
        System.out.format(GET_INPUT_MESSAGE, str_mark);
        boolean isValidMove = false;
        while(!isValidMove){
            int input = keyboardInput.readInt();
            int row = input/10, col = input%10;
            //  check what row and what col
            isValidMove = board.putMark(mark, row, col);
            if (!isValidMove) {// not valid anymore - putmark returned false - cell OCCUPIED or not valid
                if (board.getMark(row,col) == Mark.BLANK){
                    // this means get mark returned Blank cause coord are not valid
                    System.out.println(INVALID_MESSAGE);
                    continue;
                }
                System.out.println(OCCUPIED_MESSAGE);
            }
        }
    }
}
