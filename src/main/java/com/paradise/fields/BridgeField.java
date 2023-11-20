package com.paradise.fields;

import com.paradise.Figurine;

/**
 * The BridgeField class represents an event field with a bridge.
 * When a figure lands on this field, it is moved six fields forward,
 * and the event of the destination field is executed if it is an event field.
 * This class inherits from the EventField class.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class BridgeField extends EventField {

    /**
     * Constructor for BridgeField. Takes the position as an argument.
     *
     * @param position The position of the field on the game board.
     */
    public BridgeField(int position) {
        super(position);
    }

    /**
     * Executes the event on the BridgeField by moving the figure six fields forward
     * and executing the event of the destination field if it is an event field.
     *
     * @param figurineToMove The character located on the BridgeField.
     */
    @Override
    public void executeEvent(Figurine figurineToMove) {
        if (shouldExecuteEvent(figurineToMove)) {
            moveSixFieldsForward(figurineToMove);
            int numberOfFieldsToTraverse = 6;
            updateTargetPosition(figurineToMove, numberOfFieldsToTraverse);
            executeEventIfEventField(figurineToMove);
        }
    }

    /**
     * Moves the figure either to the next or previous field. Executes the
     * BridgeField event if the figure is moving forward.
     *
     * @param figurineToMove The character to be moved.
     */
    @Override
    public void moveToNextOrPrev(Figurine figurineToMove) {
        if (figurineToMove.forward()) {
            this.executeEvent(figurineToMove);
        } else {
            figurineToMove.setPosition(this.getPreviousField());
        }
    }

    /**
     * Checks whether the event on the BridgeField should be executed for the given figure.
     *
     * @param figurineToMove The character located on the BridgeField.
     * @return true if the event should be executed, otherwise false.
     */
    private boolean shouldExecuteEvent(Figurine figurineToMove) {
        boolean forward = figurineToMove.forward();
        boolean bridgeFieldIsCurrentPosition = figurineToMove.getTargetPositionNumber() == this.getPositionNumber();

        return forward || bridgeFieldIsCurrentPosition;
    }

    /**
     * Moves the figure six fields forward.
     *
     * @param figurineToMove The figure to jump six fields forward.
     */
    private void moveSixFieldsForward(Figurine figurineToMove) {
        Field newField = this.getNextField();
        for (int i = 0; i < 5; i++) {
            newField = newField.getNextField();
        }
        figurineToMove.setPosition(newField);
    }

    /**
     * Updates the target position of the figure by the specified number of fields.
     *
     * @param figurineToMove The character whose target position needs to be updated.
     * @param numberOfFields The number of fields to update the target position.
     */
    private void updateTargetPosition(Figurine figurineToMove, int numberOfFields) {
        int newTargetPositionNumber = figurineToMove.getTargetPositionNumber() + numberOfFields;
        figurineToMove.setTargetPositionNumber(newTargetPositionNumber);
    }

    /**
     * Returns a string representation of the BridgeField object.
     *
     * @return A string representation of the BridgeField object.
     */
    @Override
    public String toString() {
        return "BridgeField{position=" + getPositionNumber() + "}";
    }

}