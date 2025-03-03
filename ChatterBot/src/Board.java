/**
 * Represents a Tic-Tac-Toe game board.
 */
public class Board {
    private static final int SIZE = 4;

    private final Mark[][] board;
    private final int boardSize;

    /**
     * Constructs a new empty board with the default size.
     */
    public Board() {
        this.boardSize = SIZE;
        this.board = new Mark[SIZE][SIZE];

        // Initialize cells as blanks
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * Constructs a board with a given size and fills it with blank marks.
     *
     * @param size The size of the board (board size = size * size).
     */
    public Board(int size) {
        this.boardSize = size;
        this.board = new Mark[size][size];

        // Initialize cells as blanks
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * Gets the size of the board.
     *
     * @return The size of the board.
     */
    public int getSize() {
        return this.boardSize;
    }

    /**
     * Checks if the given coordinates are within the valid range of the board.
     *
     * @param row The row coordinate.
     * @param col The column coordinate.
     * @return True if the coordinates are valid, false otherwise.
     */
    private boolean isNotValid(int row, int col) {
        return (row < 0) || (col < 0) || (row >= boardSize) || (col >= boardSize);
    }

    /**
     * Adds the player's mark to the board at the specified coordinates.
     *
     * @param mark The mark to be placed.
     * @param row  The row coordinate.
     * @param col  The column coordinate.
     * @return True if the mark was placed successfully, false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (isNotValid(row, col) || board[row][col] != Mark.BLANK ) {
            return false;
        }
        board[row][col] = mark;
        return true;
    }

    /**
     * Retrieves the mark at the specified coordinates.
     *
     * @param row The row coordinate.
     * @param col The column coordinate.
     * @return BLANK if the coordinates are not legal; otherwise, the mark at the given coordinates.
     */
    public Mark getMark(int row, int col) {
        if (isNotValid(row, col)) {
            return Mark.BLANK;
        }
        return this.board[row][col];
    }
}
