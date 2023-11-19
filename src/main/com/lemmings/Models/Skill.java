package src.main.com.lemmings.Models;

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
    public SKILL_TYPE getSkillType();
}