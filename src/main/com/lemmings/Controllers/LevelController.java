package src.main.com.lemmings.Controllers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.main.com.lemmings.Models.GameObject;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Views.LevelView;
import src.main.com.lemmings.Models.Character;

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
public class LevelController implements GameObjectChangeListener {
    private LevelModel lvl;
    private LevelView gameView;
    // private ArrayList<Character> characters;
    private ArrayList<CharacterController> chControllers;

    public LevelController(LevelView gameView) {
        initializeLevel(gameView);
        addListeners();
    }

    private void initializeLevel(LevelView gameView) {
        this.lvl = new LevelModel();
        this.gameView = gameView;
        lvl.createGameObjectsFromMap(); // populate game objects in Model
        addObjectsToGameView(lvl.getGameObjects()); // display game objects in game view
        this.chControllers = createCharacterControllers();
        updateGameState();

    }

    private void addObjectsToGameView(ArrayList<GameObject> gameObjects) {

        gameView.clearGameObjectsFromView();
        if (gameObjects.size() != 0) {
            for (GameObject go : gameObjects) {
                gameView.addObjectToView(go, JLayeredPane.DEFAULT_LAYER);
            }
        } else {
            System.err.println("Unable to load Game Objects");
        }
        gameView.repaint();
    }

    private ArrayList<CharacterController> createCharacterControllers() {
        ArrayList<CharacterController> characterControllers = new ArrayList<>();
        for (int i = 0; i < lvl.getCharacters().size(); i++) {
            CharacterController ctrlr = new CharacterController(lvl.getCharacter(i), this);
            characterControllers.add(ctrlr);
        }
        return characterControllers;
    }

    private void addListeners() {

        // refresh at frame rate
        Timer timer = new Timer(100, new ActionListener() {

            // check for collisions
            ArrayList<GameObject> env = lvl.getGameObjects();
            ArrayList<Character> characters = lvl.getCharacters();

            @Override
            public void actionPerformed(ActionEvent e) {
                // update character
                updateGameState(env, characters);
            }
        });

        timer.start();

        // this.gameView.addMouseMotionListener(new MouseAdapter() {
        //     @Override
        //     public void mouseMoved(MouseEvent event) {
        //         gameView.showMouseLocation(MouseInfo.getPointerInfo().getLocation());
        //     }
        // });

        // add click listeners to each Ground object
        for (GameObject obj : lvl.getGameObjects()) {
            obj.setGameObjectChangeListener(this);
        }
    }

    private void updateGameState(ArrayList<GameObject> env, ArrayList<Character> characters) {
        for (CharacterController chController : chControllers) {
            chController.detectCollision(env); // returns the object that this character collides with
            chController.detectCollision(characters);

            chController.detectGround(env); // returns the ground object that this character is
                                            // standing on

            chController.updateCharacter(env);
        }
    }

    @Override
    public void removeGameObjectSelected(GameObject clickedObject) {
        // retrieve the map coordinates of the clicked object
        Point xy = clickedObject.getRowAndCol();

        // update gameObjects array
        lvl.getGameObjects().remove(clickedObject);

        // update map
        lvl.setMap(lvl.removePointFromMap(xy));
        updateGameState();
    }

    @Override
    public void removeGameObjectSelected(Point clickedObject) {
        ArrayList<GameObject> itemsToRemove = new ArrayList<>();

        // find the GameObject by its xy coordinates
        for (GameObject obj : lvl.getGameObjects()) {
            if (obj.getRowAndCol().equals(clickedObject)) {
                itemsToRemove.add(obj);
            }
        }

        for (GameObject item : itemsToRemove) {
            removeGameObjectSelected(item);
        }
    }

    @Override
    public void modifyGameObject(GameObject go) {
        lvl.getGameObjects().add(go);
        updateGameState();
    }

    @Override
    public void updateGameState() {
        gameView.redrawView(lvl.getGameObjects(), getCharacterControllers());
    }

    private ArrayList<CharacterController> getCharacterControllers() {
        return this.chControllers;
    }
}