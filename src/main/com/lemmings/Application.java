package src.main.com.lemmings;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import src.main.com.lemmings.Controllers.LevelController;
import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Models.MenuOptions;
import src.main.com.lemmings.Views.LevelView;
import src.main.com.lemmings.utilities.Utilities;

/**
 * Application.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       The main class for the Lemmings REVENGE! game application.
 *       This class sets up the game environment, including the view, game
 *       state, menu options,
 *       and the level controller. It initializes and displays the main game
 *       window.
 */
public class Application {
    // Create shared components
    private LevelView levelView = new LevelView();
    private GameState gameState = new GameState();
    private MenuOptions menuOptions = new MenuOptions();

    /**
     * Main method to launch the Lemmings REVENGE! game.
     * Initializes the game levels and starts the application.
     * 
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Utilities.createLevels();
        new Application();
    }

    /**
     * Constructs the Application.
     * Sets up the game by initializing the level view, game state, and menu
     * options.
     * It creates and configures the main game window.
     */
    public Application() {

        // Create controller
        new LevelController(levelView, gameState, menuOptions);

        JFrame mainFrame = new JFrame("Lemmings REVENGE!");

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null); // open in middle of screen
        mainFrame.add(levelView, BorderLayout.CENTER);

        mainFrame.pack(); // frame matches size of LevelView
        mainFrame.setVisible(true); // here we go!

    }
}