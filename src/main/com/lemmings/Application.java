package src.main.com.lemmings;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import src.main.com.lemmings.Controllers.LevelController;
import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Models.MenuOptions;
import src.main.com.lemmings.Views.LevelView;

/**
 * Application.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 */
public class Application {

    public static void main(String[] args) {

        // Create shared components
        LevelView levelView = new LevelView();
        LevelModel levelModel = new LevelModel();
        GameState gameState = new GameState();
        MenuOptions menuOptions = new MenuOptions();

        // Create controller
        new LevelController(levelView, levelModel, gameState, menuOptions);

        JFrame mainFrame = new JFrame("Lemmings REVENGE!");

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null); // open in middle of screen
        mainFrame.add(levelView, BorderLayout.CENTER);

        mainFrame.pack(); // frame matches size of LevelView
        mainFrame.setVisible(true); // here we go!

    }

}