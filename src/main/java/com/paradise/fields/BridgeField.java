package main.java.com.paradise.fields;

import main.java.com.paradise.Figure;

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
     * @param figureToMove The character located on the BridgeField.
     */
    @Override
    public void executeEvent(Figure figureToMove) {
        if (shouldExecuteEvent(figureToMove)) {
            moveSixFieldsForward(figureToMove);
            int numberOfFieldsToTraverse = 6;
            updateTargetPosition(figureToMove, numberOfFieldsToTraverse);
            executeEventIfEventField(figureToMove);
        }
    }

    /**
     * Moves the figure either to the next or previous field. Executes the
     * BridgeField event if the figure is moving forward.
     *
     * @param figureToMove The character to be moved.
     */
    @Override
    public void moveToNextOrPrev(Figure figureToMove) {
        if (figureToMove.forward()) {
            this.executeEvent(figureToMove);
        } else {
            figureToMove.setPosition(this.getPreviousField());
        }
    }

    /**
     * Checks whether the event on the BridgeField should be executed for the given figure.
     *
     * @param figureToMove The character located on the BridgeField.
     * @return true if the event should be executed, otherwise false.
     */
    private boolean shouldExecuteEvent(Figure figureToMove) {
        boolean forward = figureToMove.forward();
        boolean bridgeFieldIsCurrentPosition = figureToMove.getTargetPositionNumber() == this.getPositionNumber();

        return forward || bridgeFieldIsCurrentPosition;
    }

    /**
     * Moves the figure six fields forward.
     *
     * @param figureToMove The figure to jump six fields forward.
     */
    private void moveSixFieldsForward(Figure figureToMove) {
        Field newField = this.getNextField();
        for (int i = 0; i < 5; i++) {
            newField = newField.getNextField();
        }
        figureToMove.setPosition(newField);
    }

    /**
     * Updates the target position of the figure by the specified number of fields.
     *
     * @param figureToMove The character whose target position needs to be updated.
     * @param numberOfFields The number of fields to update the target position.
     */
    private void updateTargetPosition(Figure figureToMove, int numberOfFields) {
        int newTargetPositionNumber = figureToMove.getTargetPositionNumber() + numberOfFields;
        figureToMove.setTargetPositionNumber(newTargetPositionNumber);
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