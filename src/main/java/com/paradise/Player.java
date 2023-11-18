package com.paradise;


import com.paradise.enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The Player class represents a player in the game, who has a color and a list of pieces.
 * The number of pieces in paradise can be queried.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class Player {
    private final Color color;
    private final List<Figure> figures;

    /**
     * Creates a new player with the specified color.
     *
     * @param color The color of the player.
     */
    public Player(Color color) {
        this.color = color;
        figures = new ArrayList<>();
        figures.add(new Figure(color, color.name() + "-A"));
        figures.add(new Figure(color, color.name() + "-B"));
    }

    public Color getColor() {
        return color;
    }

    public List<Figure> getCharacters() {
        return figures;
    }

    /**
     * Returns the number of figures of the player that are in paradise.
     *
     * @return The number of figures in paradise.
     */
    public int getNumberOfCharactersInParadise() {
        int count = 0;
        for (Figure p : figures) {
            if (p.getPosition().getPositionNumber() == 63) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Player{" + "color=" + color + ", pieces=" + figures + '}';
    }

}