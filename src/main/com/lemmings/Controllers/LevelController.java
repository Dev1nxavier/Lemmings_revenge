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
import src.main.com.lemmings.utilities.Utilities;
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
    private int level = 0;
    private LevelModel[] levels;
    private LevelModel currentLevelModel;
    private LevelView levelView;
    private GameState gameState;
    private MenuOptions menuModel;
    private MenuOptionsController menuController;
    private GameStateController gameStateController;
    private ArrayList<CharacterController> characterControllers;
    private Skill currentSkillSelected;
    private boolean isPaused = false;
    private boolean winLossHandled = false; // flag to ensure handleWin is called one time
    private Timer timer;

    public LevelController(LevelView levelView, GameState gameState, MenuOptions menuOptions) {
        this.levelView = levelView;
        this.currentLevelModel = loadLevel(level);
        this.gameState = gameState;
        this.menuModel = menuOptions;

        initializeControllers(levelView, gameState);

        initializeGame();
        addListeners();
        
    }

    private void initializeLevel(int level){
        this.currentLevelModel = loadLevel(level);
        levelView.clearGameObjectsFromView();
        // this.characterControllers = levelView.initializeCharacterViews(currentLevelModel.getCharacters(), this);
        this.levelView.initializeWithLevelModel(this.currentLevelModel);
    }

    private void initializeControllers(LevelView levelView, GameState gameState) {
        // create controllers
        this.gameStateController = new GameStateController(levelView.getStatsPanelView(), gameState);
        this.menuController = new MenuOptionsController(levelView.getMenuOptionsView(), menuModel);
    }

    public void initializeGame() {
        
        // clear level view
        levelView.clearGameObjectsFromView();

        this.characterControllers = levelView.initializeCharacterViews(currentLevelModel.getCharacters(), this);
        // add GameObjects and Characters to LevelView
        this.levelView.initializeWithLevelModel(this.currentLevelModel);
        playSound("src/main/resources/backgroundMusic.wav");
    }

    private void addListeners() {
        setupTimer();
        setupMenuControllerListener();
        setGameObjectListener();
    }

    private void setGameObjectListener() {
        for (GameObject go : currentLevelModel.getGameObjects()) {
            go.setGameObjectChangeListener(this);
        }
    }

    private void setupMenuControllerListener() {
        menuController.addGameObjectChangeListener(this);
    }

    private LevelModel loadLevel(int level) {
        // load the LevelModels from file
        this.levels = Utilities.loadLevels("src/main/levels.dat");
        if (this.levels.length == 0 || this.levels == null) {
            System.out.println("ERROR! UNABLE TO LOAD LEVELS!!");
            return null;
        }
        // pass the current levelModel
        return levels[level];
    }

    private void setupTimer() {
        // refresh at frame rate
        this.timer = new Timer(100, new ActionListener() {

            // check for collisions
            ArrayList<GameObject> env = currentLevelModel.getGameObjects();
            ArrayList<Character> characters = currentLevelModel.getCharacters();

            @Override
            public void actionPerformed(ActionEvent e) {
                // update characters and game objects
                updateGameState(env, characters);
            }
        });

        timer.start();
    }

    /**
     * Updates character states on each frame, then checks for win or death
     * conditions.
     * 
     * @param env        the level's GameObjects which are checked against a
     *                   Characters position to determine colision or state change.
     * @param characters the CharacterControllers. Each CharacterController's
     *                   associated Character is checked against the GameObjects for
     *                   state changes.
     */
    private void updateGameState(ArrayList<GameObject> env, ArrayList<Character> characters) {
        Rectangle gameBounds = levelView.getGamePanelBounds();
        for (CharacterController chController : characterControllers) {
            chController.updateCharacter(env, characters, gameBounds);
        }
        processWinOrDeadConditions();
    }

    private void processWinOrDeadConditions() {
        ArrayList<CharacterController> remove = findCharactersMeetingConditions();
        updateCounts(remove);

        gameStateController.updateScore(calculateScore());
        if ((hasWon() || hasLost()) && !winLossHandled) {
            advanceOrRestart();
        }
    }

    private boolean hasLost() {
        return currentLevelModel.getMAX_CHARS()
                - (gameState.getCharactersThroughPortal() + gameState.getCharactersDead()) < currentLevelModel
                        .getWIN_CONDITION() - gameState.getCharactersThroughPortal();
    }

    private void playSound(String pathToClip) {
        Utilities.playClip(pathToClip);
    }

    private boolean hasWon() {
        return gameState.getCharactersThroughPortal() >= currentLevelModel.getWIN_CONDITION();
    }

    private int calculateScore() {
        int score = gameState.getCharactersThroughPortal() * currentLevelModel.getPOINTS_PER_CHARACTER();
        return score;
    }

    private void updateCounts(ArrayList<CharacterController> characterControllers) {
        for (CharacterController ch : characterControllers) {
            if (!ch.getIsDead()) {

                gameStateController.updateCharactersThroughPortal(1);
            } else {
                gameStateController.updateCharactersDead(1);
            }
        }
        removeCharacters(characterControllers);
    }

    private void advanceOrRestart() {

       if (hasWon() || hasLost()) {
         winLossHandled = true;
         String sound = hasWon() ? "win_sound.wav" : "lose_sound.wav";
         playSound("src/main/resources/" + sound);
         levelView.getWinScreen().setIsVisible(hasWon());
         levelView.getLoseScreen().setIsVisible(hasLost());
       }

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                levelView.getWinScreen().setVisible(false);
                levelView.getLoseScreen().setIsVisible(false);
                if(hasWon()){
                     level++;
                }
                ((Timer) e.getSource()).stop(); // ensure timer fires only one time
                System.exit(0);
            }

        });
        timer.setRepeats(false);
        timer.start();

    }

    // this methods finds CharacterControllers whose coupled Character models meet
    // the win condition
    private ArrayList<CharacterController> findCharactersMeetingConditions() {
        ArrayList<CharacterController> remove = new ArrayList<>();

        for (CharacterController chController : characterControllers) {
            // check if character collided with portal or y-position is beyond the screen
            // y-bounds
            if (chController.checkWinCondition(currentLevelModel.getPortal()) || chController.getIsDead()) {
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
        currentLevelModel.getGameObjects().remove(clickedObject);
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
        for (GameObject obj : currentLevelModel.getGameObjects()) {
            if (obj.getRowAndCol().equals(clickedObject)) {

                itemsToRemove.add(obj);
            }
        }

        for (GameObject item : itemsToRemove) {
            removeGameObjectSelected(item);
        }
        updateGameState();
    }

    // When a new GameObject is created, this method is called to add the object to
    // the array of GameObjects and
    // rerender the LevelView.
    @Override
    public void addGameObject(GameObject go) {
        currentLevelModel.getGameObjects().add(go);
        updateGameState();
    }

    @Override
    public void updateGameState() {
        levelView.initializeWithLevelModel(currentLevelModel);
    }

    /**
     * This method removes the {@code CharacterController} from the
     * CharacterController Array. It first calls updateGameState to remove the
     * corresponding CharacterView from the
     * Gameview.
     */
    @Override
    public void removeCharacter(CharacterController character) {
        levelView.removeObjectFromView(character.getCharacterView(), JLayeredPane.MODAL_LAYER);
        characterControllers.remove(character);
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

    @Override
    public void pauseGame() {
        this.isPaused = !isPaused;

        if (isPaused) {
            this.timer.stop();
        } else {
            this.timer.restart();
        }
    }
}