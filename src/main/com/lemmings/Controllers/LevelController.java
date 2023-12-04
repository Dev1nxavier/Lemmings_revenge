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
    private final int WIN_CONDITION = 9;
    private final int POINTS_PER_CHARACTER = 5;
    private LevelModel levelModel;
    private LevelView levelView;
    private GameState playState;
    private MenuOptions menuModel;
    private MenuOptionsController menuController;
    private GameStateController gameStateController;
    private ArrayList<CharacterController> characterControllers;
    private Skill currentSkillSelected;

    public LevelController(LevelView levelView, LevelModel levelModel, GameState gameState, MenuOptions menuOptions) {
        this.levelView = levelView;
        this.levelModel = levelModel;
        this.playState = gameState;
        this.menuModel = menuOptions;
        this.characterControllers = levelView.initializeCharacterViews(levelModel.getCharacters(), this);
        initializeGame();
        addListeners();
    }

    private void initializeGame() {

        // add GameObjects and Characters to LevelView
        this.levelView.initializeWithLevelModel(this.levelModel);

        // initialize game state and menu
        this.playState = new GameState();
        this.menuModel = new MenuOptions();

        // create controllers
        this.gameStateController = new GameStateController(levelView.getStatsPanelView(), playState);
        this.menuController = new MenuOptionsController(levelView.getMenuOptionsView(), menuModel);

        updateGameState();
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
    // private ArrayList<CharacterController> createCharacterControllers() {
    //     ArrayList<CharacterController> characterControllers = new ArrayList<>();
    //     for (int i = 0; i < levelModel.getCharacters().size(); i++) {
    //         CharacterController ctrlr = new CharacterController(levelModel.getCharacter(i), this);
    //         characterControllers.add(ctrlr);
    //     }
    //     return characterControllers;
    // }

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
        for (GameObject obj : levelModel.getGameObjects()) {
            obj.setGameObjectChangeListener(this);
        }
    }

    private void setupTimer() {
        // refresh at frame rate
        Timer timer = new Timer(100, new ActionListener() {

            // check for collisions
            ArrayList<GameObject> env = levelModel.getGameObjects();
            ArrayList<Character> characters = levelModel.getCharacters();

            @Override
            public void actionPerformed(ActionEvent e) {
                // update characters and game objects
                updateGameState(env, characters);
            }
        });

        timer.start();
    }

    private void updateGameState(ArrayList<GameObject> env, ArrayList<Character> characters) {
        Rectangle gameBounds = levelView.getGamePanelBounds();
        for (CharacterController chController : characterControllers) {
            chController.updateCharacter(env, characters, gameBounds);
        }
        processWinOrDeadConditions();
    }

    private void processWinOrDeadConditions() {
        ArrayList<CharacterController> remove = findCharactersMeetingConditions();

        // each character is 5 points
        int addScore = 0;
        int lostCharacterCount = 0;
        for (CharacterController ch : remove) {
            if (!ch.getIsDead()) {
                addScore += 5; // each character is 5 points.
            }else{
                lostCharacterCount++;
            }
        }
        gameStateController.updateScore(addScore);
        gameStateController.updateCharacterCount(lostCharacterCount);
        removeCharacters(remove);

        // if no more characters and we have met level threshold, show win screen
        if (gameStateController.getCharacterCount() <= WIN_CONDITION) {
            if (gameStateController.getScore() >= (WIN_CONDITION * POINTS_PER_CHARACTER)) {
                // show win layer
                levelView.getWinScreen().setIsVisible(true);
            }else{
                // show lose layer
                levelView.getLoseScreen().setIsVisible(true);
            }

            // For now, restart game
            new Timer(5000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
                
            });
        }
    }

    // this methods finds CharacterControllers whose coupled Character models meet
    // the win condition
    private ArrayList<CharacterController> findCharactersMeetingConditions() {
        ArrayList<CharacterController> remove = new ArrayList<>();

        for (CharacterController chController : characterControllers) {
            // check for win condition
            if (chController.checkWinCondition(levelModel.getPortal()) || chController.getIsDead()) {
                remove.add(chController);
            }
        }

        return remove;
    }

    /**
     * Removes characters passed as argument.
     * This method iterates through a list of character controllers and calls
     * removeCharacter on each.
     * 
     * @param remove
     */
    private void removeCharacters(ArrayList<CharacterController> remove) {
        for (CharacterController characterController : remove) {
            removeCharacter(characterController);
        }
    }

    @Override
    public void removeGameObjectSelected(GameObject clickedObject) {

        // update gameObjects array
        levelModel.getGameObjects().remove(clickedObject);
        updateGameState();
    }

    /**
     * Removes a {@code GameObject} from the Game Level.
     * This method iterats over the array of GameObjects and checks if the RowAndCol
     * field matches the RowAndCol field of the GameObject Point
     * passed as an argument and adds the matching GameObject to a remove array. It
     * then calls the method removeGameObjectSelected on each GameObject in the
     * remove array.
     */
    @Override
    public void removeGameObjectSelected(Point clickedObject) {
        ArrayList<GameObject> itemsToRemove = new ArrayList<>();

        // find the GameObject by its xy coordinates
        for (GameObject obj : levelModel.getGameObjects()) {
            if (obj.getRowAndCol().equals(clickedObject)) {

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
        levelModel.getGameObjects().add(go);
        updateGameState();
    }

    @Override
    public void updateGameState() {
        levelView.initializeWithLevelModel(levelModel);
    }

    private ArrayList<CharacterController> getCharacterControllers() {
        return this.characterControllers;
    }

    /**
     * This method removes the {@code CharacterController} from the
     * CharacterController Array. It calls updateGameState to render
     * the Gameview with remaining CharacterControllers in the CharacterController
     * Array.
     */
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