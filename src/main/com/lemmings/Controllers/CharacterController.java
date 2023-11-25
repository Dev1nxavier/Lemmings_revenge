package src.main.com.lemmings.Controllers;

import java.util.ArrayList;
import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObject;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Views.CharacterView;
import src.main.com.lemmings.Models.Skill.SKILL_TYPE;

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
    private Character ch;
    private GameObjectChangeListener listener;
    private CharacterView chView;
    private boolean isHighlighted = false;

    CharacterController(Character ch, GameObjectChangeListener listener) {
        this.ch = ch;
        this.listener = listener;
        this.chView = new CharacterView(ch.getXPosition(), ch.getYPosition());

        addListeners();
    }

    private void addListeners() {

    }

    public void updateCharacter() {
        invokeSkill();
        ch.updatePosition();
        ch.detectBounds();
        onCharacterModelUpdate();
    }

    /**
     * This method checks if the character has an assigned skill. If it does, it
     * checks the result of the useSkill method and returns true if the skill was
     * used.
     */
    public void invokeSkill() {
        if (ch.getSkill() != null) {
            ch.useSkill();
        }
    }

    /**
     * This method returns the assigned skill attached to the instance of a
     * Character.
     * 
     * @return an enum value representing the type of skill assigned to the instance
     *         of this Character.
     */
    public SKILL_TYPE getSkillType() {
        return ch.getSkillType();
    }

    public CharacterView getCharacterView() {
        return this.chView;
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
    public void detectCollision(ArrayList<? extends Object> environment) {
        ch.detectCollisions(environment);
    }

    public GameObject detectGround(ArrayList<GameObject> gameObjects) {
        // reset isGround
        ch.setIsGround(false);
        return ch.isOnGround(gameObjects);
    }

    /**
     * This method calls an update method in the main View, passing in the updated
     * Character model
     * 
     * @param model The Character model that has been updated.
     */

    public void onCharacterModelUpdate() {
        chView.update(ch.getXPosition(), ch.getYPosition());
    }
}