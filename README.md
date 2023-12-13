# Java Lemmings REVENGE!

## I. Objective

This program is a modern take on the classic game "Lemmings", originally published by Psygnosis for the Amiga system in 1991. Written in Java, it utilizes a SWING GUI interface to bring a nostalgic yet fresh experience to players. 

### Game Description

In this game, players are tasked with guiding a set number of characters from the level entrance to the exit. These characters are autonomous, following programmed behaviors like moving horizontally and changing direction upon encountering obstacles. 

Players can assign different "skills" to each character, allowing them to modify the environment or influence other characters' behaviors. The strategic use of these skills is crucial to navigate through various level obstacles, paving the way for characters to reach the exit and complete the level.

## II. Motivation

This project serves as a demonstration of basic game design using Java and Swing. It leverages several Swing GUI components to craft the game window, menus, and dialogues, offering instructions, hints, and level selection.

Key Highlights:
- Custom renderings and event handling.
- Advanced collision detection and autonomous character animation.
- Interactive gameplay through mouse listener events.

## III. Running the Program

To run this game, follow these simple steps:

1. Open a terminal window and navigate to the top-level directory of the program.
2. Compile the code using the Java compiler:
```bash
   javac -d bin src/**/*.java
```
3. Once compiled, start the game by running:
```bash
    java -cp bin src.main.com.lemmings.Application
```
## IV. Contributions

Contributions to this project are welcome! Whether it's improving the code, suggesting new features, or reporting bugs, your input is valuable. Please refer to CONTRIBUTING.md for guidelines on how to contribute.

## V. License

This project is licensed under the [INSERT LICENSE HERE] License - see the LICENSE.md file for details.

## VI. Acknowledgments

A special thanks to the original creators of "Lemmings" for inspiring this project.
