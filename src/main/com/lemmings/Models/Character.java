package src.main.com.lemmings.Models;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import src.main.com.lemmings.Models.GameObjects.CollapsibleGround;
import src.main.com.lemmings.Models.GameObjects.Elevator;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Models.GameObjects.Ground;
import src.main.com.lemmings.Models.GameObjects.WarpPortal;
import src.main.com.lemmings.Models.GameObjects.GameObject.ENV_TYPE;
import src.main.com.lemmings.Models.Skills.Skill;
import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;

/**
 * Character.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       Represents a character in the game, managing its movement, collisions,
 *       and interactions.
 *       This class handles character state updates, including position changes,
 *       collision detection,
 *       and skill usage.
 */
public class Character implements Collidable, Serializable {
    private double GRAVITY = 8;
    protected boolean isMovingRight = true;
    private int xPos, yPos;
    private int height = 20;
    private int width = 10;
    protected boolean isCollided, isGround, isDead;
    private Ground currentGround, lastGround = null;
    private Skill skill;
    private SKILL_TYPE type = null;
    private boolean canMoveHorizontally = true; // flag for setting model's ability to move horizontally
    private boolean isOnElevator = false;
    private int speed;
    private GameObjectChangeListener listener;

    /**
     * Default constructor that initializes the character with default position and
     * state.
     */
    public Character() {
        this.xPos = 100;
        this.yPos = 200;
        isCollided = isGround = isDead = false;
        speed = 5;
    }

    /**
     * Updates the character's position based on its current state.
     * The character moves horizontally if on ground, and vertically otherwise.
     */

    public void updatePosition() {
        if (isGround()) {

            if (canMoveHorizontally) {
                moveHorizontally();
            }
        } else {
            if (!isDead)
                moveVertically();
        }
    };

    /**
     * Advance position in the positive or negative x direction
     */
    private void moveHorizontally() {
        xPos += isMovingRight ? speed : -speed;
    }

    /**
     * Move position along the positive y direction
     */
    private void moveVertically() {
        yPos += GRAVITY;
    }

    /**
     * Toggles the characters movement direction along the x axis
     */
    public void toggleDirection() {
        isMovingRight = !isMovingRight;
    }

    // this method detects the right and left bounds of the game panel.
    public void detectHorizontalBounds(Rectangle bounds) {
        int rightBounds = bounds.x + bounds.width - 5;
        if (this.getX_pos() >= rightBounds || this.getX_pos() <= 0) {
            // update direction
            this.toggleDirection();
            return;
        }
    }

    // this method detects the bottom edge of the game panel. If a Character's
    // y-position is
    // greater than the bottom-most vertical bound, the character's isDead flag is
    // set to true.
    public void detectVerticalBounds(Rectangle bounds) {
        int floor = bounds.y + bounds.height - 10;
        if (this.getY_pos() >= floor) {
            this.isDead = true;
        }
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
    // 'ground'. If yes, it sets isGround to true and returns the current Ground
    // object
    public GameObject detectGround(ArrayList<GameObject> gameObjects) {
        setIsGround(false);

        for (GameObject g : gameObjects) {
            if (g instanceof Ground) {
                Rectangle charBounds = this.getBounds();
                Rectangle groundBounds = g.getBounds();

                // check if Character is within margin above ground
                if (isOverlapping(charBounds, groundBounds)) {

                    setIsGround(true);

                    if (this.currentGround != null && this.currentGround != g) {
                        setLastGround(this.currentGround);
                    }
                    setCurrentGround((Ground) g);
                    elevator(g);
                    collapsible(g);
                    return g;
                }
            }
        }
        return null;
    }

    /**
     * Detects if character has collided with a collapsible game object.
     * If true, sets the collapbible game object's isActivated flag to true
     * 
     * @param g the GameObject this Character has collided with
     */
    private void collapsible(GameObject g) {
        if (g.getType() == ENV_TYPE.COLLAPSIBLE) {
            CollapsibleGround cg = (CollapsibleGround) g;
            if (!cg.getIsActivated()) {
                cg.setIsActivated();
            }
        }
    }

    /**
     * Determines if the character has collided with a GameObject of type Elevator
     * If the gameObject is of type elevator, it invokes the elevator's
     * moveVertically method
     * 
     * @param g the GameObject this Character has collided with.
     */
    private void elevator(GameObject g) {
        if (g.getType() == ENV_TYPE.ELEVATOR) {
            Elevator el = (Elevator) g; // cast ground object to Elevator instance

            isOnElevator = true;

            el.setIsMoving(true);
            if (el.getIsMoving()) {
                el.moveVertically();
                setY_pos(el.getY_pos());
            }
        }
    }

    /**
     * Determines if the Characters bounding box overlaps with a GroundObject's
     * bounding box and returns true, otherwise returns false.
     * 
     * @param Char the bounding box of this Character
     * @param g    the GameObject's bounding box
     * @return true if the overlapping condition is met, otherwise false
     */
    private boolean isOverlapping(Rectangle Char, Rectangle g) {
        Rectangle r = this.getBounds();
        Rectangle ground = g.getBounds();

        return (r.y + r.height >= ground.y && r.y + r.height <= ground.y + ground.height
                && r.x < ground.x + ground.width && r.x + r.width > ground.x);
    }

    /**
     * Sets the field lastGround
     * 
     * @param newGround the current ground the Character has collided with.
     */
    private void setLastGround(Ground newGround) {
        if (this.lastGround != newGround) {

            this.lastGround = newGround;
        }
    }

    /**
     * Detects collisions between the character and other collidable objects in the
     * game.
     * This method checks for collisions with various types of game objects. If a
     * collision
     * is detected with specific objects like rocks, or with other characters using
     * certain skills,
     * the character's direction is toggled.
     *
     * @param collidables The list of collidable objects to check for collisions
     *                    against.
     */
    public void detectCollisions(ArrayList<Collidable> collidables) {

        for (Object obj : collidables) {
            if (obj instanceof GameObject && ((GameObject) obj).getType() != ENV_TYPE.PORTAL) {
                // set as a GameObjecct
                GameObject go = (GameObject) obj;
                if (go.getType() == ENV_TYPE.ROCK) {
                    if (detectCollision(go)) {
                        this.toggleDirection();
                    }
                    ;
                }
            } else if (obj instanceof Character) {
                // set as type Character
                Character ch = (Character) obj;
                if (ch.getSkillType() == SKILL_TYPE.BLOCKER && this != ch) {
                    if (detectCollision(ch)) {
                        this.toggleDirection();
                    }
                    ;
                }
            }

        }
    }

    /**
     * Detects collisions between the Character and the level's portal object.
     * 
     * @param portal the portal GameObject
     * @return true if the Character has collided with a portal GameObject
     */
    public boolean detectPortal(WarpPortal portal) {
        if (detectCollision(portal)) {
            portal.playSound("src/main/resources/warp.wav");
            return true;
        }
        return false;
    }

    /**
     * Determines if the character has collided with another collidable object.
     * Collision is detected by checking for overlaps between the bounding
     * rectangles
     * of the character and the other object. An overlap on both the X and Y axes
     * indicates a collision.
     *
     * @param object The collidable object to check for a collision with.
     * @return True if a collision is detected, false otherwise.
     */

    public boolean detectCollision(Collidable object) {

        Rectangle ob = object.getBounds();
        Rectangle r = this.getBounds();
        // Check if there is overlap along the X axis and Y axis
        boolean xOverlap = (r.x < ob.x + ob.width) && (r.x + r.width > ob.x);
        boolean yOverlap = (r.y < ob.y + ob.height) && (r.y + r.height > ob.y);

        if (xOverlap && yOverlap) {
            return true;
        }
        return false;
    }

    /*
     * Getters and setters
     */

    public void setMovingRight(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
    }

    public boolean getIsMovingRight() {
        return this.isMovingRight;
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

    public boolean getIsDead() {
        return this.isDead;
    }

    /**
     * removes the skill that has been set to this Character. 
     * @return true if a skill was set and has been removed, false otherwise. 
     */
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

    @Override
    public Rectangle getBounds() {
        return new Rectangle(xPos, yPos, width, height);
    }

    @Override
    public GameObjectChangeListener getGameObjectChangeListener() {
        return this.listener;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getX_pos() {
        return this.xPos;
    }

    @Override
    public int getY_pos() {
        return this.yPos;
    }

    @Override
    public void setObjectBounds(int x, int y, int width, int height) {
        this.setObjectBounds(x, y, width, height);
    }

    @Override
    public void setGameObjectChangeListener(GameObjectChangeListener listener) {
        this.listener = listener;

    }

    @Override
    public void setHeight(int height) {
        this.height = height;

    }

    @Override
    public void setWidth(int width) {
        this.width = width;

    }

    @Override
    public void setX_pos(int x) {
        this.xPos = x;

    }

    @Override
    public void setY_pos(int y) {
        this.yPos = y;
    }
}