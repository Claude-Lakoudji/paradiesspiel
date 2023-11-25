package com.paradise;

import com.paradise.enums.Color;
import com.paradise.fields.BridgeField;
import com.paradise.fields.Field;
import com.paradise.fields.LuckField;
import com.paradise.fields.ParadiseField;
import com.paradise.fields.UpturnField;
import com.paradise.interfaces.IParadiseGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class ParadiseGame implements IParadiseGame {
    private final List<Field> gameBoard = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final Dice numberDice = new Dice(6);
    private Dice colorDice;
    private Player currentPlayer;
    private static final String PLAYER = "Player";
    private static final Logger LOGGER = LoggerFactory.getLogger(ParadiseGame.class);

    public ParadiseGame(Color... colors) {
        initializeGameBoard();
        initializePlayers(colors);
        placePlayerFiguresOnStartField();
    }

    public ParadiseGame(String config, Color... colors) {
        initializeGameBoard();
        initializePlayers(colors);
        placePlayerFiguresWithConfigOnStartField(config);
    }

    public Color getColorOnTurn() {
        return currentPlayer.getColor();
    }

    public void setColorOnTurn(Color color) {
        currentPlayer = players.stream().filter(p -> p.getColor() == color).findFirst().orElse(null);
        if (currentPlayer == null) {
            LOGGER.info("Invalid color!");
        } else {
            LOGGER.info("{}{} is the current player.", PLAYER, currentPlayer.getColor());
        }
    }

    public int getCharacterPosition(String character) {
        for (Player player : players) {
            for (Figurine c : player.getCharacters()) {
                if (c.getName().equals(character)) {
                    return c.getPosition().getPositionNumber();
                }
            }
        }
        return -1;
    }

    public boolean moveCharacter(String character, int... diceRolls) {
        Figurine figurineToMove = getFigureByName(character);

        if (shouldNotMoveFigure(figurineToMove)) {
            return false;
        }

        int totalSteps = diceRolls[0] + diceRolls[1];
        figurineToMove.setDiceValues(diceRolls);
        Field currentPosition;

        for (int i = 1; i <= totalSteps; i++) {
            currentPosition = figurineToMove.getPosition();
            currentPosition.moveToNextOrPrev(figurineToMove);
        }

        return true;
    }

    public Color getWinner() {
        // Check if a player has all figures in Paradise
        for (Player player : players) {
            if (player.getNumberOfCharactersInParadise() == player.getCharacters().size()) {
                return player.getColor();
            }
        }
        // No winner found
        return null;
    }

    public Color[] getAllPlayers() {
        Color[] playerColors = new Color[players.size()];
        for (int i = 0; i < players.size(); i++) {
            playerColors[i] = players.get(i).getColor();
        }
        return playerColors;
    }

    /**
     * This method performs the main game loop until a winner is determined.
     */
    public void start() {
        // Start the game
        LOGGER.info("");
        LOGGER.info("The game is starting.");
        LOGGER.info("Determining the first player...");
        LOGGER.info("");

        // Determine the current player
        Color[] playerColors = getAllPlayers();
        int rolledColorIndex = colorDice.roll() - 1;
        setColorOnTurn(playerColors[rolledColorIndex]);
        LOGGER.info("{} {} begins.", getColorOnTurn(), PLAYER);

        Scanner scanner = new Scanner(System.in);

        // Play rounds
        boolean gameRunning = true;
        while (gameRunning) {
            LOGGER.info("");
            LOGGER.info("");
            LOGGER.info("");

            // Roll the dice
            int roll1 = numberDice.roll();
            int roll2 = numberDice.roll();
            int[] totalRoll = {roll1, roll2};

            LOGGER.info("Player {} rolls the dice: ", getColorOnTurn());
            LOGGER.info("{} and {}", roll1, roll2);

            // Move a figure
            String figureName = "";
            while (figureName.isEmpty()) {
                LOGGER.info("Which figure should be moved?");
                String input = scanner.nextLine().toUpperCase();
                if (!input.isEmpty()) {
                    figureName = input;
                }
            }
            LOGGER.info("");
            boolean successfullyMoved = moveCharacter(figureName, totalRoll);

            if (!successfullyMoved) {
                LOGGER.info("Figurine not found or couldn't be moved.");
            }

            // Check for game end
            Color winner = getWinner();
            if (winner != null) {
                LOGGER.info("{}{} has won!", PLAYER, winner);
                gameRunning = false;
                continue;
            }

            //Next player's turn
            int currentPlayerIndex = Arrays.asList(getAllPlayers()).indexOf(getColorOnTurn());
            Color nextColor = getAllPlayers()[(currentPlayerIndex + 1) % getAllPlayers().length];
            setColorOnTurn(nextColor);
        }

        scanner.close();

        // End the game
        LOGGER.info("The game is over.");
    }

    public String toString() {
        // Print figures
        StringBuilder figuresString = new StringBuilder("Figures:\n");
        for (Player player : players) {
            for (Figurine figurine : player.getCharacters()) {
                figuresString.append(String.format("%s: %d %n", figurine.getName(), figurine.getPosition().getPositionNumber()));
            }
        }

        // Print current player
        String currentPlayerString = String.format("Current"+PLAYER+" %s %n", currentPlayer.getColor());

        return figuresString + currentPlayerString;
    }

    /**
     * Initializes the game board with a sequence of fields, each representing a different type based on its position.
     * The method iterates over a fixed number of positions, initializes a field for each position, and then connects
     * each field with its previous field to form a linked structure.
     */
    private void initializeGameBoard() {
        for (int i = 0; i < 64; i++) {
            Field field = initField(i);
            connectCurrentFieldWithPrevious(i, field);
            this.gameBoard.add(field);
        }
    }

    /**
     * Initializes and returns a field based on the given position index.
     * The type of field is determined by the index: certain indices correspond to special types of fields,
     * like 'Misfortune', 'Bridge', 'Luck', etc. Default field type is used for indices without specific types.
     *
     * @param i The index representing the position on the game board.
     * @return A Field object corresponding to the given index.
     */
    private Field initField(int i) {
        return switch (i) {
            case 5, 9 -> new Field(i); // Misfortune
            case 6 -> new BridgeField(i);
            case 14, 18, 27, 32, 36, 50 -> new LuckField(i);
            case 19 -> new Field(i); // Labyrinth
            case 24, 41, 54 -> new Field(i); // Disaster
            case 52 -> new UpturnField(i);
            case 58 -> new Field(i); // New Beginning
            case 63 -> new ParadiseField(i);
            default -> new Field(i);
        };
    }

    /**
     * Connects the current field with its previous field if it's not the first field.
     * This method sets up a bidirectional link between the current field and the previous one,
     * effectively creating a linked structure of fields on the game board.
     *
     * @param i The index of the current field.
     * @param field The current field to be connected with its predecessor.
     */
    private void connectCurrentFieldWithPrevious(int i, Field field) {
        if (i > 0) {
            Field previousField = this.gameBoard.get(i - 1);
            field.setPreviousField(previousField);
            previousField.setNextField(field);
        }
    }

    /**
     * This method sets the players' figures on the start field.
     * @param colors the colors of the players' figures
     */
    private void initializePlayers(Color... colors) {
        colorDice = new Dice(colors.length);
        for (Color color : colors) {
            players.add(new Player(color));
        }
    }

    /**
     * This method sets the players' figures on the start field
     */
    private void placePlayerFiguresOnStartField() {
        for (Player player : players) {
            for (Figurine figurine : player.getCharacters()) {
                figurine.setPosition(gameBoard.get(0));
            }
        }
    }

    /**
     * This method sets the players' figures on the start field based on a
     * configuration.
     *
     * @param config The configuration of the figures
     */
    private void placePlayerFiguresWithConfigOnStartField(String config) {
        for (Player player : players) {
            for (Figurine figurine : player.getCharacters()) {
                figurine.setPosition(gameBoard.get(0));
            }
        }

        for (String figureConfig : config.split(", ")) {
            String[] parts = figureConfig.split(":");
            String name = parts[0];
            int position = Integer.parseInt(parts[1]);
            Figurine figurine = getFigureByName(name);
            if (figurine != null) {
                figurine.setPosition(gameBoard.get(position));
            }
        }
    }

    /**
     * This method searches for a figure with the given name.
     *
     * @param figureName The name of the figure
     * @return The figure with the given name, or null if no figure with the name
     *         was found.
     */
    private Figurine getFigureByName(String figureName) {
        for (Player player : players) {
            Optional<Figurine> optionalFigure = player.getCharacters().stream().filter(f -> f.getName().equals(figureName))
                    .findFirst();
            if (optionalFigure.isPresent()) {
                return optionalFigure.get();
            }
        }
        return null;
    }

    /**
     * This method checks if the figure should not be moved.
     *
     * @param figurineToMove The figure to be moved
     * @return true if the figure should not be moved, otherwise false
     */
    private boolean shouldNotMoveFigure(Figurine figurineToMove) {
        if (figurineToMove == null) {
            return true;
        }

        boolean wrongFigureSelected = !(figurineToMove.getColor().equals(currentPlayer.getColor()));
        boolean thereIsAWinner = (getWinner() != null);
        boolean figureIsAlreadyInParadise = figurineToMove.getPosition() instanceof ParadiseField;

        return (wrongFigureSelected || thereIsAWinner || figureIsAlreadyInParadise);
    }
}