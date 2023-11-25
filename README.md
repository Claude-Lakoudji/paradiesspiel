# Paradise-Game

## Overview
"Paradise" is a Java-based board game for 2 to 6 players, inspired by the traditional "Game of the Goose". The objective is to be the first player to move all their pieces to Paradise, located at the end of the board.

## Running the Application
To start the "Paradise" game:
1. Ensure you have Java and Maven installed on your system.
2. Clone the game repository to your local machine.
3. Navigate to the project directory containing the `pom.xml` file using a command-line interface.
4. Build and install the game using the command `mvn install`.
5. After a successful build, you can run the game using the command `mvn exec:java`.
6. Follow the on-screen instructions to play the game.

## Running the Application with Docker
You can also run the "Paradise" game using Docker. Follow these steps:

1. Ensure you have Docker installed on your system.
2. Clone the game repository to your local machine.
3. Navigate to the project directory containing the Dockerfile.
4. Build the Docker image using the following command: `docker build -t paradisegame .`
5. Run the Docker container with the following command: `docker run -it paradisegame`
6. Follow the on-screen instructions to play the game.

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