package com.paradise;
import com.paradise.enums.Color;
import com.paradise.interfaces.IParadiseGame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class ParadiseGameTest {
    private final IParadiseGame game = new ParadiseGame(
            "BLUE-A:0, BLUE-B:62, YELLOW-A:45, YELLOW-B:40, BLACK-A:60, BLACK-B:63, GREEN-A:12, GREEN-B:10",
            Color.BLUE, Color.YELLOW, Color.BLACK, Color.GREEN
    );

    @Test
    void doNotMoveWrongPlayersFigurine() {
        game.setColorOnTurn(Color.BLUE);
        boolean figurineYellowCanBeMove = game.moveCharacter("YELLOW-A");
        assertFalse(figurineYellowCanBeMove, "Only the figurine of the player whose turn it is may be moved!");
    }

    @Test
    void generate6StandardPlayerFigures() {
        ParadiseGame standardGame = new ParadiseGame(
                Color.RED, Color.WHITE, Color.BLUE, Color.YELLOW, Color.BLACK, Color.GREEN
        );
        Color[] colors = standardGame.getAllPlayers();
        assertEquals(6, colors.length, "The number of players should be exactly the same as the number of colors chosen at the start of the game.");

        assertInitialPosition(standardGame, "BLUE-A", "BLUE-B", "RED-A", "RED-B", "WHITE-A", "WHITE-B");
        assertInitialPosition(standardGame, "GREEN-A", "GREEN-B", "BLACK-A", "BLACK-B", "YELLOW-A", "YELLOW-B");
    }

    @Test
    void bridgeNotTarget() {
        game.setColorOnTurn(Color.BLUE);
        game.moveCharacter("BLUE-A", 6, 5);
        assertCharacterPosition("BLUE-A", 17);
    }

    @Test
    void moveFigureParadiseBack() {
        game.setColorOnTurn(Color.BLACK);
        game.moveCharacter("BLACK-A", 6, 5);
        assertCharacterPosition("BLACK-A", 55);
    }
    @Test
    void moveFigureExists() {
        game.setColorOnTurn(Color.BLUE);
        boolean figureCanBeMoved = game.moveCharacter("BLUE-A", 2, 1);
        assertCharacterPosition("BLUE-A", 3);
        assertTrue(figureCanBeMoved, "The figurine should have been moved!");
    }
    @Test
    void generate3StandardPlayerFigures() {
        ParadiseGame standardGame = new ParadiseGame(Color.RED, Color.WHITE, Color.BLUE);
        Color[] colors = standardGame.getAllPlayers();
        assertEquals(3, colors.length, "The number of players should be exactly the same as the number of colors chosen at the start of the game.");

        assertInitialPosition(standardGame, "BLUE-A", "BLUE-B", "RED-A", "RED-B", "WHITE-A", "WHITE-B");
    }
    @Test
    void upturnFrom62IntoParadise() {
        game.setColorOnTurn(Color.BLUE);
        game.moveCharacter("BLUE-B", 6, 6);
        assertCharacterPosition("BLUE-B", 63);
    }

    @Test
    void upturnWith7NotIntoParadise() {
        game.setColorOnTurn(Color.YELLOW);
        game.moveCharacter("YELLOW-A", 4, 3);
        assertCharacterPosition("YELLOW-A", 52);
    }

    @Test
    void upturnWith12IntoParadise() {
        game.setColorOnTurn(Color.YELLOW);
        game.moveCharacter("YELLOW-B", 6, 6);
        assertCharacterPosition("YELLOW-B", 63);
    }

    @Test
    void figureStartPositionConfigured() {
        assertCharacterPosition("BLUE-A", 0);
        assertCharacterPosition("BLUE-B", 62);
        assertCharacterPosition("YELLOW-A", 45);
        assertCharacterPosition("YELLOW-B", 40);
        assertCharacterPosition("BLACK-A", 60);
        assertCharacterPosition("BLACK-B", 63);
        assertCharacterPosition("GREEN-A", 12);
        assertCharacterPosition("GREEN-B", 10);
    }
    @Test
    void moveFigureWhenNameDoesNotExistInTheGame() {
        game.setColorOnTurn(Color.BLUE);
        boolean figureHasBeenMoved = game.moveCharacter("BLUE-H", 2, 6);
        assertFalse(figureHasBeenMoved, "This figure's name does not exist in the game and normally should not be able to be moved");
    }
    @Test
    void turnForPlayerBlack() {
        game.setColorOnTurn(Color.BLACK);
        assertEquals(Color.BLACK, game.getColorOnTurn(), "The player with the black color should be on turn.");
    }
    @Test
    void moveFigureOutOfParadise() {
        game.setColorOnTurn(Color.BLACK);
        boolean figureCanBeMoved = game.moveCharacter("BLACK-B", 2, 6);
        assertFalse(figureCanBeMoved, "The figure may not be moved if it is already in Paradise");
    }
    @Test
    void turnForPlayerYellow() {
        game.setColorOnTurn(Color.YELLOW);
        assertEquals(Color.YELLOW, game.getColorOnTurn(), "The player with the yellow color should be on turn.");
    }
    @Test
    void BridgeFieldIsTheTargetField() {
        game.setColorOnTurn(Color.BLUE);
        game.moveCharacter("BLUE-A", 3, 3);
        assertEquals(12, game.getCharacterPosition("BLUE-A"), "When a figure lands on a bridge-field, it must be advanced by 6");
    }
    @Test
    void luck2x() {
        game.setColorOnTurn(Color.GREEN);
        game.moveCharacter("GREEN-B", 2, 2);
        assertEquals(22, game.getCharacterPosition("GREEN-B"), "When a figure lands on a luck-field, it must be advanced by the numbers rolled");
    }
    @Test
    void turnForPlayerBlue() {
        game.setColorOnTurn(Color.BLUE);
        assertEquals(Color.BLUE, game.getColorOnTurn(), "The player with the blue color should be on turn.");
    }
    @Test
    void moveFigureWhenColorDoesNotExist() {
        game.setColorOnTurn(Color.BLUE);
        boolean figureHasBeenMoved = game.moveCharacter("RED-A", 2, 6);
        assertFalse(figureHasBeenMoved, "This player's color does not exist in the game and normally should not be able to be moved");
    }
    @Test
    void luck1x() {
        game.setColorOnTurn(Color.GREEN);
        game.moveCharacter("GREEN-A", 1, 1);
        assertEquals( 16, game.getCharacterPosition("GREEN-A"), "When a figure lands on a luck-field, it must be advanced by the numbers rolled");
    }
    @Test
    void moveFigureStayInParadise() {
        game.setColorOnTurn(Color.BLACK);
        boolean figureCanBeMoved = game.moveCharacter("BLACK-B", 1, 2);
        assertFalse(figureCanBeMoved, "A figure in paradise can not be moved anymore");
    }

    @Test
    void determineWinner() {
        game.setColorOnTurn(Color.BLACK);
        game.moveCharacter("BLACK-A", 1, 2);
        Color winner = game.getWinner();
        assertEquals(Color.BLACK, winner, "The Player with the color 'BLACK' should be the winner!");
    }
    @Test
    void bridgeWithLuck() {
        game.setColorOnTurn(Color.BLUE);
        game.moveCharacter("BLUE-A", 3, 5);
        assertEquals(22, game.getCharacterPosition("BLUE-A"), "When a character crosses the bridge, they should advance 6 spaces. If they land on a Lucky Square, they should move forward by the number rolled on the dice");
    }
    @Test
    void moveFigureToEnd() {
        game.setColorOnTurn(Color.BLACK);
        game.moveCharacter("BLACK-A", 1, 2);
        Color winner = game.getWinner();
        assertEquals( Color.BLACK, winner, "The correct color of the winner should be displayed.");
        game.setColorOnTurn(Color.BLUE);
        game.moveCharacter("BLUE-A", 1, 2);
        assertEquals(0, game.getCharacterPosition("BLUE-A"), "No figure may be moved after the end of the game.");
    }

    private void assertInitialPosition(IParadiseGame game, String... figureNames) {
        for (String figureName : figureNames) {
            assertEquals(0, game.getCharacterPosition(figureName), "The colors selected at the start of the game should be reusable in the game logic.");
        }
    }

    private void assertCharacterPosition(String figureName, int expectedPosition) {
        int position = game.getCharacterPosition(figureName);
        assertEquals(expectedPosition, position, "The position of the game piece on the board should be accurate.");
    }
}
