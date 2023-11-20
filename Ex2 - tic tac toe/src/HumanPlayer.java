import java.util.Scanner;

/**
 * player can play turn on a given board and uses the given mark.
 * the player needs to provide a legal input on board to play
 * @author Tamuz Gitler
 */
class HumanPlayer implements Player {

    //================ private constants ================

    private static final Scanner userCoordinationInput = new Scanner(System.in);

    //================ public methods ================

    /**
     * plays a turn on the given board.
     * the player needs to provide a legal coordination on board to finish his turn
     * @param board       to play on coordinate
     * @param playersMark to put on board
     */
    @Override
    public void playTurn(Board board, Mark playersMark) {
        boolean illegalInput = true;
        while (illegalInput) {
            /* receives user coordination for placing playersMark */
            System.out.println("Player " + playersMark + ", type coordinates: ");
            int num = userCoordinationInput.nextInt();
            int rowInput = num / 10 - 1;
            int colInput = num % 10 - 1;
            if (board.putMark(playersMark, rowInput, colInput)) {
                illegalInput = false;
            }
            else {
                System.out.println("Invalid coordinates, type again: ");
            }
        }
    }

}
