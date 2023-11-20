import java.util.*;
/**
 * WhateverPlayer is an implementation of Infrence Player.
 * WhateverPlayer Randomly chooses a coordinate on board and tries to putMark on it,
 * until success.
 * @author Tamuz Gitler
 */
class WhateverPlayer implements Player {


    //================ private constants ================

    private final Random rand = new Random();

    //================ public methods ================

    /**
     * plays a turn on the given board.
     * whatever chooses in a randomly way a coordinate on board
     * @param board       to play on coordinate
     * @param playersMark to put on board
     */
    @Override
    public void playTurn(Board board, Mark playersMark) {
        boolean illegalCoordinate = true;
        while (illegalCoordinate) {
            int randomRow = rand.nextInt(Board.SIZE); //randomly chooses row
            int randomCol = rand.nextInt(Board.SIZE); //randomly chooses col
            if (board.putMark(playersMark, randomRow, randomCol)){
                illegalCoordinate = false;
            }
        }
    }
}
