package src.main.com.lemmings.Controllers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Models.GameObjects.WarpPortal;
import src.main.com.lemmings.Models.Skills.Blocker;
import src.main.com.lemmings.Models.Skills.Builder;
import src.main.com.lemmings.Models.Skills.Miner;
import src.main.com.lemmings.Models.Skills.Ordinance;
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
    private Character ch;
    private CharacterView chView;
    private GameObjectChangeListener listener;

    CharacterController(Character ch, GameObjectChangeListener listener) {
        this.ch = ch;
        this.chView = new CharacterView(ch.getXPosition(), ch.getYPosition());
        this.listener = listener;
        addListeners();
    }

    private void addListeners() {
        this.chView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent clickEvent) {
                super.mousePressed(clickEvent);
                // add an icon to the view
                chView.setSkillIcon("miner_icon.png");
                onCharacterSelected();
            }

            @Override
            public void mouseEntered(MouseEvent enterEvent){
                super.mouseEntered(enterEvent);
                chView.showArrow();
            }

            @Override
            public void mouseExited(MouseEvent exitEvent){
                super.mouseExited(exitEvent);
                chView.hideArrow();
            }
            
        });
    }

    protected void onCharacterSelected() {
        listener.updateCharacterModel(this);
    }

    public void updateCharacter(ArrayList<GameObject> env, ArrayList<Character> characters) {
        invokeSkill(env);
        ch.detectCollisions(env);
        ch.detectCollisions(characters);
        ch.updatePosition();
        ch.detectBounds();
        detectGround(env);
        onCharacterModelUpdate();
    }

    /**
     * This method checks if the character has an assigned skill. If it does, it
     * checks the result of the useSkill method and returns true if the skill was
     * used.
     */
    public void invokeSkill(ArrayList<GameObject> env) {
        if (ch.getSkill() == null) {
            return;
        }
        ch.useSkill(env);
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

    public void setSkill(Skill skill) {
        System.out.printf("Setting skill in ChController.setSkill: %s", skill.getSkillType() );
        ch.setSkill(skill);
    }

    public CharacterView getCharacterView() {
        return this.chView;
    }

    public Character getCharacterModel(){
        return this.ch;
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
        return ch.detectGround(gameObjects);
    }

    public boolean checkWinCondition(WarpPortal portal){
        return ch.detectPortal(portal);
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