/**
 * Players interface, all players classes has to implement playTurn
 * @author Tamuz Gitler
 */
public interface Player {
    void playTurn(Board board, Mark playersMark);
}
