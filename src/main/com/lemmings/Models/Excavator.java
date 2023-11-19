package src.main.com.lemmings.Models;

/**
 * Excavator
 */
public class Excavator implements Skill {
    private final Skill.SKILL_TYPE type = SKILL_TYPE.EXCAVATOR;

    public Excavator(){
        
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
        if (c.isGround()) {
            return true;
        }
        return false;
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    @Override
    public boolean useSkill(Character c) {
        if (c.isGround()) {
            return true;
        }
        return false;
    }

}