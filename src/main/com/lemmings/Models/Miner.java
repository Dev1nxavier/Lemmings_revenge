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
    public void useSkill(Character c) {
        try {
            if (getCount() > 0) {
                if (c.getLastGround().getxPos() != c.getCurrentGround().getxPos()) {
                    decrementCount();
                }
            } 
        } catch (Exception e) {
            System.err.println("unable to update");
        }
    }
}