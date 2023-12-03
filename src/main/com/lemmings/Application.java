package src.main.com.lemmings;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

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
        mainFrame.setLocationRelativeTo(null); // open in middle of screen
        mainFrame.add(gameView, BorderLayout.CENTER);
        
        mainFrame.pack(); // frame matches size of LevelView
        mainFrame.setVisible(true); // here we go!

    }

}