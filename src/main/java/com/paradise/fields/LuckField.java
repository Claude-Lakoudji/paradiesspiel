package main.java.com.paradise.fields;

import main.java.com.paradise.Figure;

/**
 * The LuckField class represents an event field where a piece can change its
 * position on the game board according to the rolled dice value.
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
     * @param figureToMove The character that has landed on the LuckField.
     */
    @Override
    public void executeEvent(Figure figureToMove) {
        if (figureToMove.getTargetPositionNumber() == this.getPositionNumber()) {
            Field newField = calculateNewField(figureToMove);
            figureToMove.setPosition(newField);
            updateTargetPosition(figureToMove);
            executeEventIfEventField(figureToMove);
        }
    }

    /**
     * Moves the piece to the next or previous position depending on its
     * movement direction.
     *
     * @param figureToMove The character to be moved on the game board.
     */
    @Override
    public void moveToNextOrPrev(Figure figureToMove) {
        boolean forward = figureToMove.forward();
        Field newField = forward ? this.getNextField() : this.getPreviousField();
        figureToMove.setPosition(newField);
    }

    /**
     * Calculates the new field for the piece based on the sum of the rolled dice value.
     *
     * @param figureToMove The character for which the new position should be calculated.
     * @return The new field where the piece will be placed.
     */
    private Field calculateNewField(Figure figureToMove) {
        Field newField = this.getNextField();
        int stepsToTarget = figureToMove.getDiceValues()[0] + figureToMove.getDiceValues()[1];
        for (int i = 1; i < stepsToTarget; i++) {
            newField = newField.getNextField();
        }
        return newField;
    }

    /**
     * Updates the target position of the piece on the game board based on the sum of
     * the rolled dice value.
     *
     * @param figureToMove The Figure whose target position should be updated.
     */
    private void updateTargetPosition(Figure figureToMove) {
        int sumOfDiceValues = figureToMove.getDiceValues()[0] + figureToMove.getDiceValues()[1];
        int newTargetPositionNumber = figureToMove.getTargetPositionNumber() + sumOfDiceValues;

        figureToMove.setTargetPositionNumber(newTargetPositionNumber);
    }

    @Override
    public String toString() {
        return "LuckField {position=" + getPositionNumber() + "}";
    }

}