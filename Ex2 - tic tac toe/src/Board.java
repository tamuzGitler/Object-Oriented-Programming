/**
 * a board of game. This class is intended for use in tic tac toe game.
 * the board keeps track on if a game is over and if and who has won the game
 * @author Tamuz Gitler
 */
public class Board {

    //================ public constants ================

    public static final int SIZE = 6;
    public static final int WIN_STREAK = 4;

    //================ private constants ================

    private static final int ROW_UP = -1;
    private static final int ROW_DOWN = 1;
    private static final int COL_LEFT = -1;
    private static final int COL_RIGHT = 1;
    private static final int NO_CHANGE = 0;

    //================ fields ================

    private int emptyCells = SIZE * SIZE;
    private boolean isGameOver = false;
    private Mark winner = Mark.BLANK;
    private final Mark[][] board;

    //================ constructor ================

    /**
     * Constructor of the class Board.
     * inits a board with BLANK Marks in all locations
     */
    public Board() {
        this.board = new Mark[Board.SIZE][Board.SIZE];
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                this.board[row][col] = Mark.BLANK;
            }
        }
    }

    //================ public methods ================

    /**
     * assigns players mark on board.
     * @param mark - the players mark choice
     * @param row  - coordination on board
     * @param col  - coordination on board
     * @return true if assigment succeeded, else false
     */
    public boolean putMark(Mark mark, int row, int col) {
        /* checks input and if coordinate is empty */
        if (inputIsIllegal(row, col) || mark == Mark.BLANK || this.board[row][col] != Mark.BLANK) {
            return false;
        }
        this.board[row][col] = mark;
        this.emptyCells--;
        updateGameStatus(mark, row, col);
        return true;
    }

    /**
     * gets the mark on board in coordination (row,col).
     * @param row - coordination on board
     * @param col - coordination on board
     * @return the mark if its legal coordination, else Mark.BLANK
     */
    public Mark getMark(int row, int col) {
        /* checks if the given row or col are out of bond */
        if (inputIsIllegal(row, col)){
            return Mark.BLANK;
        }
        return this.board[row][col];
    }

    /**
     * checks if the game on board has ended.
     * @return true if game is over, else false
     */
    public boolean gameEnded() {
        return this.isGameOver;
    }

    /**
     * gets the winner of the board game.
     * @return winners Mark or BLANK mark if tie
     */
    public Mark getWinner() {
        return this.winner;
    }

    //================ private methods ================

    /**
     * checks if input of row and col is legal
     * @param row - coordination on board
     * @param col - coordination on board
     * @return true if input is illegal
     */
    private boolean inputIsIllegal(int row, int col) {
        return  row >= Board.SIZE || col >= Board.SIZE || row < 0 || col < 0;
    }

    /**
     * counts the amount of same marks as mark in a given direction.
     * @param row      to start from on board
     * @param col      to start from on board
     * @param rowDelta for direction
     * @param colDelta for direction
     * @param mark     to look for
     * @return count, of the sequence of mark in the wanted direction
     */
    private int countMarkInDirection(int row, int col, int rowDelta, int colDelta, Mark mark) {
        int count = 0;
        while (row < SIZE && row >= 0 && col < SIZE && col >= 0 && board[row][col] == mark) {
            count++;
            row += rowDelta;
            col += colDelta;
        }
        return count;
    }

    /**
     * updates board data, by checking if a player has won or its a tie.
     * @param mark on board
     * @param row  chosen coordinate on board
     * @param col  chosen coordinate on board
     */
    private void updateGameStatus(Mark mark, int row, int col) {
        if (!playerHasWon(mark, row, col)) {
            checkIfTie();
        }
    }

    /**
     * checks if the player with mark has won the board game.
     * @param mark on board
     * @param row  chosen coordinate on board
     * @param col  chosen coordinate on board
     * @return true if there is a win streak with current mark on board, else false
     */
    private boolean playerHasWon(Mark mark, int row, int col) {
        if (checkHorizontal(mark, row, col) || checkVertical(mark, row, col) ||
                checkLeftDiagonal(mark, row, col) || checkRightDiagonal(mark, row, col)) {
            this.winner = mark;
            this.isGameOver = true;
            return true;
        }
        return false;
    }

    /**
     * checks if it's a tie.
     */
    private void checkIfTie() {
        if (this.emptyCells == 0 && this.winner == Mark.BLANK) {
            this.isGameOver = true;
        }
    }

    /**
     * checks the board horizontally from given coordination.
     * @param mark on board
     * @param row  chosen coordinate on board
     * @param col  chosen coordinate on board
     * @return true if there is horizontal win streak, else false
     */
    private boolean checkHorizontal(Mark mark, int row, int col) {
        int left = countMarkInDirection(row, col, NO_CHANGE, COL_LEFT, mark);
        int right = countMarkInDirection(row, col, NO_CHANGE, COL_RIGHT, mark);
        int horizontal = left + right - 1;
        return horizontal >= Board.WIN_STREAK;
    }

    /**
     * checks the board vertically from given coordination.
     * @param mark on board
     * @param row  chosen coordinate on board
     * @param col  chosen coordinate on board
     * @return true if there is vertical win streak, else false
     */
    private boolean checkVertical(Mark mark, int row, int col) {
        int up = countMarkInDirection(row, col, ROW_DOWN, NO_CHANGE, mark);
        int down = countMarkInDirection(row, col, ROW_UP, NO_CHANGE, mark);
        int vertical = down + up - 1;
        return vertical >= Board.WIN_STREAK;
    }

    /**
     * checks the board leftDiagonally from given coordination.
     * @param mark on board
     * @param row  chosen coordinate on board
     * @param col  chosen coordinate on board
     * @return true if there is leftDiagonal win streak, else false
     */
    private boolean checkLeftDiagonal(Mark mark, int row, int col) {
        int rightUp = countMarkInDirection(row, col, ROW_UP, COL_RIGHT, mark);
        int leftDown = countMarkInDirection(row, col, ROW_DOWN, COL_LEFT, mark);
        int leftDiagonal = leftDown + rightUp - 1;
        return leftDiagonal >= Board.WIN_STREAK;
    }

    /**
     * checks the board rightDiagonally from given coordination.
     * @param mark on board
     * @param row  chosen coordinate on board
     * @param col  chosen coordinate on board
     * @return true if there is rightDiagonal win streak, else false
     */
    private boolean checkRightDiagonal(Mark mark, int row, int col) {
        int leftUp = countMarkInDirection(row, col, ROW_UP, COL_LEFT, mark);
        int rightDown = countMarkInDirection(row, col, ROW_DOWN, COL_RIGHT, mark);
        int rightDiagonal = leftUp + rightDown - 1;
        return rightDiagonal >= Board.WIN_STREAK;
    }
}


