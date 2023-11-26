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

        try {
            // we need to detect an edge
            if (c.isGround()) {
                Point currentGround = c.getCurrentGround().getRowAndCol();
                Point nextGround = null;
                boolean isEdge = true; // set flag for edge.

                if (c.isMovingRight && currentGround.y+1 < 8) {
                    nextGround = new Point(currentGround.x, currentGround.y + 1);

                } else {
                    if (currentGround.y - 1 >= 0) {
                        nextGround = new Point(currentGround.x, currentGround.y - 1);
                    }
                }
                if (nextGround != null) {
                    for (GameObject go : env) {
                        if (nextGround.equals(go.getRowAndCol())) {
                            isEdge = false;
                            break;
                        }
                    }

                    if (isEdge) {
                        decrementCount();

                        // build the bridge
                        Ground bridge = new Bridge(c.getXPosition(), c.getYPosition(), nextGround);
                        listener.modifyGameObject(bridge);
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