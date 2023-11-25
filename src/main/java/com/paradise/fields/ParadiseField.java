package com.paradise.fields;

import com.paradise.Figurine;

/**
 * The ParadiseField class represents a special field on the game board where a piece can land.
 * However, ParadiseField can only be reached with a direct roll of the dice. If the rolled number
 * is higher than what is needed to enter Paradise, the piece must move the remaining steps
 * back from Paradise.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public class ParadiseField extends Field {

    /**
     * Constructor for the Paradise class.
     *
     * @param position The position of the Paradise field on the game board.
     */
    public ParadiseField(int position) {
        super(position);
    }

    /**
     * Moves the piece to the previous field if it goes beyond the Paradise field
     * and updates the piece's target position accordingly.
     *
     * @param figurine figurine to be moved.
     *
     */
    @Override
    public void moveToNextOrPrev(Figurine figurine) {
        int stepsBeyondParadise = figurine.getTargetPositionNumber() - this.getPositionNumber();
        if (stepsBeyondParadise > 0) {
            figurine.setTargetPositionNumber(this.getPositionNumber() - stepsBeyondParadise);
            figurine.setPosition(this.getPreviousField());
        }
    }

    @Override
    public String toString() {
        return "ParadiseField {position=" + getPositionNumber() + "}";
    }
}