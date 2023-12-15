package src.main.com.lemmings.Models.GameObjects;

import java.awt.Point;

/**
 * Bridge.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       Defines a Bridge object.
 */
public class Bridge extends Ground {
    String text;

    public Bridge(int x, int y, Point rowAndCol) {
        super(x, y, 75, 40, rowAndCol);
        setImage("bridge.png");
        setType(ENV_TYPE.GROUND);
        this.text = "Bridge";
    }

    /**
     * Sets the view bounds of this game object.
     * For use in the LevelView layeredPane where the layoutmanager is null
     */
    @Override
    public void setObjectBounds(int x, int y, int width, int height) {
        this.setBounds(x, y+25, width, height);
    }

}