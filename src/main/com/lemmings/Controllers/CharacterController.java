package src.main.com.lemmings.Controllers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import src.main.com.lemmings.Models.Blocker;
import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObject;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.Miner;
import src.main.com.lemmings.Models.Rock;
import src.main.com.lemmings.Views.ArrowIcon;
import src.main.com.lemmings.Views.CharacterView;
import src.main.com.lemmings.Views.SkillIcon;
import src.main.com.lemmings.utilities.ImageLoader;
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
    private CharacterView chView;
    private boolean isHighlighted = false;

    CharacterController(Character ch, CharacterView chView) {
        this.ch = ch;
        this.chView = chView;

        addListeners();
    }

    private void addListeners() {
        this.chView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent clickEvent) {
                isHighlighted = !isHighlighted;
                ch.setSkill(new Blocker());

                chView.getAnimationFrames().clear(); // remove existing images
                chView.getAnimationFrames().add(ImageLoader.GAME_IMAGES.get("Lemming_police_1.png"));
                chView.getAnimationFrames().add(ImageLoader.GAME_IMAGES.get("Lemming_police_3.png"));
                chView.getAnimationFrames().add(ImageLoader.GAME_IMAGES.get("Lemming_police_2.png"));
                chView.resetCharacterFrame();
                chView.setDelay(5);
                updateCharacter();
                chView.repaint();
            }
        });
    }

    public void updateCharacter() {
        ch.updatePosition();
        ch.detectBounds();
        onCharacterModelUpdate();
    }

    /**
     * This method checks if the character has an assigned skill. If it does, it
     * checks the result of the useSkill method and returns true if the skill was
     * used.
     */
    public boolean invokeSkill(GameObject ground) {
        if (ch.getSkillType() != null) {
            if (ch.getSkill().getCount() <= 0) {
                chView.setSkillIcon(null);
            }
            return ch.useSkill(this);
        }
        return false;
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

    /**
     * This method checks for collisions between a character and a game object.
     * it calls Character class's detectCollision method, passing all level
     * gameObjects as arguments.
     * 
     * @param gameObjects the level's gameObjects to test
     * @return the gameObject instance that the character has collided with, or
     *         null.
     */
    public GameObject detectCollision(ArrayList<GameObject> gameObjects) {
        return ch.detectCollisions(gameObjects);
    }

    public GameObject detectGround(ArrayList<GameObject> gameObjects) {
        // reset isGround
        ch.setIsGround(false);
        return ch.isOnGround(gameObjects);
    }

    public void onModelUpdate(GameObjectChangeListener changeListener){
        GameObject barrier = new Rock(ch.getXPosition(), ch.getYPosition(), ch.getC_WIDTH(), ch.getC_HEIGHT(), null);
        barrier.setImage(ImageLoader.GAME_IMAGES.get("Lemming_police_3"));
        changeListener.modifyGameObject(barrier);
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