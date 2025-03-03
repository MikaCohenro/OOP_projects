/**
 * A factory class for creating instances of different players based on the specified type.
 */
public class PlayerFactory {
    /**
     * Default constructor for the PlayerFactory.
     */
    public PlayerFactory(){}

    /**
     * Creates and returns an instance of a player based on the specified type.
     *
     * @param type The type of player to be created ("human", "clever", "genius", or "whatever").
     * @return A new instance of the corresponding player type.
     */
    public Player buildPlayer(String type){
        // Check the type of player and create an instance accordingly
        if (type.equalsIgnoreCase("human")) {
            return new HumanPlayer();
        } else if ("clever".equalsIgnoreCase(type)) {
            return new CleverPlayer();
        } else if ("genius".equalsIgnoreCase(type)) {
            return new GeniusPlayer();
        } else if ("whatever".equalsIgnoreCase(type)) {
            return new WhateverPlayer();
        } else {
            // Print an error message if an invalid player type is specified
            System.out.println("Choose a player, and start again.\nThe players: " +
                    "[human, clever, whatever, genius]");
            // Return null for an invalid player type
            return null;
        }
    }
}
