package src.main.com.lemmings.Models.Skills;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.Bridge;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Models.GameObjects.Ground;

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

    // zero argumnent constructor
    public Builder() {
        setCount(2);
    }

    public Builder(GameObjectChangeListener listener) {
        this();
        setListener(listener);
    }

    @Override
    public void useSkill(Character c, ArrayList<GameObject> env) {

        try {
            // we need to detect an edge
            if (c.isGround()) {
                Point currentGround = c.getCurrentGround().getRowAndCol();
                Point gap = null;
                boolean isEdge = true; // set flag for edge.
                Ground bridge = null;

                if (c.getIsMovingRight() && currentGround.y + 1 < 8) {
                    gap = new Point(currentGround.x, currentGround.y + 1);

                } else if (currentGround.y - 1 >= 0) {
                    gap = new Point(currentGround.x, currentGround.y - 1);
                }
                if (gap != null) {
                    for (GameObject go : env) {
                        if (gap.equals(go.getRowAndCol())) {
                            isEdge = false;
                            break;
                        }
                    }

                    if (isEdge) {
                        decrementCount();
                        bridge = new Bridge(c.getXPosition() + (c.getIsMovingRight() ? 0 : -150), c.getYPosition(),
                                gap);
                        listener.addGameObject(bridge);
                        destroyAfterDelay(bridge);
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void destroyAfterDelay(Ground bridge) {
        Thread worker = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000); // delay execution 3 seconds
                    System.out.println("Destroying bridge...");
                    getListener().removeGameObjectSelected(bridge);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread is interrupted: " + ie.getMessage());
                }
            }

        });
        worker.start();
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