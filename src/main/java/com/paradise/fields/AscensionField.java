package com.paradise.fields;

import com.paradise.Figure;

/**
 * The AscensionField class represents an event field where a figure, if it meets a specific condition,
 * can directly reach the Paradise field. The condition is that the figure rolls two sixes.
 * This class inherits from the EventField class.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class AscensionField extends EventField {

    /**
     * Creates a new AscensionField with the specified position.
     *
     * @param position The position of the field on the game board.
     */
    public AscensionField(int position) {
        super(position);
    }

    /**
     * Executes the event when the figure is on the Ascension field and has rolled two
     * sixes. In this case, the figure is moved directly to the Paradise field.
     *
     * @param figure The figure triggering the event.
     */
    @Override
    public void executeEvent(Figure figure) {
        if (shouldMoveToParadise(figure)) {
            Field paradiseField = findParadiseField();
            figure.setPosition(paradiseField);
        }
    }

    /**
     * Moves the figure to the next or previous field.
     *
     * @param figure The figure being moved.
     */
    @Override
    public void moveToNextOrPrev(Figure figure) {
        if (figure.forward()) {
            figure.setPosition(this.getNextField());
        } else {
            figure.setPosition(this.getPreviousField());
        }
    }

    /**
     * Checks if the figure should reach the Paradise field.
     *
     * @param figure The figure being checked.
     * @return true if the figure should reach the Paradise field, otherwise false.
     */
    private boolean shouldMoveToParadise(Figure figure) {
        boolean figureCanAscend = figure.getDiceValues()[0] == 6 && figure.getDiceValues()[1] == 6;
        boolean ascensionFieldIsTargetField = figure.getTargetPositionNumber() == this.getPositionNumber();

        return ascensionFieldIsTargetField && figureCanAscend;
    }

    /**
     * Finds the Paradise field on the game board.
     *
     * @return The Paradise field.
     */
    private Field findParadiseField() {
        Field newField = this.getNextField();
        while (!(newField instanceof ParadiseField)) {
            newField = newField.getNextField();
        }
        return newField;
    }

    /**
     * Returns a string representation of the AscensionField object.
     *
     * @return A string representation of the AscensionField object.
     */
    @Override
    public String toString() {
        return "AscensionField{" +
                "position=" + getPositionNumber() +
                '}';
    }
}