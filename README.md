# Paradise-Game

## Overview
"Paradise" is a Java-based board game for 2 to 6 players, inspired by the traditional "Game of the Goose". The objective is to be the first player to move all their pieces to Paradise, located at the end of the board.

## Game Setup
- Each player chooses a color and receives two pieces of that color.
- The game board consists of 64 squares, including a Start field, Paradise, various Event fields, and standard fields.
- All pieces start at field number 0 but can be placed on any field if required.

## Playing the Game
- The game is played with two dice: a color die to decide which player takes a turn and a number die for moving the pieces.
- On their turn, a player first rolls the color die to determine who plays next, followed by rolling the number die twice. The sum of these rolls indicates the number of squares a piece should move forward.
- Players choose one of their pieces to move based on the dice outcome.
- When a piece lands on an Event field, the corresponding event is triggered.

## Event Fields
- **Misfortune**: The piece moves backward by the number rolled.
- **Bridge**: The piece may skip 6 fields forward, not counting it as a step.
- **Luck**: The piece moves forward by the number rolled.
- **Labyrinth**: The player skips a turn.
- **Disaster**: The piece moves backward double the number rolled.
- **AscensionField**: If reached with double sixes, the piece moves directly to Paradise.
- **Restart**: The piece returns to the Start field.
- **Paradise**: Reached only by an exact roll. If the roll is higher, the piece moves back.

## Winning the Game
- The first player to move all their pieces to Paradise wins.

## Running the Application
To start the "Paradise" game:
1. Ensure you have Java installed on your system.
2. Download the game files from the repository.
3. Navigate to the directory where the game files are located using a command-line interface.
4. Run the game using the command `java -jar ParadiseGame.jar`.
5. Follow the on-screen instructions to play the game.