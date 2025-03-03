/**
 * Class representing a single tic-tac-toe game
 */
public class Game {

    private static final int DEFAULT_WIN_STREAK = 3;

    private Player[] players;
    private Renderer renderer;
    private Board board;
    private int winStreak;

    private int movesNumber = 0;
    /**
     * Constructs a new game with default settings.
     *
     * @param playerX   The X player.
     * @param playerO   The O player.
     * @param renderer  renderer void or console renderer
     */
    public Game(Player playerX,Player
            playerO, Renderer renderer){
        this.players = new Player[]{playerX,playerO};
        this.renderer = renderer;
        this.board = new Board();
        this.winStreak = DEFAULT_WIN_STREAK;
    }

    /**
     * Constructs a new game with custom settings.
     *
     * @param playerX    The X player.
     * @param playerO    The O player.
     * @param size       The size of the game board.
     * @param winStreak  The number of marks required for a win.
     * @param renderer   The renderer for displaying the game.
     */
    public Game(Player playerX,Player playerO,int size, int winStreak, Renderer renderer){
        this.players = new Player[]{playerX,playerO};
        this.renderer = renderer;
        this.board = new Board(size);
        if (winStreak > size) {
            this.winStreak = size;
        }
        else{
            this.winStreak = winStreak;
        }
    }

    /**
     * Gets the win streak required for a player to win.
     *
     * @return The win streak.
     */
    public int getWinStreak(){
        return this.winStreak;
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the board.
     */

    public int getBoardSize(){
        return this.board.getSize();
    }

    /**
     * Runs the tic-tac-toe game, allowing players to take turns until a winner is determined or it's a tie.
     *
     * @return The mark of the winning player or BLANK in case of a tie.
     */

    public Mark run() {
        Player currentPlayer = players[movesNumber % 2];
        Mark currentMark = (movesNumber % 2 == 0) ? Mark.X : Mark.O;
        while (true) {
            renderer.renderBoard(board);
            currentPlayer.playTurn(this.board, currentMark);
            if (isWinner(currentMark)) {
                renderer.renderBoard(board); // Display the final state of the board
                return currentMark;
            }
            movesNumber++;
            currentPlayer = players[movesNumber % 2];
            currentMark = (movesNumber % 2 == 0) ? Mark.X : Mark.O;
            if (movesNumber == board.getSize() * board.getSize()) { //ots a tie
                renderer.renderBoard(board); // Display the final state of the board
                return Mark.BLANK;
            }

        }
    }

    /**
     * Helper function to count the number of marks in a given direction.
     *
     * @param row      The starting row.
     * @param col      The starting column.
     * @param rowDelta The change in row for each step.
     * @param colDelta The change in column for each step.
     * @param mark     The mark to count.
     * @return The count of marks in the specified direction.
     */
    private int countMarkInDirection(int row, int col, int rowDelta,
                                     int colDelta, Mark mark) {
        int count = 0;
        while (row < this.getBoardSize() && row >= 0 && col < this.getBoardSize() && col >= 0 &&
                this.board.getMark(row, col) == mark) {
            count++;
            row += rowDelta;
            col += colDelta;
        }
        return count;
    }

    /**
     * Checks if the current player has won the game.
     *
     * @param mark The mark of the current player.
     * @return True if the current player has won, false otherwise.
     */
    private boolean isWinner(Mark mark) {
        int size = this.getBoardSize();
        int winStreakMinusOne = winStreak - 1;

        return checkDirection(mark, size, winStreakMinusOne, 0, 1) ||   // Rows
                checkDirection(mark, size, winStreakMinusOne, 1, 0) ||   // Columns
                checkDirection(mark, size, winStreakMinusOne, 1, 1) ||   // Diagonal
                checkDirection(mark, size, winStreakMinusOne, 1, -1);    // Anti-diagonal
    }

    /**
     * Checks for a winning streak in a specified direction.
     *
     * @param mark            The mark of the current player.
     * @param size            The size of the game board.
     * @param winStreakMinusOne The win streak minus one.
     * @param rowDelta        The change in row for each step.
     * @param colDelta        The change in column for each step.
     * @return True if there is a winning streak, false otherwise.
     */
    private boolean checkDirection(Mark mark, int size, int winStreakMinusOne, int rowDelta, int colDelta) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size - winStreakMinusOne; col++) {
                if (countMarkInDirection(row, col, rowDelta, colDelta, mark) >= winStreak) {
                    return true;
                }
            }
        }
        return false;
    }
}
