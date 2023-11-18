package com.paradise;

import java.util.Random;

/**
 * The Dice class represents a die with a specific number of faces (sides).
 * The die can be rolled to generate random values within the valid range.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class Dice {
    private final int faceCount;
    private final Random random = new Random();

    /**
     * Creates a new die with the specified number of faces (sides).
     *
     * @param faceCount The number of faces (sides) on the die.
     */
    public Dice(int faceCount) {
        this.faceCount = faceCount;
    }

    /**
     * Rolls the die and returns a random face value between 1 and the maximum face value.
     *
     * @return The rolled face value.
     */
    public int roll() {
        return random.nextInt(faceCount) + 1;
    }

    @Override
    public String toString() {
        return "Die with " + faceCount + " faces";
    }
}