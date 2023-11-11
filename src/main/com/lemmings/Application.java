package src.main.com.lemmings;

import javax.swing.JFrame;

import src.main.com.lemmings.Controllers.LevelController;
import src.main.com.lemmings.Views.LevelView;

/**
 * Application.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 */
public class Application {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("Lemmings REVENGE!");
        
        LevelView gameView = new LevelView();
        LevelController gameController = new LevelController(gameView);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(gameView);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }
    
}