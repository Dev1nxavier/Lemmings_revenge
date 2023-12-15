package src.main.com.lemmings.Models.GameObjects;

import java.awt.Point;

/**
 * Rock.java
 */
public class Rock extends GameObject {

    public Rock(int x, int y, int width, int height, Point rowAndCol) {
        super(x, y, width, height, rowAndCol);
        setImage("stalagmite_01.png");
        setType(ENV_TYPE.ROCK);
    }

}