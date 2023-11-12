package src.main.com.lemmings.Controllers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
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
    private ArrayList<Character> characters;
    private ArrayList<CharacterView> chViews;
    private ArrayList<CharacterController> chControllers;

    public LevelController(LevelView gameView) {
        this.lvl = new LevelModel();
        this.gameView = gameView;
        this.chControllers = new ArrayList<>();
        characters = lvl.getCharactersArray();
        gameView.setLayout(new FlowLayout());
        // pass map to gameview
        gameView.setMap(lvl.getMap());
        // load character images
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-two.png"));
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-three.png"));
        chFrames.add(Utilities.getGameImages("src/main/resources/Lemming_pose-0.png"));

        int counter = 0;
        if (characters.size() != 0) {
            for (Character ch : characters) {
                // initialize a new view
                System.out.printf("Character %d: x = %d, y= %d\n",counter, ch.getXPosition(), ch.getYPosition() );
                CharacterView chView = new CharacterView(chFrames, ch.getXPosition(), ch.getYPosition());
                chView.setPreferredSize(new Dimension(100, 200));
                //add view to panel
                gameView.addCharacterView(chView);
                //create a new controller
                CharacterController ctrlr = new CharacterController(ch, chView);
                ctrlr.updateCharacter();
                chControllers.add(ctrlr);
                counter++; 
            }

        }

        addListeners();
    }

    private void addListeners() {

        // refresh at frame rate
        Timer timer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // update character
                for (CharacterController ctrl : chControllers) {
                    ctrl.updateCharacter();
                }

                //check for collisions
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

        this.gameView.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event){
                 gameView.showMouseLocation(MouseInfo.getPointerInfo().getLocation());
            }
        });
    }

}