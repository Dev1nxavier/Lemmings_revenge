package src.main.com.lemmings.Controllers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Models.MenuOptions;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Models.Skills.Skill;
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
    private LevelModel level;
    private LevelView levelView;
    private GameState playState;
    private MenuOptions menuModel;
    private MenuOptionsController menuController;
    private GameStateController gameStateController;
    private ArrayList<CharacterController> characterControllers;
    private Skill currentSkillSelected;

    public LevelController(LevelView levelView) {
        initializeGame(levelView);
        addListeners();
    }

    private void initializeGame(LevelView levelView) {

        // initialize level
        this.level = new LevelModel();
        this.levelView = levelView;

        // initialize game state and menu
        this.playState = new GameState();
        this.menuModel = new MenuOptions();

        addObjectsToGameView(level.getGameObjects()); // display game objects in game view

        // create controllers
        this.characterControllers = createCharacterControllers();
        this.gameStateController = new GameStateController(levelView.getStatsPanelView(), playState);
        this.menuController = new MenuOptionsController(levelView.getMenuOptionsView(), menuModel);

        updateGameState();

    }

    /**
     * This method calls a method to clear all elements from LevelView before
     * itterating over an ArrayList of gameObjects and adding
     * each gameObject to the LevelView's JLayeredPane default layer.
     * 
     * @param gameObjects
     */
    private void addObjectsToGameView(ArrayList<GameObject> gameObjects) {

        levelView.clearGameObjectsFromView();
        if (gameObjects.size() != 0) {
            for (GameObject go : gameObjects) {
                levelView.addObjectToView(go, JLayeredPane.DEFAULT_LAYER);
            }
        } else {
            System.err.println("Unable to load Game Objects");
        }
    }

    /**
     * Creates and initializes a list of {@code CharacterController} objects.
     * This method iterates over an ArrayList of {@code Character} objects in the
     * {@code LevelModel} and creates
     * a new {@code CharacterController} instance for each Character object. The
     * {@code CharacterController} instance is initialized
     * with a reference to the respective {@code Character} object and passed a
     * reference to the current GameObjectChangeListener instance.
     * 
     * @return An ArrayList of initialized CharacterControllers for this LevelModel
     *         instance.
     */
    private ArrayList<CharacterController> createCharacterControllers() {
        ArrayList<CharacterController> characterControllers = new ArrayList<>();
        for (int i = 0; i < level.getCharacters().size(); i++) {
            CharacterController ctrlr = new CharacterController(level.getCharacter(i), this);
            characterControllers.add(ctrlr);
        }
        return characterControllers;
    }

    private void addListeners() {

        setupTimer();

        setupClickListeners();

        setupMenuControllerListener();
    }

    private void setupMenuControllerListener() {
        menuController.addGameObjectChangeListener(this);
    }

    private void setupClickListeners() {
        // add click listeners to each Ground object
        for (GameObject obj : level.getGameObjects()) {
            obj.setGameObjectChangeListener(this);
        }
    }

    private void setupTimer() {
        // refresh at frame rate
        Timer timer = new Timer(100, new ActionListener() {

            // check for collisions
            ArrayList<GameObject> env = level.getGameObjects();
            ArrayList<Character> characters = level.getCharacters();

            @Override
            public void actionPerformed(ActionEvent e) {
                // update characters and game objects
                updateGameState(env, characters);
            }
        });

        timer.start();
    }

    private void updateGameState(ArrayList<GameObject> env, ArrayList<Character> characters) {
        for (CharacterController chController : characterControllers) {
            chController.updateCharacter(env, characters);
        }
        processWinConditions();
    }

    private void processWinConditions() {
        ArrayList<CharacterController> remove = findCharactersMeetingWinConditions();

        // each character is 5 points
        int addScore = remove.size() * 5;
        gameStateController.updateScore(addScore);

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

        for (CharacterController chController : characterControllers) {
            // check for win condition
            if (chController.checkWinCondition(level.getPortal())) {
                remove.add(chController);
            }
        }

        return remove;
    }

    @Override
    public void removeGameObjectSelected(GameObject clickedObject) {
        System.out.println("inside listener.removeGameObjectSelected");
        // retrieve the map coordinates of the clicked object
        Point xy = clickedObject.getRowAndCol();

        // update gameObjects array
        level.getGameObjects().remove(clickedObject);

        // update map
        // level.setMap(level.removePointFromMap(xy));
        updateGameState();
    }

    @Override
    public void removeGameObjectSelected(Point clickedObject) {
        ArrayList<GameObject> itemsToRemove = new ArrayList<>();
        System.err.println("Inside listener.removeGameObjectSelected");
        // find the GameObject by its xy coordinates
        for (GameObject obj : level.getGameObjects()) {
            if (obj.getRowAndCol().equals(clickedObject)) {
                System.out.println("Found object to remove");
                itemsToRemove.add(obj);
            }
        }

        for (GameObject item : itemsToRemove) {
            removeGameObjectSelected(item);
        }
    }

    // When a new GameObject is created, this method is called to add the object to
    // the array of GameObjects and
    // rerender the LevelView.
    @Override
    public void addGameObject(GameObject go) {
        level.getGameObjects().add(go);
        updateGameState();
    }

    @Override
    public void updateGameState() {
        System.out.println("Inside LvlController.updateGameState");
        levelView.redrawView(level.getGameObjects(), getCharacterControllers());
    }

    private ArrayList<CharacterController> getCharacterControllers() {
        return this.characterControllers;
    }

    @Override
    public void removeCharacter(CharacterController character) {
        getCharacterControllers().remove(character);
        updateGameState();
    }

    @Override
    public void updateMenuSelection(Skill skill) {
        skill.setListener(this);
        this.currentSkillSelected = skill;
    }

    @Override
    public void updateCharacterModel(CharacterController chController) {
        chController.setSkill(currentSkillSelected);
    }
}