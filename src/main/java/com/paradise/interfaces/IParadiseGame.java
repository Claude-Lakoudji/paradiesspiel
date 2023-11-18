package com.paradise.interfaces;

import com.paradise.enums.Color;

public interface IParadiseGame {

    // public ParadiseGame(Color... colors);

    // public ParadiseGame(String conf, Color... colors);

    /**
     * This method returns the color of the player currently taking their turn.
     *
     * @return The color of the player currently taking their turn
     */
    public Color getColorOnTurn();

    /**
     * This method assigns the turn to the specified color.
     *
     * @param color The color that will take the turn
     */
    public void setColorOnTurn(Color color);

    /**
     * This method returns the current position (field number) of the searched piece,
     * if it exists.
     *
     * @param character The name of the searched piece (e.g., "BLUE-A")
     *
     * @return The current position or field number of the searched piece, or -1 if
     *         it does not exist
     */
    public int getCharacterPosition(String character);

    /**
     * This method moves the specified piece to a new position. The number of squares
     * the piece should move is determined by the sum of the two dice rolls.
     *
     * @param figure       The name of the figure to be moved (e.g., "BLUE-A")
     * @param dice   One or more randomly rolled numbers
     *
     * @return true if the piece could be moved or was allowed to move; otherwise
     *         false. The method also returns false if the desired piece could not
     *         be found.
     */
    public boolean moveCharacter(String figure, int... dice);

    /**
     * This method returns the color of the winner.
     *
     * @return The color of the winner or null if there is no winner yet.
     */
    public Color getWinner();

    /**
     * This method returns an array with the colors of all active players.
     *
     * @return An array with the colors of the players.
     */
    public Color[] getAllPlayers();

    /**
     * This method formats the game for output to a text console in a meaningful way.
     *
     * @return The game in text format
     */
    public String toString();
}
