package src.main.com.lemmings.Models.GameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import src.main.com.lemmings.utilities.ImageLoader;

/**
 * CollapsibleGround
 * 
 * @author Sean Greene
 * @date November 26, 2023
 * 
 *       This class extends ground to include a collapsible element. It contains
 *       methods that destroy this
 *       game object after a set number of Characters have collided with it.
 */
public class CollapsibleGround extends Ground {
    // private transient ArrayList<BufferedImage> imageFrames = new ArrayList<>();
    private String[] imageFrames = new String[] {
        "collapsibleGround_02.png",
        "collapsibleGround_03.png",
        "collapsibleGround_03.png"
    };

    private int currentFrame;
    private int count; // number of stages remaining until object is removed from board
    private boolean isActivated = false;
    private Timer timer;
    private long delayAndPeriod = 3000L; // 5 second timer repeat.
    private int offset = 20; // the y-offset of the image
    private final int repeats = 3;

    public CollapsibleGround(int x, int y, Point rowAndCol) {
        super(x, y, 75, 75, rowAndCol);
        setType(ENV_TYPE.COLLAPSIBLE);
        this.text = "" + this.getRowAndCol().x + ", " + this.getRowAndCol().y;
        this.currentFrame = 0;
        setCount(repeats);
        // initializeAnimationFrames();
        setImage("collapsibleGround_01.png");
    }

    private void setCount(int count) {
        this.count = count;
    }

    // private void initializeAnimationFrames() {
    //     this.imageFrames.add(ImageLoader.getImage("collapsibleGround_02.png"));
    //     this.imageFrames.add(ImageLoader.getImage("collapsibleGround_03.png"));
    //     this.imageFrames.add(ImageLoader.getImage("collapsibleGround_03.png"));
    // }

    // public ArrayList<BufferedImage> getImageFrames() {
    //     return this.imageFrames;
    // }

    // public void setImageFrames(ArrayList<BufferedImage> imageFrames) {
    //     this.imageFrames = imageFrames;
    // }

    private void updateGroundModel() {
        decrementCount();
        setCurrentFrame();
        removeObject();
    }

    private void removeObject() {
        if (this.count <= 0) {
            // cancel timer
            timer.cancel();
            timer.purge();
            getGameObjectChangeListener().removeGameObjectSelected(this);
        }
    }

    public void setIsActivated() {
        isActivated = true;
        countdownTimer();
    }

    /**
     * Creates a new timer object that countdowns 3 seconds before calling
     * updateGroundModel.
     */
    private void countdownTimer() {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // update component on Swing's event dispatch thread
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        updateGroundModel();
                    }

                });

            }
        };

        this.timer = new Timer(); // creates a new thread to execute task on
        timer.schedule(task, delayAndPeriod, delayAndPeriod); // run after 5 seconds.

    }

    private void setCurrentFrame() {
        // currentFrame = (currentFrame + 1) % imageFrames.size();
        currentFrame = (currentFrame + 1) % imageFrames.length;
        setImage(imageFrames[currentFrame]);
        // setImage(imageFrames.get(currentFrame));
        this.setObjectBounds(getX_pos(), getY_pos() - offset, WIDTH, HEIGHT);

        // this.repaint();
    }

    private void decrementCount() {
        this.count--;
    }

    public boolean getIsActivated() {
        return this.isActivated;
    }

}