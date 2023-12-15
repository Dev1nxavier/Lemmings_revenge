package src.main.com.lemmings.Models.GameObjects;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

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

    /**
     * Constructs a collapsibleground object. Initializes the object with the default skill type, image, and count. 
     * @param x
     * @param y
     * @param rowAndCol
     */
    public CollapsibleGround(int x, int y, Point rowAndCol) {
        super(x, y, 75, 75, rowAndCol);
        setType(ENV_TYPE.COLLAPSIBLE);
        this.text = "" + this.getRowAndCol().x + ", " + this.getRowAndCol().y;
        this.currentFrame = 0;
        setCount(repeats);
        // initializeAnimationFrames();
        setImage("collapsibleGround_01.png");
    }

    /**
     * Sets the number of stages this object will be available for. 
     * 
     * @param count The number of times this object should exist before being removed from the game state.
     */
    private void setCount(int count) {
        this.count = count;
    }

    /**
     * This method is responsible for updating the image of the ground and decrementing the number of stages before this
     * object is removed from the game state.
     */
    private void updateGroundModel() {
        decrementCount();
        setCurrentFrame();
        removeObject();
    }

    /**
     * Checks the remove condition and if no count remains, removes the game object from the gamestate. 
     */
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
                        playSound("src/main/resources/collapse_sound.wav");
                        updateGroundModel();
                    }

                });

            }
        };

        this.timer = new Timer(); // creates a new thread to execute task on
        timer.schedule(task, delayAndPeriod, delayAndPeriod); // run after 5 seconds.

    }

    /**
     * Sets the current image to be used in this object's view. 
     */
    private void setCurrentFrame() {
        // currentFrame = (currentFrame + 1) % imageFrames.size();
        currentFrame = (currentFrame + 1) % imageFrames.length;
        setImage(imageFrames[currentFrame]);
        // setImage(imageFrames.get(currentFrame));
        this.setObjectBounds(getX_pos(), getY_pos() - offset, WIDTH, HEIGHT);
    }

    private void decrementCount() {
        this.count--;
    }

    public boolean getIsActivated() {
        return this.isActivated;
    }

}