package com.paradise.fields;

import com.paradise.Figurine;

/**
 * The UpturnField class represents an event field where a figure, if it meets a specific condition,
 * can directly reach the Paradise field. The condition is that the figure rolls two sixes.
 * This class inherits from the EventField class.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class UpturnField extends EventField {

    /**
     * Creates a new UpturnField with the specified position.
     *
     * @param position The position of the field on the game board.
     */
    public UpturnField(int position) {
        super(position);
    }

    /**
     * Executes the event when the figurine is on the Ascension field and has rolled two
     * sixes. In this case, the figurine is moved directly to the Paradise field.
     *
     * @param figurine The figurine triggering the event.
     */
    @Override
    public void executeEvent(Figurine figurine) {
        if (shouldMoveToParadise(figurine)) {
            Field paradiseField = findParadiseField();
            figurine.setPosition(paradiseField);
        }
    }

    /**
     * Moves the figurine to the next or previous field.
     *
     * @param figurine The figurine being moved.
     */
    @Override
    public void moveToNextOrPrev(Figurine figurine) {
        if (figurine.forward()) {
            figurine.setPosition(this.getNextField());
        } else {
            figurine.setPosition(this.getPreviousField());
        }
    }

    /**
     * Checks if the figurine should reach the Paradise field.
     *
     * @param figurine The figurine being checked.
     * @return true if the figurine should reach the Paradise field, otherwise false.
     */
    private boolean shouldMoveToParadise(Figurine figurine) {
        boolean figureCanAscend = figurine.getDiceValues()[0] == 6 && figurine.getDiceValues()[1] == 6;
        boolean ascensionFieldIsTargetField = figurine.getTargetPositionNumber() == this.getPositionNumber();

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
     * Returns a string representation of the UpturnField object.
     *
     * @return A string representation of the UpturnField object.
     */
    @Override
    public String toString() {
        return "UpturnField{" +
                "position=" + getPositionNumber() +
                '}';
    }
}