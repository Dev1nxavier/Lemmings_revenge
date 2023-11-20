package src.main.com.lemmings.Models;

/**
 * Miner
 */
public class Miner extends Excavator {
    private final Skill.SKILL_TYPE type = SKILL_TYPE.MINER;

    public Miner() {
        setCount(2);
    };

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    @Override
    public boolean useSkill(Character c) {
        if (getCount() > 0) {
            if (c.getLastGround().getxPos() != c.getCurrentGround().getxPos()) {
                decrementCount();
                return true;
            }
        }
        // c.removeSkill();
        return false;
    }
}