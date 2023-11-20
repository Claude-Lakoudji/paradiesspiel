package com.paradise;


import com.paradise.enums.Color;
import com.paradise.fields.Field;

import java.util.Arrays;

/**
 * The Figurine class represents a figure in the game. Each figure has a color, a
 * name, a position on the game board, a target position, a number of steps to
 * the target, and the rolled dice values.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class Figurine {
    private final Color color;
    private final String name;
    private Field position;
    private int stepsToTarget;
    private int targetPositionNumber;
    private int[] diceValues;

    /**
     * Creates a new figure with a specific color and name.
     *
     * @param color The color of the figure.
     * @param name  The name of the figure.
     */
    public Figurine(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    // Getter and setter methods for various attributes of the Figurine class.

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Field getPosition() {
        return position;
    }

    public void setPosition(Field position) {
        this.position = position;
    }

    public int getTargetPositionNumber() {
        return this.targetPositionNumber;
    }

    public void setTargetPositionNumber(int targetPosition) {
        this.targetPositionNumber = targetPosition;
    }

    /**
     * Sets the dice values for the figure and updates the steps to the target and
     * the target position. The dice values represent the values rolled on both dice.
     *
     * @param diceValues An array containing the rolled values of the two dice.
     */
    public void setDiceValues(int[] diceValues) {
        this.diceValues = diceValues;
        this.setStepsToTarget(diceValues[0] + diceValues[1]);
        this.setTargetPositionNumber(this.position.getPositionNumber() + this.stepsToTarget);
    }

    public int[] getDiceValues() {
        return diceValues;
    }

    public int getStepsToTarget() {
        return this.stepsToTarget;
    }

    public void setStepsToTarget(int steps) {
        this.stepsToTarget = steps;
    }

    /**
     * Determines whether the figure should move forward or backward based on the
     * current and target positions.
     *
     * @return true if the target position is greater than the current position,
     *         otherwise false.
     */
    public boolean forward() {
        return this.getTargetPositionNumber() > this.getPosition().getPositionNumber();
    }

    @Override
    public String toString() {
        return "Figurine{" + "color=" + color + ", name='" + name + '\'' + ", position=" + position
                + ", stepsToTarget=" + stepsToTarget + ", targetPositionNumber=" + targetPositionNumber
                + ", diceValues=" + Arrays.toString(diceValues) + '}';
    }
}