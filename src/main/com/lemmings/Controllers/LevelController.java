package src.main.com.lemmings.Controllers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Models.MenuModel;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Views.GameStateView;
import src.main.com.lemmings.Views.LevelView;
import src.main.com.lemmings.Views.MenuView;
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
    private GameState playState;
    private GameStateView scoreView;
    private MenuView menuView;
    private MenuModel menuModel;
    private MenuController menuController;
    private GameStateController psController;
    private ArrayList<CharacterController> chControllers;

    public LevelController(LevelView gameView) {
        initializeGame(gameView);
        addListeners();
    }

    private void initializeGame(LevelView gameView) {

        // initialize level
        this.lvl = new LevelModel();
        this.gameView = gameView;

        // initialize game state
        this.scoreView = new GameStateView();
        this.playState = new GameState();
        this.menuView = new MenuView();
        this.menuModel = new MenuModel();

        lvl.createGameObjectsFromMap(); // populate game objects in Model

        // add views to game panel
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // center contents horizontally
        scorePanel.setOpaque(false);
        scorePanel.add(scoreView);
        gameView.add(scorePanel, BorderLayout.NORTH);
        gameView.add(menuView, BorderLayout.EAST);
        addObjectsToGameView(lvl.getGameObjects()); // display game objects in game view

        // create controllers
        this.chControllers = createCharacterControllers();
        this.psController = new GameStateController(scoreView, playState);
        this.menuController = new MenuController(menuView, menuModel);

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
                // update characters and game objects
                updateGameState(env, characters);
            }
        });

        timer.start();

        // // FIXME: Remove after testing
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
            chController.updateCharacter(env, characters);
        }

        processWinConditions();
    }

    private void processWinConditions() {
        ArrayList<CharacterController> remove = findCharactersMeetingWinConditions();

        // each character is 5 points
        int addScore = remove.size() * 5;
        psController.updateScore(addScore);

        removeCharacters(remove);
    }

    private void removeCharacters(ArrayList<CharacterController> remove) {
        for (CharacterController characterController : remove) {
            removeCharacter(characterController);
        }
    }

    // this methods finds CharacterControllers whose coupled Character models meet
    // the win condition
    private ArrayList<CharacterController> findCharactersMeetingWinConditions() {
        ArrayList<CharacterController> remove = new ArrayList<>();

        for (CharacterController chController : chControllers) {
            // check for win condition
            if (chController.checkWinCondition(lvl.getPortal())) {
                remove.add(chController);
            }
        }

        return remove;
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
    public void addGameObject(GameObject go) {
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

    @Override
    public void removeCharacter(CharacterController character) {
        getCharacterControllers().remove(character);
        updateGameState();
    }
}