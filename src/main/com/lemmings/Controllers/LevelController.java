package src.main.com.lemmings.Controllers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObject;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Views.LevelView;
import src.main.com.lemmings.utilities.ImageLoader;
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
    private ArrayList<CharacterController> chControllers;

    public LevelController(LevelView gameView) {
        initializeLevel(gameView);

        addListeners();
    }

    private void initializeLevel(LevelView gameView) {
        this.lvl = new LevelModel();
        this.gameView = gameView;
        this.chControllers = new ArrayList<>();
        characters = lvl.getCharactersArray();
        // pass map to gameview
        gameView.setMap(lvl.getMap());
        initializeGameObjects();

        initializeCharacters(gameView);

    }

    private void initializeGameObjects() {
        ArrayList<GameObject> gameObjects = lvl.getGameObjects();

        if (gameObjects.size() != 0) {
            for (GameObject go : gameObjects) {
                gameView.addGameObjectsToView(go, JLayeredPane.DEFAULT_LAYER);
                gameView.repaint();
            }
        } else {
            System.err.println("Unable to load Game Objects");
        }

    }

    private void initializeCharacters(LevelView gameView) {
        // load character images
        chFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-two.png"));
        chFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-three.png"));
        chFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-0.png"));

        if (characters.size() != 0) {
            for (Character ch : characters) {
                // initialize a new view
                CharacterView chView = new CharacterView(ch.getXPosition(), ch.getYPosition());
                // add view to panel
                gameView.addCharacterToView(chView, JLayeredPane.DEFAULT_LAYER);
                // create a new controller
                CharacterController ctrlr = new CharacterController(ch, chView);
                ctrlr.updateCharacter();
                chControllers.add(ctrlr);
            }

        }
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

                // check for collisions
                ArrayList<GameObject> env = lvl.getGameObjects();
                for (Character ch : characters) {
                    // check every game object
                    //reset for each loop
                    ch.isGround = false;
                    for (GameObject obj : env) {
                        if (obj.getType() == GameObject.ENV_TYPE.GROUND) {
                            ch.isOnGround(obj.getBounds());
                        } else {
                            // check if this is an obstacle
                            ch.detectCollision(obj.getBounds());
                        }
                    }
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

        this.gameView.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                gameView.showMouseLocation(MouseInfo.getPointerInfo().getLocation());
            }
        });
    }

}