package main.java.com.paradise;

import main.java.com.paradise.enums.Color;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The main program that initializes and starts the Paradise Game.
 *
 * @author Group036
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Paradise Game!");

        Scanner scanner = new Scanner(System.in);

        int numberOfPlayers = requestNumberOfPlayers(scanner);
        Set<Color> usedColors = new HashSet<>();
        Color[] selectedColors = selectColors(scanner, numberOfPlayers, usedColors);
        displaySelectedColors(numberOfPlayers, selectedColors);
        ParadiseGame game = new ParadiseGame("BLUE-A:50, BLUE-B:50, YELLOW-A:50, YELLOW-A:50", selectedColors);
        game.start();
    }

    /**
     * This method prompts the user for the number of players and returns it.
     * @param scanner Scanner object for user input
     * @return The number of players
     */
    private static int requestNumberOfPlayers(Scanner scanner) {
        int numberOfPlayers = 0;
        while (numberOfPlayers < 2 || numberOfPlayers > 6) {
            System.out.print("Please enter the number of players (between 2 and 6): ");
            numberOfPlayers = scanner.nextInt();
        }
        return numberOfPlayers;
    }

    /**
     * This method allows each player to select a color and returns an array with the selected colors.
     * @param scanner Scanner object for user input
     * @param numberOfPlayers Number of players
     * @param usedColors Set of already used colors
     * @return An array with the selected colors
     */
    private static Color[] selectColors(Scanner scanner, int numberOfPlayers, Set<Color> usedColors) {
        Color[] selectedColors = new Color[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println();
            System.out.println("Player " + (i + 1) + ", choose a color:");
            System.out.print("Available colors: ");
            for (Color color : Color.values()) {
                if (!usedColors.contains(color)) {
                    System.out.print(color.name() + " ");
                }
            }
            System.out.println();

            Color selectedColor = null;
            while (selectedColor == null) {
                System.out.print("Your choice: ");
                String colorString = scanner.next();
                try {
                    selectedColor = Color.valueOf(colorString.toUpperCase());
                    if (usedColors.contains(selectedColor)) {
                        System.out.println("Color already chosen. Please choose a different color.");
                        selectedColor = null;
                    } else {
                        usedColors.add(selectedColor);
                        selectedColors[i] = selectedColor;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("This color does not exist. Please choose a color from the list.");
                }
            }
        }
        return selectedColors;
    }

    /**
     * This method displays the colors selected by the user.
     * @param numberOfPlayers Number of players
     * @param selectedColors An array with the selected colors
     */
    private static void displaySelectedColors(int numberOfPlayers, Color[] selectedColors) {
        System.out.println();
        System.out.println("The following colors have been chosen:");
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Player " + (i + 1) + ": " + selectedColors[i].name());
        }
    }
}