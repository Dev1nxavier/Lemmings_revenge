package src.main.com.lemmings.Controllers;
import java.lang.reflect.Array;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObject;
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
    private CharacterView chView;

    CharacterController(Character ch, CharacterView chView) {
        this.ch = ch;
        this.chView = chView;
    }

    public void updateCharacter() {
        ch.updatePosition();
        ch.detectBounds();
        chView.update(ch.getXPosition(), ch.getYPosition());
    }

    /**
     * This method checks if the character has an assigned skill. If it does, it
     * checks the result of the useSkill method and returns true if the skill was used. 
     */
    public boolean invokeSkill(GameObject ground) {
        if (ch.getSkillType() != null) {
            if (ch.getSkill().getCount() <=0) {
                chView.setSkillIcon(null);
            }
            return ch.useSkill(ground);
        }
        return false;
    }

    /**
     * This method returns the assigned skill attached to the instance of a Character.
     * @return an enum value representing the type of skill assigned to the instance of this Character. 
     */
    public SKILL_TYPE getSkillType(){
        return ch.getSkillType();
    }

    /**
     * This method checks for collisions between a character and a game object.
     * it calls Character class's detectCollision method, passing all level gameObjects as arguments. 
     * @param gameObjects the level's gameObjects to test
     * @return the gameObject instance that the character has collided with, or null. 
     */
    public GameObject detectCollision(ArrayList<GameObject> gameObjects){
        return ch.detectCollision(gameObjects);
    }

    public GameObject detectGround(ArrayList<GameObject> gameObjects){
        //reset isGround
        ch.setIsGround(false);
        return ch.isOnGround(gameObjects);
    }
}