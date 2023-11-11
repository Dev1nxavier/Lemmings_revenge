package src.main.com.lemmings;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.itextpdf.layout.element.Image;

import src.main.com.lemmings.Controllers.CharacterController;
import src.main.com.lemmings.Controllers.LevelController;
import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.Lemming;
import src.main.com.lemmings.Views.CharacterView;
import src.main.com.lemmings.Views.LevelView;

/**
 * Application.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 */
public class Application {
    private static String[] pathsToImages = { "src/main/resources/Lemming_pose-two.png",
            "src/main/resources/Lemming_pose-three.png" };
    private static BufferedImage[] lemmingAnimationFrames = new BufferedImage[pathsToImages.length];

    public static void main(String[] args) {
        
        JFrame mainFrame = new JFrame("Lemmings REVENGE!");

        LevelView gameView = new LevelView();
        for (int i = 0; i < pathsToImages.length; i++) {
            lemmingAnimationFrames[i] = getAnimationImages(pathsToImages[i]);
        }
        
        //try visualizing on screen
        JLabel animationFrame = new JLabel(new ImageIcon(lemmingAnimationFrames[1]));
        mainFrame.add(animationFrame);

        LevelController gameController = new LevelController(gameView);
        //register a character controller with gameController
        Character lemming = new Lemming();
        gameController.setCharacterController(new CharacterController(lemming, lemmingAnimationFrames));
        
        // add a new character to screen
        gameView.addCharacterView(new CharacterView(lemmingAnimationFrames), lemming.getXPosition(), lemming.getYPosition());

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(gameView);
        
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    private static BufferedImage getAnimationImages(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            System.err.println("Unable to laod image");
            return null;
        }
    }

}