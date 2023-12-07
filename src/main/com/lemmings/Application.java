package src.main.com.lemmings;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import src.main.com.lemmings.Controllers.LevelController;
import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Models.MenuOptions;
import src.main.com.lemmings.Views.LevelView;
import src.main.com.lemmings.utilities.Utilities;

/**
 * Application.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 */
public class Application {
    // Create shared components
    private LevelView levelView = new LevelView();
    private GameState gameState = new GameState();
    private MenuOptions menuOptions = new MenuOptions();

    public static void main(String[] args) {
        Utilities.createLevels();
        new Application();
    }

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