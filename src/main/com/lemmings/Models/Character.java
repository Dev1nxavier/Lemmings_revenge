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
    private ArrayList <CharacterModelListener> modelListeners;

    public int speed;

    Character() {
        this.isMovingRight = true;
        this.x_pos = 100;
        this.y_pos = 200;
        isCollided = false;
        speed = 5;
        this.type = null;
    }

    /**
     * This method adds instances of a CharacterModelListener to this model
     */
    public void addListeners(CharacterModelListener modelListener) {
        modelListeners.add(modelListener);
    }

    public void updateCharacterModel(){
        updatePosition();
        notifySubscribedWatchers();
    }

    private void notifySubscribedWatchers() {
        for (CharacterModelListener listener : modelListeners) {
            listener.onCharacterModelUpdate(this); // send this model through to subscribers
        }
    }

    // updates character's position
    public void updatePosition() {

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
        // GameObject tempGround = null;
        setIsGround(false);

        for (GameObject g : gameObjects) {
            if (g.getType() == GameObject.ENV_TYPE.GROUND) {
                if (isOverlapping(this.getBounds(), g.getBounds())) {
                    setIsGround(true);
                    setLastGround(g);
                    setCurrentGround((Ground) g);
                    return g;
                }
            }
        }
        return null;
    }

    private boolean isOverlapping(Rectangle Char, Rectangle g) {
        Rectangle r = this.getBounds();
        Rectangle ground = g.getBounds();

        return (r.y + r.height >= ground.y && r.x < ground.x + ground.width && r.x + r.width > ground.x);
    }

    private void setLastGround(GameObject newGround) {
        if (this.currentGround != null
                && (this.lastGround == null || this.lastGround.getUniqueId() != newGround.getUniqueId())) {
            this.lastGround = this.currentGround;
        }
    }

    public GameObject detectCollision(ArrayList<GameObject> gameObjects) {

        GameObject collider = null;

        for (GameObject go : gameObjects) {
            if (go.getType() != GameObject.ENV_TYPE.GROUND) {
                Rectangle r = this.getBounds();
                Rectangle ob = go.getBounds();
                // Check if there is overlap along the X axis and Y axis
                boolean xOverlap = (r.x < ob.x + ob.width) && (r.x + r.width > ob.x);
                boolean yOverlap = (r.y < ob.y + ob.height) && (r.y + r.height > ob.y);

                if (xOverlap && yOverlap) {
                    this.toggleDirection();
                    collider = go;
                    break;
                }
            }
        }
        return collider;
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

    /**
     * InnerCharacter.java
     * 
     * This is an interclass-innerface that defines the event listener for the Character model
     * It contains a single method for updating a View class on update
     */
    public interface CharacterModelListener {
        
        public void onCharacterModelUpdate(Character model);
        
    }
}