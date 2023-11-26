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
    private GameObjectChangeListener listener;
    

    public Excavator(){
        setCount(3);
    }

    public Excavator(GameObjectChangeListener listener){
        this();
        setListener(listener);
    }

    /**
     * This method checks if the character is on ground and returns true. 
     * 
     * @param Character the character that implements this skill. 
     * @return true if character is currently on ground, otherwise returns false. 
     */
    @Override
    public void useSkill(Character c) {
        // TODO: this skill removes a block of ground directly beneath character
        // check if object is ground and...
        if (getCount() > 0 && c.isGround()) {
            getListener().removeGameObjectSelected(c.getCurrentGround());
            decrementCount();
        }
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    public int getCount(){
        return this.count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public GameObjectChangeListener getListener(){
        return this.listener;
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

    @Override
    public void useSkill(Character c, ArrayList<GameObject> env) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
    }

    @Override
    public void setListener(GameObjectChangeListener listener) {
        this.listener = listener;
    }

}