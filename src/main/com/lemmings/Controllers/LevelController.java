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
    private static final int MAX_CHARS = 10;
    private LevelModel lvl;
    private LevelView gameView;
    private ArrayList<BufferedImage> chFrames = new ArrayList<>();
    private ArrayList <Character> characters;
    private ArrayList<CharacterView> chViews;
    private ArrayList<CharacterController> chControllers;

    public LevelController(LevelView gameView) {
        this.lvl = new LevelModel();
        this.gameView = gameView;
        this.chControllers = new ArrayList<>();
        // pass map to gameview
        gameView.setMap(lvl.getMap());
        // load character images
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-two.png"));
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-three.png"));
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-0.png"));

        // FIXME: move to own CharacterController

        for (int i = 0; i < MAX_CHARS; i++) {
            // initiate a new Character
            Character ch = new Lemming(); // default are Lemming type
            // initialize a new characterview
            CharacterView chView = new CharacterView(chFrames);
            // add characterview to this JPanel
            gameView.add(chView);
            // create a new controller
            chControllers.add(new CharacterController(ch, chView));
        }

        addListeners();
    }

    private void addListeners() {

        // refresh at frame rate
        Timer timer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //update character
                for (CharacterController ctrl : chControllers) {
                    ctrl.updateCharacter();
                }

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