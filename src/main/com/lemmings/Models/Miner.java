package src.main.com.lemmings.Models;

import java.awt.Point;

/**
 * Miner
 */
public class Miner extends Excavator {
    private final Skill.SKILL_TYPE type = SKILL_TYPE.MINER;
    private GameObjectChangeListener listener;

    public Miner(GameObjectChangeListener listener) {
        setCount(2);
        this.listener = listener;
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
               if (Math.abs(currentGround.getRowAndCol().x - lastGround.getRowAndCol().x)>=0 && Math.abs(currentGround.getRowAndCol().y - lastGround.getRowAndCol().y) > 0) {
                    listener.gameObjectClicked(currentGround);
               }
            }
        } catch (Exception e) {
            System.err.println("unable to update");
        }
    }

    // Miner can interact directly with levelcontroller via dependencyInjection of
    // interface

}