/**
 * Represents a genius-level computer player in a Tic-Tac-Toe game.
 */
public class GeniusPlayer implements Player {

    /**
     * Plays a turn for the genius-level computer player.
     * The genius player will try to fill the first available empty cell in each column.
     *
     * @param board The game board.
     * @param mark  The mark of the player (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int col = 1; col < board.getSize(); col++) {
            for (int row = 0; row < board.getSize(); row++) {
                if (board.getMark(row, col) == Mark.BLANK) {
                    board.putMark(mark, row, col);
                    return;
                }
            }
        }
    }
}
