package com.paradise;

import com.paradise.enums.Color;
import com.paradise.fields.BridgeField;
import com.paradise.fields.Field;
import com.paradise.fields.LuckField;
import com.paradise.fields.ParadiseField;
import com.paradise.fields.UpturnField;
import com.paradise.interfaces.IParadiseGame;

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
            System.out.println("Invalid color!");
        } else {
            System.out.println("Player " + currentPlayer.getColor() + " is the current player.");
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
        System.out.println("");
        System.out.println("The game is starting.");
        System.out.println("Determining the first player...");
        System.out.println("");

        // Determine the current player
        Color[] playerColors = getAllPlayers();
        int rolledColorIndex = colorDice.roll() - 1;
        setColorOnTurn(playerColors[rolledColorIndex]);
        System.out.println("Player " + getColorOnTurn() + " begins.");

        Scanner scanner = new Scanner(System.in);

        // Play rounds
        boolean gameRunning = true;
        while (gameRunning) {
            System.out.println();
            System.out.println(toString());
            System.out.println();

            // Roll the dice
            int roll1 = numberDice.roll();
            int roll2 = numberDice.roll();
            int[] totalRoll = {roll1, roll2};

            System.out.println("Player " + getColorOnTurn() + " rolls the dice: ");
            System.out.println(roll1 + " and " + roll2);

            // Move a figure
            String figureName = "";
            while (figureName.isEmpty()) {
                System.out.println("Which figure should be moved?");
                String input = scanner.nextLine().toUpperCase();
                if (!input.isEmpty()) {
                    figureName = input;
                }
            }
            System.out.println("");
            boolean successfullyMoved = moveCharacter(figureName, totalRoll);

            if (!successfullyMoved) {
                System.out.println("Figurine not found or couldn't be moved.");
            }

            // Check for game end
            Color winner = getWinner();
            if (winner != null) {
                System.out.println("Player " + winner + " has won!");
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
        System.out.println("The game is over.");
    }

    public String toString() {
        // Print the game board
        /*
         * String gameBoardString = "Game Board:\n"; for (int i = 0; i < 64; i++) {
         * gameBoardString += String.format("%02d: %s\n", i,
         * gameBoard[i].getDescription()); }
         */

        // Print figures
        StringBuilder figuresString = new StringBuilder("Figures:\n");
        for (Player player : players) {
            for (Figurine figurine : player.getCharacters()) {
                figuresString.append(String.format("%s: %d\n", figurine.getName(), figurine.getPosition().getPositionNumber()));
            }
        }

        // Print current player
        String currentPlayerString = String.format("Current Player: %s\n", currentPlayer.getColor());

        return figuresString + currentPlayerString;
    }

    /**
     * This method initializes the game board with the corresponding field types and
     * connects them.
     */
    private void initializeGameBoard() {
        for (int i = 0; i < 64; i++) {
            Field field;
            if (i == 5 || i == 9) {
                field = new Field(i); // Misfortune TODO
            } else if (i == 6) {
                field = new BridgeField(i);
            } else if (i == 14 || i == 18 || i == 27 || i == 32 || i == 36 || i == 50) {
                field = new LuckField(i);
            } else if (i == 19) {
                field = new Field(i); // Labyrinth TODO
            } else if (i == 24 || i == 41 || i == 54) {
                field = new Field(i); // Disaster TODO
            } else if (i == 52) {
                field = new UpturnField(i);
            } else if (i == 58) {
                field = new Field(i); // New Beginning TODO
            } else if (i == 63) {
                field = new ParadiseField(i);
            } else {
                field = new Field(i);
            }

            // Connect the current field with the previous field
            if (i > 0) {
                Field previousField = this.gameBoard.get(i - 1);
                field.setPreviousField(previousField);
                previousField.setNextField(field);
            }

            this.gameBoard.add(field);
        }
    }

    /**
     * This method sets the players' figures on the start field.
     * @param colors the colors of the players'figures
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