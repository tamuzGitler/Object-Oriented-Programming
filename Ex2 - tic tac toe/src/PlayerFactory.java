/**
 * This class builds players.
 * @author Tamuz Gitler
 */
public class PlayerFactory {

    //================ public methods ================

    /**
     * builds a player instance
     * @param playerType players name to build
     * @return new player
     */
    public Player buildPlayer(String playerType) {
        switch (playerType) {
            case "human":
                return new HumanPlayer();
            case "whatever":
                return new WhateverPlayer();
            case "clever":
                return new CleverPlayer();
            case "snartypamts":
                return new SnartypamtsPlayer();
            default:
                return null;
        }
    }
}
