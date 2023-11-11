package src.main.com.lemmings.Controllers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.Lemming;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Views.LevelView;
import src.main.com.lemmings.utilities.Utilities;
import src.main.com.lemmings.Views.CharacterView;

/**
 * LevelController.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       This class controlls the levels of the game "Lemmings REVENGE!". The
 *       class
 *       uupdates the game state, and includes
 *       methods for registering mouseClick events and making updates to the
 *       environment and characters
 * 
 */
public class LevelController {
    private LevelModel lvl;
    private LevelView gameView;
    private ArrayList<BufferedImage> chFrames = new ArrayList<>();
    private Character lemming;
    private CharacterView chView;

    public LevelController(LevelView gameView) {
        this.lvl = new LevelModel();
        this.gameView = gameView;

        // pass map to gameview
        gameView.setMap(lvl.getMap());
        // load character images
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-two.png"));
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-three.png"));
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-0.png"));

        // FIXME: move to own CharacterController
        // initiate a new Character
        lemming = new Lemming();
        chView = new CharacterView(chFrames);
        // pass reference of new CharacterView to LevelView
        gameView.addCharacterView(chView);

        addListeners();
    }

    private void addListeners() {
        // FIXME: Adjust frame rate

        // refresh at frame rate
        Timer timer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //update character
                lemming.updatePosition();
                // get updated position and pass to CharacterView
                chView.setPosX(lemming.getXPosition());

                // TODO: Update view
                gameView.setMap(lvl.getMap()); // pass level map each time
                gameView.updateView();
            }

        });

        timer.start();

        this.gameView.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent event) {
                JOptionPane.showMessageDialog(null, "You Clicked the Mouse!");
            }
        });
    }

}