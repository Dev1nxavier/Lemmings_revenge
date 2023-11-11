package src.main.com.lemmings.Models;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Character.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       this class models a character.
 */
public abstract class Character {
    protected boolean isMovingRight;
    public int x_pos;
    private int y_pos;
    private final int C_HEIGHT = 20;
    private final int C_WIDTH = 10;
    protected boolean isCollided;
    private boolean isGround;
    public int speed;

    Character() {
        this.isMovingRight = true;
        this.x_pos = 100;
        this.y_pos = 200;
        isCollided = false;
        speed = 5;
    }

    // updates character's position
    public abstract void updatePosition();

    public void toggleDirection(){
        isMovingRight = !isMovingRight;
    }

    // returns the characters hitbox
    public Rectangle getBounds(){
        return new Rectangle(x_pos, y_pos - C_HEIGHT, C_WIDTH, C_HEIGHT);
    }

    public int getXPosition(){
        return this.x_pos;
    }
    public int getYPosition(){
        return this.y_pos;
    }

}