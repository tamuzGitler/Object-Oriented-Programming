/**
 * Snartypamts is an implementation of Infrence Player.
 * Snartypamts chooses his next move in a smarter way than all the other players!
 * @author Tamuz Gitler
 */
class SnartypamtsPlayer implements Player {

    //================ public methods ================

    /**
     * plays a turn on the given board.
     * Snartypamts chooses in a smart way a coordinate on board
     * @param board       to play on coordinate
     * @param playersMark to put on board
     */
    @Override
    public void playTurn(Board board, Mark playersMark) {
        for (int col = 0; col < Board.SIZE; col++) {
            //blocks a player who chooses coordinates in the same row
            if (board.putMark(playersMark,0, col)) {
                break;
            }
            else {
                if (board.getMark(0, col) == playersMark) {
                    for (int row = 0; row < Board.SIZE; row++) {
                        if (board.putMark(playersMark,row, col)) {
                            break;
                        }
                        //better proceed to next column
                        if(board.getMark(row,col) != playersMark){
                            break;
                        }
                    }
                }
            }
        }
    }
}
