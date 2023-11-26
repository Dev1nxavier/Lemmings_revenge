package src.main.com.lemmings.Models;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Builder.java
 * 
 * @author Sean Greene
 * @date November 25, 2023
 * 
 *       This class defines the Builder skill. It contains methods for building
 *       a bridge GameObject
 */
public class Builder implements Skill {
    private GameObjectChangeListener listener;
    private int count;
    private SKILL_TYPE type = SKILL_TYPE.BUILDER;

    public Builder(GameObjectChangeListener listener) {
        setCount(2);
        setListener(listener);
    }

    @Override
    public void useSkill(Character c, ArrayList<GameObject> env) {
        c.setCanMoveHorizontally(false);
        try {
            // we need to detect an edge
            if (c.isGround()) {
                Point currentGround = c.getCurrentGround().getRowAndCol();
                Point nextGround;
                if (c.isMovingRight) {
                    nextGround = new Point(currentGround.x, currentGround.y + 1);
                    System.out.printf("Current Ground: %s\nNext Ground: %s\n", currentGround, nextGround);
                } else {
                    if (currentGround.y - 1 >= 0) {
                        nextGround = new Point(currentGround.x, currentGround.y - 1);
                    } else {
                        nextGround = null;
                    }

                }

                if (nextGround != null) {
                    for (GameObject go : env) {
                        if (nextGround.equals(go.getRowAndCol())) {
                            c.setCanMoveHorizontally(true);
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void useSkill(Character c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
    }

    @Override
    public ArrayList<BufferedImage> getAnimationFrames() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnimationFrames'");
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
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