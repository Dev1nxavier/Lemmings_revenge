package src.main.com.lemmings.Models;

import java.awt.image.BufferedImage;

import java.awt.Rectangle;

/**
 * Collidable.java
 * 
 * @author Sean Greene
 * @date November 30, 2023
 * 
 * This interface defines methods for collidable objects that have Rectangular bounds.
 * It contains methods for detecting collisions, the objects x and y position. 
 */

public interface Collidable {
    public int getX_pos();
    public void setX_pos(int x);
    public int getY_pos();
    public void setY_pos(int y);
    public int getWidth();
    public void setWidth(int width);
    public int getHeight();
    public void setHeight(int height);
    public Rectangle getBounds();
    public void setObjectBounds(int x, int y, int width, int height);
    public void setGameObjectChangeListener(GameObjectChangeListener listener);
    public GameObjectChangeListener getGameObjectChangeListener();

}
