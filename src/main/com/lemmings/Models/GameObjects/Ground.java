package src.main.com.lemmings.Models.GameObjects;
import java.awt.Point;
/**
 * Ground.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 * Represents a Ground game object. 
 * This object has special properties that allow a Character to appear on top of it
 * or walk in front of it. It also can be removed by a Character with certain skill actions
 * such as ordinance or excavator
 */
public class Ground extends GameObject {
    String text;

    /**
     * The constructor for a new Ground object. 
     * @param x the x-coordinate of the object
     * @param y the y-coordinate of the object
     * @param width the width of the object
     * @param height the height of the object
     * @param rowAndCol the 2D array address of this object in the LevelModel's map array. 
     */
    public Ground(int x, int y, int width, int height, Point rowAndCol) {
        super(x, y, width, height, rowAndCol);
        setImage("ground_tile_02.png");
        setType(ENV_TYPE.GROUND);
        this.text = "" + this.getRowAndCol().x + ", " + this.getRowAndCol().y;
    }
}