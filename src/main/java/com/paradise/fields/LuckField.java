package com.paradise.fields;

import com.paradise.Figurine;

/**
 * The LuckField class represents an event field where a figure may advance by the numbers rolled
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class LuckField extends EventField {

    /**
     * Constructor for the LuckField class.
     *
     * @param position The position of the LuckField on the game board.
     */
    public LuckField(int position) {
        super(position);
    }

    /**
     * Executes the event when a piece lands on the LuckField. The piece is moved
     * according to the rolled dice value.
     *
     * @param figurineToMove The character that has landed on the LuckField.
     */
    @Override
    public void executeEvent(Figurine figurineToMove) {
        if (figurineToMove.getTargetPositionNumber() == this.getPositionNumber()) {
            Field newField = calculateNewField(figurineToMove);
            figurineToMove.setPosition(newField);
            updateTargetPosition(figurineToMove);
            executeEventIfEventField(figurineToMove);
        }
    }

    /**
     * Moves the piece to the next or previous position depending on its
     * movement direction.
     *
     * @param figurineToMove The character to be moved on the game board.
     */
    @Override
    public void moveToNextOrPrev(Figurine figurineToMove) {
        boolean forward = figurineToMove.forward();
        Field newField = forward ? this.getNextField() : this.getPreviousField();
        figurineToMove.setPosition(newField);
    }

    /**
     * Calculates the new field for the piece based on the sum of the rolled dice value.
     *
     * @param figurineToMove The character for which the new position should be calculated.
     * @return The new field where the piece will be placed.
     */
    private Field calculateNewField(Figurine figurineToMove) {
        Field newField = this.getNextField();
        int stepsToTarget = figurineToMove.getDiceValues()[0] + figurineToMove.getDiceValues()[1];
        for (int i = 1; i < stepsToTarget; i++) {
            newField = newField.getNextField();
        }
        return newField;
    }

    /**
     * Updates the target position of the piece on the game board based on the sum of
     * the rolled dice value.
     *
     * @param figurineToMove The Figurine whose target position should be updated.
     */
    private void updateTargetPosition(Figurine figurineToMove) {
        int sumOfDiceValues = figurineToMove.getDiceValues()[0] + figurineToMove.getDiceValues()[1];
        int newTargetPositionNumber = figurineToMove.getTargetPositionNumber() + sumOfDiceValues;

        figurineToMove.setTargetPositionNumber(newTargetPositionNumber);
    }

    @Override
    public String toString() {
        return "LuckField {position=" + getPositionNumber() + "}";
    }

}