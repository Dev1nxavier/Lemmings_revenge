package src.main.com.lemmings.Models.Skills;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.GameObject;

/**
 * Ordinance.java
 * 
 * @author Sean Greene
 * @date November 25, 2023
 * 
 *       This class contains methods for removing GameObjects from the gamestate
 *       and gameview.
 */
public class Ordinance implements Skill {
    private final SKILL_TYPE type = SKILL_TYPE.BOMBER;
    private int count;
    private GameObjectChangeListener listener;

    // zero-arg constructor
    public Ordinance() {
        setCount(1);
    }

    public Ordinance(GameObjectChangeListener listener) {
        this();
        setListener(listener);
    };

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    // removes all gameObjects within 1 unit length of the character
    @Override
    public void useSkill(Character c) {

        try {
            Thread worker = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(3000); // delay execution 3 seconds
                        if (getCount() > 0) {

                            if (c.isGround()) {
                                decrementCount();
                                Point currentGround = c.getCurrentGround().getRowAndCol();
                                Point left = new Point(currentGround.x, currentGround.y - 1);
                                Point right = new Point(currentGround.x, currentGround.y + 1);
                                Point top = new Point(currentGround.x - 1, currentGround.y);

                                getListener().removeGameObjectSelected(top);
                                getListener().removeGameObjectSelected(right);
                                getListener().removeGameObjectSelected(left);
                                getListener().removeGameObjectSelected(currentGround);
                            }
                        }
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread is interrupted: " + ie.getMessage());
                    }
                }
            });
            worker.start();
        } catch (Exception e) {
            System.err.println("Unable to invoke useSkill method" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void useSkill(Character c, ArrayList<GameObject> env) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
    }

    @Override
    public ArrayList<BufferedImage> getAnimationFrames() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnimationFrames'");
    }

    @Override
    public int decrementCount() {
        return this.count--;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setListener(GameObjectChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public GameObjectChangeListener getListener() {
        return this.listener;
    }

}