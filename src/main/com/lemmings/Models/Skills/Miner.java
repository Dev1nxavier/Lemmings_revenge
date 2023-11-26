package src.main.com.lemmings.Models.Skills;

import java.awt.Point;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.Ground;

/**
 * Miner
 */
public class Miner extends Excavator {
    private final SKILL_TYPE type = SKILL_TYPE.MINER;

    public Miner(GameObjectChangeListener listener) {
        super(listener);
        setCount(4);
    };

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    @Override
    public void useSkill(Character c) {
        try {
            if (getCount() > 0) {
                Ground currentGround = c.getCurrentGround();
                Ground lastGround = c.getLastGround();

                // only remove ground if its one row lower than last
                if (Math.abs(currentGround.getRowAndCol().x - lastGround.getRowAndCol().x) >= 0
                        && Math.abs(currentGround.getRowAndCol().y - lastGround.getRowAndCol().y) > 0) {

                    getListener().removeGameObjectSelected(currentGround);
                    decrementCount();
                }
            }
        } catch (Exception e) {
            System.err.println("unable to invoke useSkill method");
        }
    }

}