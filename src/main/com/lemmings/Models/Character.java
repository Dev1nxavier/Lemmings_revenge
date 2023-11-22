package src.main.com.lemmings.Models;

import java.awt.Rectangle;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Skill.SKILL_TYPE;

/**
 * Character.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       this class models a character.
 */
public abstract class Character {
    private static final int GRAVITY = 8;
    protected boolean isMovingRight;
    private int x_pos;
    private int y_pos;
    private final int C_HEIGHT = 20;
    private final int C_WIDTH = 10;
    protected boolean isCollided;
    private boolean isGround = false;
    private Ground currentGround = null;
    private Ground lastGround = null;
    private Skill skill;
    private SKILL_TYPE type;

    public int speed;

    Character() {
        this.isMovingRight = true;
        this.x_pos = 100;
        this.y_pos = 200;
        isCollided = false;
        speed = 5;
        this.type = null;
    }

    // updates character's position
    public void updatePosition() {
        System.out.println("IsGround: " + isGround());
        if (isGround())
            moveHorizontally();
        else
            moveVertically();
    };

    private void moveHorizontally() {
        x_pos += isMovingRight ? speed : -speed;
    }

    private void moveVertically() {
        y_pos += GRAVITY;
    }

    public void toggleDirection() {
        isMovingRight = !isMovingRight;
    }

    public abstract GameObject detectCollision(ArrayList<GameObject> object);

    // this method detects the right and left bounds of the game panel.
    public void detectBounds() {
        if (this.getXPosition() >= 595 || this.getXPosition() <= 0) {
            // update direction
            this.toggleDirection();
            return;
        }
    }

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

    /**
     * This method calls the Skill class's useSkill method for the instance of
     * this Skill class assigned to this Character model.
     * 
     * @param ground
     * 
     * @return true if the Skill class's useSkill method returns true, otherwise
     *         false.
     */
    public boolean useSkill(GameObject ground) {
        boolean val = this.skill.useSkill(this);
        return val;
    }

    // this method determines if object has collided with an obstacle of type
    // 'ground'. If yes, it sets isGround to true.
    public GameObject isOnGround(ArrayList<GameObject> gameObjects) {
        GameObject tempGround = null;
        for (GameObject g : gameObjects) {
            if (g.getType() == GameObject.ENV_TYPE.GROUND) {
                System.out.println("Inside isOnGround");
                Rectangle r = this.getBounds();
                System.out.println("Character bounds: " + r);
                Rectangle ground = g.getBounds();
                System.out.println("Ground bounds: " + ground);
                if (r.y + r.height >= ground.y && r.x < ground.x + ground.width && r.x + r.width > ground.x) {
                    isGround = true;
                    tempGround = g;
                }
            }
        }

        if (tempGround != null) {
            setLastGround(tempGround); // update last
            setCurrentGround((Ground) tempGround); // update current
        }

        return getCurrentGround();
    }

    private void setLastGround(GameObject newGround) {
        if (this.currentGround != null
                && (this.lastGround == null || this.lastGround.getUniqueId() != newGround.getUniqueId())) {
            this.lastGround = this.currentGround;
            System.out.printf("Character.setLastGround:\nlast ground: %d\ncurrent ground: %d\n",
                    getLastGround().getUniqueId(), getCurrentGround().getUniqueId());
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
        return this.currentGround;
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
        setSkillType();
    }

    public Skill getSkill() {
        return this.skill;
    }

    public void setSkillType() {
        this.type = getSkill().getSkillType();
    }

    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    public boolean removeSkill() {
        this.type = null;
        setSkill(null);
        if (getSkill() == null) {
            return true;
        }
        return false;
    }

    public Ground getLastGround() {
        return this.lastGround;
    }
}