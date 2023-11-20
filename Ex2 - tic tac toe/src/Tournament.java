/**
 * simulates a tournament between two players.
 * this class holds Main function
 * @author Tamuz Gitler
 */
public class Tournament {

    //================ public constants ================

    private static final int ARG_NUM = 4;
    private static final int ARG_ROUNDS = 0;
    private static final int ARG_RENDERER = 1;
    private static final int ARG_FIRST_PLAYER = 2;

    //================ fields ================

    private final Renderer renderer;
    private final int rounds;
    private final Player[] players;

    //================ constructor ================

    /**
     * Constructor - builds a new tournament from given arguments.
     * @param rounds how many games the players will play
     * @param renderer interface of tic toc game
     * @param players competing players in the game
     */
    public Tournament(int rounds, Renderer renderer, Player[] players) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            this.players[i] = players[i];
        }
    }

    //================ public methods ================

    /**
     * simulates tic toc games between two players
     * @return counter of game results
     */
    public int[] playTournament() {
        int[] gameResults = {0, 0, 0};
        for (int round = 0; round < this.rounds; round++) {
            Game game = new Game(this.players[round % 2], this.players[(round + 1) % 2], renderer);
            Mark winner = game.run();
            updateGameResults(gameResults, round, winner);
        }
        printGameResult(gameResults);
        return gameResults;
    }

    //================ private methods ================

    /**
     * updates after each round the results of wins and ties
     * @param gameResults for tracking game results
     * @param round current game round
     * @param winner who won the current round
     */
    private void updateGameResults(int[] gameResults, int round, Mark winner) {
        if (winner == Mark.BLANK) {
            gameResults[2]++;
        } else if (winner == Mark.X) {
            gameResults[round % 2]++;
        } else {
            gameResults[(round + 1) % 2]++;
        }
    }

    /**
     * prints the final result of tournament between two players
     * @param gameResults for tracking game results
     */
    private void printGameResult(int[] gameResults) {
        System.out.println(String.format("=== player 1: %d | player 2: %d | Draws: %d ===\r",
                gameResults[0], gameResults[1], gameResults[2]));
    }

    //================ MAIN ================

    public static void main(String[] args) {
        if (args.length != ARG_NUM) {
            System.err.println("Illegal num of args, format is: rounds, renderer, player1, player2");
            return;
        }
        /* init arguments */
        int rounds = Integer.parseInt(args[ARG_ROUNDS]);
        Renderer renderer = new RendererFactory().buildRenderer(args[ARG_RENDERER]);
        Player player1 = new PlayerFactory().buildPlayer(args[ARG_FIRST_PLAYER]);
        Player player2 = new PlayerFactory().buildPlayer(args[ARG_FIRST_PLAYER + 1]);
        Player[] players = {player1, player2};

        if (illegalArgs(rounds, renderer, player1, player2)) {
            System.err.println("\nIllegal input: rounds - positive number, renderer - console ||... players - human||..");
        }
        /* if reached here - args are leggal */
        Tournament tournament = new Tournament(rounds, renderer, players);
        tournament.playTournament();
    }

    /**
     * checks if the gotten args are illegal
     * @param rounds should be positive
     * @param renderer shouldnt be null
     * @param player1 shouldnt be null
     * @param player2 shouldnt be null
     * @return true if arguments are legal, else false
     */
    private static boolean illegalArgs(int rounds, Renderer renderer, Player player1, Player player2) {
        return rounds < 0 || renderer == null || player1 == null || player2 == null;
    }
}
