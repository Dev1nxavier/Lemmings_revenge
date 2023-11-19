package src.main.com.lemmings.Models;

/**
 * Miner
 */
public class Miner extends Excavator {
    private final Skill.SKILL_TYPE type = SKILL_TYPE.MINER;

    public Miner() {
    };

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    @Override
    public boolean useSkill(Character c) {
        // put an arrow on him

        // System.out.printf("Miner.useSkill:\nObject: %d\nLast ground: %d", c.getCurrentGround().getUniqueId(), c.getLastGround().getUniqueId() );
        if (c.getLastGround().getxPos() != c.getCurrentGround().getxPos()) {
            return true;
        }
        return false;
    }

}