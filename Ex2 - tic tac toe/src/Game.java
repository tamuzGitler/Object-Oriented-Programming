/**
 * Game class simulates a tic tac toe.
 * tic tac toe game has two players that uses the following marks: X and O
 * each round the player chooses a coordination on the board until the game ends
 * @author Tamuz Gitler
 */
public class Game {

    //================ fields ================
    private final Player[] players;
    private final Renderer renderer;

    //================ constructor ================

    /**
     * Constructor of the class Game.
     *
     * @param playerX  - the first player in game
     * @param playerO  - the second player in game
     * @param renderer - Renders a given Board to the console
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.players = new Player[2];
        this.players[0] = playerX;
        this.players[1] = playerO;
        this.renderer = renderer;
    }

    //================ public methods ================

    /**
     * runs a tic tac toe game with two players till it ends.
     *
     * @return players Mark of the player who won or BLANK in case of a tie
     */
    public Mark run() {
        Board board = new Board();
        Mark[] marks = {Mark.X, Mark.O};
        int counter = 0;
        this.renderer.renderBoard(board);
        while (!board.gameEnded()) {
            this.players[counter % 2].playTurn(board, marks[counter % 2]);
            this.renderer.renderBoard(board);
            counter++;
        }
        return board.getWinner();
    }
}
