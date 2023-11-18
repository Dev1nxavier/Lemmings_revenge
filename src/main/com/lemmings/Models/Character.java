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
    private boolean isGround = false;
    private Ground currentGround = null;
    private Skill skill;

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

    public void setGround(Ground g) {
        this.currentGround = g;
    }

    public boolean useSkill() {
        if (skill.useSkill(this) == true) {
            return true;
        }
        return false;
    }

    // this method determines if object has collided with an obstacle of type
    // 'ground'. If yes, it sets isGround to true and returns the current ground
    // object.
    public void isOnGround(Ground g) {

        Rectangle r = this.getBounds();
        Rectangle ground = g.getBounds();
        if (r.y + r.height >= ground.y && r.x < ground.x + ground.width && r.x + C_WIDTH > ground.x) {
            isGround = true;
            setGround(g);
        }

    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
    }

    public int getX_pos() {
        return x_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public int getC_HEIGHT() {
        return C_HEIGHT;
    }

    public int getC_WIDTH() {
        return C_WIDTH;
    }

    public boolean isCollided() {
        return isCollided;
    }

    public void setCollided(boolean isCollided) {
        this.isCollided = isCollided;
    }

    public boolean isGround() {
        return isGround;
    }

    public void setIsGround(boolean isGround) {
        this.isGround = isGround;
    }

    public Ground getCurrentGround() {
        return currentGround;
    }

    public void setCurrentGround(Ground currentGround) {
        this.currentGround = currentGround;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill(){
        return this.skill;
    }
}