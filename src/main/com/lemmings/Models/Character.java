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
    private double GRAVITY = 8;
    protected boolean isMovingRight;
    private int x_pos;
    private int y_pos;
    private int C_HEIGHT = 20;
    private int C_WIDTH = 10;
    protected boolean isCollided;
    private boolean isGround = false;
    private Ground currentGround = null;
    private Ground lastGround = null;
    private Skill skill;
    private SKILL_TYPE type;
    private boolean canMoveHorizontally; // flag for setting model's ability to move horizontally
    private int delay = 0; // method execution delay
    public int speed;

    Character() {
        this.isMovingRight = true;
        this.x_pos = 100;
        this.y_pos = 200;
        isCollided = false;
        speed = 5;
        this.type = null;
        this.canMoveHorizontally = true;
    }

    public void updateCharacterModel() {
        updatePosition();
    }

    // updates character's position
    public void updatePosition() {
        if (isGround()) {
            if (canMoveHorizontally) {
                moveHorizontally();
            }
        } else {
            moveVertically();
        }
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
    public void useSkill(ArrayList<GameObject> env) {
        if (this.skill.getCount() <= 0) {
            return;
        }

        switch (this.getSkillType()) {
            case BUILDER:
                this.skill.useSkill(this, env);
                break;

            default:
                this.skill.useSkill(this);
                break;
        }

    }

    // this method determines if object has collided with an obstacle of type
    // 'ground'. If yes, it sets isGround to true.
    public GameObject isOnGround(ArrayList<GameObject> gameObjects) {
        // GameObject tempGround = null;
        setIsGround(false);

        for (GameObject g : gameObjects) {
            if (g instanceof Ground) {
                if (isOverlapping(this.getBounds(), g.getBounds())) {
                    setIsGround(true);

                    if (this.currentGround != null && this.currentGround != g) {
                        setLastGround(this.currentGround);
                    }
                    setCurrentGround((Ground) g);
                    return g;
                }
            }
        }
        return null;
    }

    private void setLastGround(Ground newGround) {
        if (this.lastGround != newGround) {

            this.lastGround = newGround;
        }
    }

    private boolean isOverlapping(Rectangle Char, Rectangle g) {
        Rectangle r = this.getBounds();
        Rectangle ground = g.getBounds();

        return (r.y + r.height >= ground.y && r.y + r.height <= ground.y + ground.height
                && r.x < ground.x + ground.width && r.x + r.width > ground.x);
    }

    public void detectCollisions(ArrayList<? extends Object> gameObjects) {

        for (Object obj : gameObjects) {
            if (obj instanceof GameObject) {
                // set as a GameObjecct
                GameObject go = (GameObject) obj;
                if (go.getType() != GameObject.ENV_TYPE.GROUND) {
                    detectCollision(go.getBounds());
                }
            } else if (obj instanceof Character) {
                // set as type Character
                Character ch = (Character) obj;
                if (ch.getSkillType() == SKILL_TYPE.BLOCKER && this != ch) {
                    detectCollision(ch.getBounds());
                }
            }

        }
    }

    public boolean detectCollision(Rectangle objectBounds) {
        Rectangle ob = objectBounds;
        Rectangle r = this.getBounds();
        // Check if there is overlap along the X axis and Y axis
        boolean xOverlap = (r.x < ob.x + ob.width) && (r.x + r.width > ob.x);
        boolean yOverlap = (r.y < ob.y + ob.height) && (r.y + r.height > ob.y);

        if (xOverlap && yOverlap) {
            this.toggleDirection();
            return true;
        }
        return false;
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

    public void setCanMoveHorizontally(boolean moveHorizontally) {
        this.canMoveHorizontally = moveHorizontally;
    }

    public int getDelay() {
        return this.delay;
    }
}