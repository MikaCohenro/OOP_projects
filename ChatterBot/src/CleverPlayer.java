/**
 * Represents a clever computer player in a Tic-Tac-Toe game.
 * The clever player plays its turn by selecting the first available empty cell in a row-major order.
 */
public class CleverPlayer implements Player {

    /**
     * Plays a turn for the clever computer player.
     * The clever player selects the first available empty cell in a row-major order.
     *
     * @param board The game board.
     * @param mark  The mark of the player (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getMark(row, col) == Mark.BLANK) {
                    board.putMark(mark, row, col);
                    return;
                }
            }
        }
    }
}
