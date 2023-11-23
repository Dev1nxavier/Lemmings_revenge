package src.main.com.lemmings.Models;

import src.main.com.lemmings.Controllers.CharacterController;
import src.main.com.lemmings.Views.CharacterView;

/**
 * Skill.java
 * 
 * @author Sean Greene
 * @date November 18, 2023
 * 
 * 
 */
public interface Skill {
    enum SKILL_TYPE{
        BUILDER,
        EXCAVATOR,
        MINER,
        BLOCKER,
        BOMBER,
        CLIMBER
    }
    public boolean useSkill(Character c, GameObject obj);
    public boolean useSkill(Character c);
    public boolean useSkill(Character c, CharacterController controller);
    public SKILL_TYPE getSkillType();
        /**
     * Decrements count and returns remaining count. 
     * @return the remaining count of this Skill instance.
     */
    public int decrementCount();

    public int getCount();
 
}