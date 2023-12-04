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
    private Ground lastRemoved;

    // zero-arg constructor
    public Miner() {
        super();
        setCount(2);

    }

    public Miner(GameObjectChangeListener listener) {
        super(listener);
        setCount(2);
    };

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    @Override
    public void useSkill(Character c) {
        System.err.println("Miner.useSkill. Count: " + getCount());
        try {
            if (getCount() > 0) {
                Ground currentGround = c.getCurrentGround();
                Ground lastGround = c.getLastGround();
                if (lastRemoved == null && currentGround !=null) {
                    lastRemoved = currentGround;
                }
                System.out.printf("Current Ground: (%d,%d)\nLast Ground: (%d,%d)\n", currentGround.getRowAndCol().x,
                        currentGround.getRowAndCol().y, lastGround.getRowAndCol().x, lastGround.getRowAndCol().y);
                // only remove ground if its one row lower and one block over than last
                if (Math.abs(currentGround.getRowAndCol().x - lastGround.getRowAndCol().x) >= 0
                        && Math.abs(currentGround.getRowAndCol().y - lastGround.getRowAndCol().y) > 0) {
                    if (!lastRemoved.equals(currentGround)) {
                        lastRemoved = currentGround;
                        decrementCount();
                    }
                    getListener().removeGameObjectSelected(currentGround);

                }

            }
        } catch (Exception e) {
            System.err.println("unable to invoke useSkill method");
        }
    }
}