package com.paradise;

import main.java.com.paradise.ParadiseGame;
import main.java.com.paradise.enums.Color;
import main.java.com.paradise.interfaces.IParadiseGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ParadiseGameTest {
    private IParadiseGame game;

    @BeforeEach
    void setUp() {
       game = new ParadiseGame("BLUE-A:50, BLUE-B:50, YELLOW-A:50, YELLOW-A:50", Color.RED, Color.GREEN, Color.YELLOW);
    }

    @Test
    void moveCharacterFromParadise_shouldFailWhenColorNotOnTurn() {
        game.setColorOnTurn(Color.GREEN);
        assertFalse(game.moveCharacter("YELLOW-A", 6, 6), "Figur darf nicht bewegt werden, wenn der Spieler nicht am Zug ist.");
    }

    @Test
    void moveCharacterThatDoesNotExist_shouldReturnFalse() {
        game.setColorOnTurn(Color.GREEN);
        assertFalse(game.moveCharacter("NON_EXISTENT", 3, 4), "Bewege Figur Nicht Existiert Name sollte false zurückgeben.");
    }

    @Test
    void moveCharacterNotOwnedByPlayer_shouldReturnFalse() {
        game.setColorOnTurn(Color.YELLOW);
        assertFalse(game.moveCharacter("GRUEN-A", 3, 4), "Bewege Figur Nicht Vom Spieler sollte false zurückgeben.");
    }

    @Test
    void moveCharacterWithInvalidColor_shouldReturnFalse() {
        game.setColorOnTurn(Color.GREEN);
        assertFalse(game.moveCharacter("INVALID_COLOR-A", 3, 4), "Figur Startposition Standard Error Farbe sollte false zurückgeben.");
    }
}
