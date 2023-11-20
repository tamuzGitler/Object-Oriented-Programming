/**
 * A void implementation of Renderer
 * @author Tamuz Gitler
 */
public class VoidRenderer implements Renderer {

    /**
     * this function doesnt print the board to the screen.
     * useful when simulating a lot of games.
     * @param board the board to "print".
     */
    @Override
    public void renderBoard(Board board) {
    }

}
