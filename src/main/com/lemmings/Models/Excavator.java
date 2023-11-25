package src.main.com.lemmings.Models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Controllers.CharacterController;

/**
 * Excavator
 */
public class Excavator implements Skill {
    private final Skill.SKILL_TYPE type = SKILL_TYPE.EXCAVATOR;
    private int count; // counts the number of times this skill can be invoked
    public Excavator(){
        count = 2;
    }

    /**
     * This method checks if the character is on ground and returns true. 
     * 
     * @param Character the character that implements this skill. 
     * @return true if character is currently on ground, otherwise returns false. 
     */
    @Override
    public boolean useSkill(Character c, GameObject obj) {
        // TODO: this skill removes a block of ground directly beneath character
        // check if object is ground and...
        if (c.isGround() && decrementCount() >0) {
            return true;
        }
        // c.removeSkill();
        return false;
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    @Override
    public void  useSkill(Character c) {
     
    }

    public int getCount(){
        return this.count;
    }

    public void setCount(int count){
        this.count = count;
    }

    /**
     * Decrements count and returns remaining count. 
     * @return the remaining count of this Skill instance.
     */
    public int decrementCount(){
       this.count--;
       return getCount();
    }

    @Override
    public ArrayList<BufferedImage> getAnimationFrames() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnimationFrames'");
    }

}