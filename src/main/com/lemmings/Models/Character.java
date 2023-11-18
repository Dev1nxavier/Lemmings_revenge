package src.main.com.lemmings.Models;

import java.awt.Rectangle;

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
    public boolean isGround = false;
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
        System.out.println("moving right? " + isMovingRight);
    }

    public abstract void detectCollision(Rectangle object);

    // this method detects the right and left bounds of the game panel.
    public abstract void detectBounds();

    // returns the characters hitbox
    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, C_WIDTH, C_HEIGHT);
    }

    public void setPosition(int x, int y) {
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
    public void isOnGround(Rectangle ground) {

        Rectangle r = this.getBounds();
        if (r.y + r.height >= ground.y && r.x < ground.x + ground.width && r.x + C_WIDTH > ground.x) {
            isGround = true;
            return;
        }
    }
}