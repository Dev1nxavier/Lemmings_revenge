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
    public int y_pos;
    private final int C_HEIGHT = 20;
    private final int C_WIDTH = 10;
    protected boolean isCollided;
    public boolean isGround = true;
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

    public void toggleDirection() {
        isMovingRight = !isMovingRight;
    }

    public abstract void detectCollision();

    // returns the characters hitbox
    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos - C_HEIGHT, C_WIDTH, C_HEIGHT);
    }

    public void setPosition(int x, int y){
        this.x_pos = x;
        this.y_pos = y;
    }

    public int getXPosition() {
        return this.x_pos;
    }

    public int getYPosition() {
        return this.y_pos;
    }

    // this method determines if object has collided with an obstacle of type
    // 'ground'
    // and returns true, otherwise it returns false;
    public boolean isOnGround(Rectangle ground) {
        Rectangle r = this.getBounds();

        // check for overlap
        if (r.y + r.height > ground.y + .05 && r.x < ground.x + ground.width && r.x + r.width > ground.x) { // extend
                                                                                                            // hit box    // beyond                                                                                     // ground
            // we are on an obstacle
            return true;
        }
        return false;
    }
}