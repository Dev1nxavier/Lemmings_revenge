package src.main.com.lemmings.Models.Skills;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.GameObject;

/**
 * Skill.java
 * 
 * @author Sean Greene
 * @date November 18, 2023
 * 
 * 
 */
public interface Skill {
    public static enum SKILL_TYPE{
        BUILDER,
        EXCAVATOR,
        MINER,
        BLOCKER,
        BOMBER
    }

    public void useSkill(Character c, ArrayList<GameObject> env);
    public void useSkill(Character c);
    public SKILL_TYPE getSkillType();
    public BufferedImage getImage();
    public void setImage();

        /**
     * Decrements count and returns remaining count. 
     * @return the remaining count of this Skill instance.
     */
    public int decrementCount();

    public int getCount();

    public void setCount(int count);

    public void setListener(GameObjectChangeListener listener);

    public GameObjectChangeListener getListener();
 
}