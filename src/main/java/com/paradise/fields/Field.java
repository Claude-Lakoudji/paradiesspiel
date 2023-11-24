package com.paradise.fields;

import com.paradise.Figurine;

/**
 * The Field class represents a field on the game board. It is the
 * base class for all fields and implements basic functions for navigation on the game board.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class Field {
    private final int positionNumber;
    private Field previousField;
    private Field nextField;

    /**
     * Creates a new field with the specified position on the game board.
     *
     * @param positionNumber The position of the field on the game board.
     */
    public Field(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public Field getPreviousField() {
        return previousField;
    }

    public void setPreviousField(Field previousField) {
        this.previousField = previousField;
    }

    public Field getNextField() {
        return nextField;
    }

    public void setNextField(Field nextField) {
        this.nextField = nextField;
    }

    /**
     * Moves a figure to the next or previous position on the game board,
     * depending on the figure's movement direction.
     *
     * @param figurineToMove The figure to be moved on the game board.
     */
    public void moveToNextOrPrev(Figurine figurineToMove) {
        Field newPosition = calculateNewPosition(figurineToMove);
        figurineToMove.setPosition(newPosition);
        executeEventIfEventField(figurineToMove);
    }

    /**
     * Executes the event of an event field if the new position of the figure
     * is an event field.
     *
     * @param figurineToMove The figure that was moved on the game board.
     */
    public void executeEventIfEventField(Figurine figurineToMove) {
        if (figurineToMove.getPosition() instanceof EventField) {
            EventField eventField = (EventField) figurineToMove.getPosition();
            eventField.executeEvent(figurineToMove);
        }
    }

    /**
     * Calculates the new position of a figure based on its current movement direction.
     *
     * @param figurineToMove The figure for which the new position should be calculated.
     * @return The new position of the figure on the game board.
     */
    private Field calculateNewPosition(Figurine figurineToMove) {
        boolean forward = figurineToMove.forward();

        if (forward) {
            return this.getNextField();
        } else {
            return this.getPreviousField();
        }
    }

    @Override
    public String toString() {
        return "Field{" + "positionNumber=" + positionNumber + '}';
    }

}