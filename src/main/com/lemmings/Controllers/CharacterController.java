package src.main.com.lemmings.Controllers;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.Collidable;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Models.GameObjects.WarpPortal;
import src.main.com.lemmings.Models.Skills.Skill;
import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.Views.CharacterView;

/**
 * CharacterController.java
 * 
 * @author Sean Greene
 * @date November 11, 2023
 * 
 *       This class is responsible for updating the CharacterView and the
 *       Character model
 */
public class CharacterController {
    private Character character;
    private CharacterView characterView;
    private GameObjectChangeListener listener;

    /**
     * Constructor for a new Character Controller object. 
     * 
     * @param character the Character model to be associated with this Character Controller
     * @param characterView the CharacterView to be associated with this character controller
     * @param listener the GameObjectChangeListener interface to be set to this Character Controller
     */
    public CharacterController(Character character, CharacterView characterView, GameObjectChangeListener listener) {
        this.character = character;
        this.characterView = characterView;
        this.listener = listener;
        addListeners();
    }

    /**
     * Adds mouse listeners to the CharacterView associated with this Character Controller
     * Listeners handle various mouse events: press, enter, exit
     * 
     */
    private void addListeners() {
        this.characterView.addMouseListener(new MouseAdapter() {
            /**
             * On press, handle character selection
             */
            @Override
            public void mousePressed(MouseEvent clickEvent) {
                super.mousePressed(clickEvent);
                onCharacterSelected();
            }

            /**
             * on mouse over, display an arrow icon over the characterview
             */
            @Override
            public void mouseEntered(MouseEvent enterEvent) {
                super.mouseEntered(enterEvent);
                characterView.showArrow();
            }

            /**
             * On mouse exit, remove arrow icon from characterview
             */
            @Override
            public void mouseExited(MouseEvent exitEvent) {
                super.mouseExited(exitEvent);
                characterView.hideArrow();
            }

        });
    }

    /**
     * Update the GameObjectChangeListener when the characterview is clicked
     */
    protected void onCharacterSelected() {
        listener.updateCharacterModel(this);
    }

    /**
     * Updates the charactermodel state in response to game environment interactions
     * Invokes any skills associated with character model, detects collisions, and updates characters position
     * Checks for boundary constraints within game panel
     * 
     * @param env           The game environment objects that can interact wtih the character
     * @param characters    The array of other characters in the game, for collision detection
     * @param gameBounds    The boundaries of the game area to constrain characters x movement or detect falling
     */
    public void updateCharacter(ArrayList<GameObject> env, ArrayList<Character> characters, Rectangle gameBounds) {
        // Collect all collidable objects
        ArrayList<Collidable> collidables = new ArrayList<>();
        collidables.addAll(env);
        collidables.addAll(characters);

        // update character
        invokeSkill(env);
        character.detectCollisions(collidables);
        character.updatePosition();
        character.detectHorizontalBounds(gameBounds);
        character.detectVerticalBounds(gameBounds);

        // detect ground and alert listener
        detectGround(env);
        onCharacterModelUpdate();

    }

    public boolean getIsDead(){
        return character.getIsDead();
    }

    /**
     * This method checks if the character has an assigned skill. If it does, it
     * checks the result of the useSkill method and returns true if the skill was
     * used.
     */
    public void invokeSkill(ArrayList<GameObject> env) {
        if (character.getSkill() == null) {
            return;
        }
        character.useSkill(env);
    }

    /**
     * This method returns the assigned skill attached to the instance of a
     * Character.
     * 
     * @return an enum value representing the type of skill assigned to the instance
     *         of this Character.
     */
    public SKILL_TYPE getSkillType() {
        return character.getSkillType();
    }

    public void setSkill(Skill skill) {
        character.setSkill(skill);
        characterView.setSkillIcon(skill.getImage());
    }

    public CharacterView getCharacterView() {
        return this.characterView;
    }

    public Character getCharacterModel() {
        return this.character;
    }

    /**
     * This method checks for collisions between a character and a game object.
     * it calls Character class's detectCollision method, passing all level
     * gameObjects as arguments.
     * 
     * @param gameObjects the level's gameObjects to test
     * @return the gameObject instance that the character has collided with, or
     *         null.
     */
    public void detectCollision(ArrayList<Collidable> environment) {
        character.detectCollisions(environment);
    }

    public GameObject detectGround(ArrayList<GameObject> gameObjects) {

        // reset isGround
        character.setIsGround(false);
        return character.detectGround(gameObjects);
    }

    public boolean checkWinCondition(WarpPortal portal) {
        return character.detectPortal(portal);
    }

    /**
     * This method calls an update method in the main View, passing in the updated
     * Character model
     * 
     * @param model The Character model that has been updated.
     */

    public void onCharacterModelUpdate() {
        characterView.update(character.getX_pos(), character.getY_pos());
    }
}