import java.util.Random;

/**
 * Represents a player that makes random moves on the game board.
 */
public class WhateverPlayer implements Player {
    private final Random rand = new Random();

    /**
     * Plays a turn by making random moves on the game board until a valid move is made.
     *
     * @param board The game board.
     * @param mark  The mark of the player (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int randomRow = rand.nextInt(board.getSize());
        int randomCol = rand.nextInt(board.getSize());
        while (!board.putMark(mark, randomRow, randomCol)) {
            randomRow = rand.nextInt(board.getSize());
            randomCol = rand.nextInt(board.getSize());
        }
    }
}
