/**
 * The Tournament class represents a simple framework for a two-player game tournament.
 * It includes a main method to run the tournament based on command-line arguments.
 */
public class Tournament {
    private static final int ROUND_NUM = 0;
    private static final int SIZE = 1;
    private static final int WINSTREAK = 2;
    private static final int RENDERER = 3;
    private static final int FIRST_PLAYER = 4;
    private static final int SECOND_PLAYER = 5;

    private final int roundsNum;
    private final Renderer renderer;
    private final Player[] players;

    /**
     * Constructor for the Tournament class.
     *
     * @param rounds Number of rounds in the tournament.
     * @param renderer Renderer for the game.
     * @param player1 First player in the tournament.
     * @param player2 Second player in the tournament.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.roundsNum = rounds;
        this.renderer = renderer;
        this.players = new Player[]{player1, player2};
    }


    /**
     * Method to play the tournament.
     *
     * @param size Size of the game board.
     * @param winStreak Number of consecutive marks required to win.
     * @param playerName1 Name of the first player.
     * @param playerName2 Name of the second player.
     */
    public void playTournament(int size, int winStreak,
                        String playerName1, String playerName2) {
        int curRound = 0;
        int playerX = 0, playerO = 1;
        int[] scores = new int[]{0, 0, 0};//{player1score,player2score,ties}
        while (this.roundsNum > curRound) {
            Game game = new Game(this.players[playerX], this.players[playerO], size, winStreak,this.renderer);
            Mark winner = game.run();
            updateScores(winner, scores, playerX, playerO);
            curRound++;
            int[] rotatedPlayers = rotatePlayers(playerX, playerO);
            playerX = rotatedPlayers[0];
            playerO = rotatedPlayers[1];
        }
        printFinalScores(playerName1, playerName2, scores);
    }

    private void updateScores(Mark winner, int[] scores, int playerX, int playerO) {
        if (winner == Mark.X) {
            scores[playerX]++;
        } else if (winner == Mark.O) {
            scores[playerO]++;
        } else {
            scores[2]++; // Ties
        }
    }


    private int[] rotatePlayers(int playerX, int playerO) {
        return new int[]{playerO, playerX};
    }

    private void printFinalScores(String playerName1, String playerName2, int[] scores) {
        System.out.println("######### Results #########");
        System.out.printf("Player 1, %s won: %d rounds%n", playerName1, scores[0]);
        System.out.printf("Player 2, %s won: %d rounds%n",  playerName2, scores[1]);
        System.out.printf("Ties: %d", scores[2]);
    }

    /**
     * The main method to run the tournament based on command-line arguments.
     *
     * @param args Command-line arguments specifying tournament parameters.
     */
    public static void main(String[] args) {
        int roundNum = Integer.parseInt(args[ROUND_NUM]), boardSize = Integer.parseInt(args[SIZE]),
                winStreak = Integer.parseInt(args[WINSTREAK]);
        if (winStreak > boardSize) {
            winStreak = boardSize;
        }
        Renderer renderer = new RendererFactory().buildRenderer(args[RENDERER], boardSize);
        if (renderer == null) return;
        PlayerFactory playerFactory = new PlayerFactory();
        String[] playerNames = new String[]{args[FIRST_PLAYER].toLowerCase(),
                                            args[SECOND_PLAYER].toLowerCase()};
        Player[] players = new Player[]{playerFactory.buildPlayer(playerNames[0]),
                                        playerFactory.buildPlayer(playerNames[1])};
        if (players[0] == null||players[1] == null) return;
        Tournament tournament = new Tournament(roundNum, renderer, players[0],players[1]);
        tournament.playTournament(boardSize, winStreak, playerNames[0],playerNames[1]);
    }
}