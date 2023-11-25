package com.paradise.fields;
import com.paradise.Figurine;

/**
 * The EventField class represents an event field on the game board.
 * It is the base class for all fields that trigger special events.
 *
 * @author Claude Lakoudji
 * @version 0.1.0
 */
public abstract class EventField extends Field {

    /**
     * Creates a new event field with the specified position on the game board.
     *
     * @param position The position of the event field on the game board.
     */
    EventField(int position) {
        super(position);
    }

    /**
     * Executes the event for the specified figurine standing on this field.
     * This method should be overridden in subclasses to implement the specific
     * behavior for each type of event field.
     *
     * @param figurine The figurine for which the event on the field should be executed.
     */
    public void executeEvent(Figurine figurine) {
    }

    @Override
    public String toString() {
        return "EventField{" +
                "position=" + this.getPositionNumber() +
                '}';
    }

}