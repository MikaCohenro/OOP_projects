/**
 * Represents a renderer that does not display the game board.
 */
public class VoidRenderer implements Renderer{
    /**
     * override the renderBoard function.
     * A blank renderer when NONE option is chosen - don't want a board to show
     *
     * @param board board - class obj
     */
    @Override
    public void renderBoard(Board board) {
        // Do nothing, as this renderer does not display the board.
    }
}
