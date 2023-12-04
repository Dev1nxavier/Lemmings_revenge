package src.main.com.lemmings.Models.Skills;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * Excavator
 */
public class Excavator implements Skill {
    private final Skill.SKILL_TYPE type = SKILL_TYPE.EXCAVATOR;
    private int count; // counts the number of times this skill can be invoked
    private GameObjectChangeListener listener;
    private BufferedImage image;
    

    public Excavator(){
        setCount(2);
        setImage();
    }

    public Excavator(GameObjectChangeListener listener){
        this();
        setListener(listener);
    }

   /**
 * Uses the skill on a specified character. 
 * This skill removes a block of ground directly beneath the character if certain conditions are met.
 * The skill is used only if the skill count is greater than 0 and the character is currently on the ground. 
 * Once used, the skill count is decremented.
 *
 * @param c The Character on which the skill is to be used. The method checks if the character is on the ground.
 */
    @Override
    public void useSkill(Character c) {
        // TODO: this skill removes a block of ground directly beneath character
        // check if object is ground and...
        if (getCount() > 0 && c.isGround()) {
            getListener().removeGameObjectSelected(c.getCurrentGround());
            decrementCount();
        }else if (getCount()<=0) {
            c.removeSkill();
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
    public void useSkill(Character c, ArrayList<GameObject> env) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
    }

    @Override
    public void setListener(GameObjectChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public void setImage() {
        try {
            this.image = ImageLoader.getImage("miner_icon.png");
        } catch (Exception e) {
            System.err.println("Unable to load image: " + e.getMessage());
        }
    }

}