/**
 * Clever is an implementation of Infrence Player.
 * clever chooses his next move in a smart way that beats almost all the other players!
 * @author Tamuz Gitler
 */
class CleverPlayer implements Player {

    //================ public methods ================

    /**
     * plays a turn on the given board.
     * clever chooses in a clever way a coordinate on board
     * @param board       to play on coordinate
     * @param playersMark to put on board
     */
    @Override
    public void playTurn(Board board, Mark playersMark) {
        int row = 0;
        boolean putMarkSuccseded = false;
        while (!putMarkSuccseded){
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.putMark(playersMark,row, col)) {
                    putMarkSuccseded = true;
                    break;
                }
            }
            row ++;
        }
    }
}
