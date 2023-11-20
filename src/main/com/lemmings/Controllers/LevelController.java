package src.main.com.lemmings.Controllers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObject;
import src.main.com.lemmings.Models.GameObjectClickListener;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Models.Skill;
import src.main.com.lemmings.Views.LevelView;
import src.main.com.lemmings.Views.SkillIcon;
import src.main.com.lemmings.utilities.ImageLoader;
import src.main.com.lemmings.Views.CharacterView;
import src.main.com.lemmings.Views.GameView;

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
public class LevelController implements GameObjectClickListener {
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

        // initialize characters
        updateCharacterViewsInGameModel(createCharacterViews()); // add character views to game model
        addCharacterViewsToGameView(gameView, lvl.getCharacterViews());// add views to gameView
        this.chControllers = createCharacterControllers();
    }

    private void addObjectsToGameView(ArrayList<GameObject> gameObjects) {

        gameView.clearGameObjectsFromView();
        if (gameObjects.size() != 0) {
            for (GameObject go : gameObjects) {
                gameView.addGameObjectsToView(go, JLayeredPane.DEFAULT_LAYER);
            }
        } else {
            System.err.println("Unable to load Game Objects");
        }
        gameView.repaint();
    }

    private ArrayList<CharacterView> createCharacterViews() {
        // FIXME: Move CharactersArray to LevelModel
        ArrayList<CharacterView> characterViews = new ArrayList<>();
        ArrayList<Character> characters = lvl.getCharacters();
        for (Character ch : characters) {
            // initialize a new view
            CharacterView chView = new CharacterView(ch.getXPosition(), ch.getYPosition());
            if (ch.getSkillType() != null) {
                SkillIcon skillIcon = new SkillIcon(ImageLoader.GAME_IMAGES.get("miner_icon.png"), ch.getXPosition(), ch.getYPosition()+30);
                chView.setSkillIcon(skillIcon);
                lvl.addSkillToSkillViews(skillIcon);

            }
            lvl.setCharacterViews(chView);
            // add view to panel
            characterViews.add(chView);
        }
        return characterViews;
    }

    public void updateCharacterViewsInGameModel(ArrayList<CharacterView> characterViews) {
        for (CharacterView characterView : characterViews) {
            lvl.setCharacterViews(characterView);
        }
    }

    private void addCharacterViewsToGameView(LevelView gameView, ArrayList<CharacterView> characterViews) {
        for (CharacterView cView : characterViews) {
            gameView.addCharacterToView(cView, JLayeredPane.MODAL_LAYER);
            if (cView.getSkillIcon()!=null) {
                gameView.addSkillIconToView(cView.getSkillIcon(), JLayeredPane.MODAL_LAYER);
            }
        }
    }

    private ArrayList<CharacterController> createCharacterControllers() {
        ArrayList<CharacterController> characterControllers = new ArrayList<>();
        for (int i = 0; i < lvl.getCharacters().size(); i++) {
            CharacterController ctrlr = new CharacterController(lvl.getCharacter(i), (CharacterView)lvl.getGameView(i));
            ctrlr.updateCharacter();
            characterControllers.add(ctrlr);
        }
        return characterControllers;
    }

    private void addListeners() {

        // refresh at frame rate
        Timer timer = new Timer(100, new ActionListener() {

            // check for collisions
            ArrayList<GameObject> env = lvl.getGameObjects();
            // ArrayList<Character> characters = lvl.getCharacters();

            @Override
            public void actionPerformed(ActionEvent e) {
                // update character
                updateGameState(env);
            }
        });

        timer.start();

        this.gameView.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                gameView.showMouseLocation(MouseInfo.getPointerInfo().getLocation());
            }
        });

        // add click listeners to each Ground object
        for (GameObject obj : lvl.getGameObjects()) {
            obj.setGameObjectClickListener(this);
        }
    }

    private void updateGameState(ArrayList<GameObject>env) {
        for (CharacterController ctrl : chControllers) {
            ctrl.detectCollision(env); // returns the object that this character collides with
            GameObject ground = ctrl.detectGround(env); // returns the ground object that this character is standing on
            invokeSkill(ctrl, ground);
            ctrl.detectGround(env);
            ctrl.updateCharacter();
        }
    }

    private void invokeSkill(CharacterController ctrl, GameObject ground) {
        try {
            Skill.SKILL_TYPE type = ctrl.getSkillType();
            if (type != null && ground != null && ctrl.invokeSkill(ground)) {
                switch (type) {
                    case EXCAVATOR:
                        // dig down two levels
                        gameObjectClicked(ground);
                        break;
                    case MINER:
                        // dig diagonally
                        gameObjectClicked(ground);
                        break;
                    default:
                        break;
                }
            }
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gameObjectClicked(GameObject clickedObject) {
        // retrieve the map coordinates of the clicked object
        Point xy = clickedObject.getRowAndCol();
        // update gameObjects array
        lvl.getGameObjects().remove(clickedObject);
        // update map
        lvl.setMap(lvl.removePointFromMap(xy));
        // // recreate game objects
        gameView.clearGameObjectsFromView();
        // add the objects to gameview
        addObjectsToGameView(lvl.getGameObjects());
        // add characters back to gameView
        addCharacterViewsToGameView(gameView, lvl.getCharacterViews());
        // recheck
    }
}