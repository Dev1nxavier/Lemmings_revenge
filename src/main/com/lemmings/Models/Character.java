package src.main.com.lemmings.Models;

import java.awt.Rectangle;
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
 *       this class models a character.
 */
public class Character implements Collidable {
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

    public Character() {
        this.xPos = 100;
        this.yPos = 200;
        isCollided = isGround = isDead = false;
        speed = 5;
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
            if (!isDead)
                moveVertically();
        }
    };

    private void moveHorizontally() {
        xPos += isMovingRight ? speed : -speed;
        // y_pos = (currentGround.getyPos()-getC_HEIGHT()); // ensure character is
        // standing on ground
    }

    private void moveVertically() {
        yPos += GRAVITY;
    }

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

    // this method detects the bottom edge of the game panel. If a Character's y-position is
    // greater than the bottom-most vertical bound, the character's isDead flag is set to true. 
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
        // setCanMoveHorizontally(true);
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

    private void collapsible(GameObject g) {
        if (g.getType() == ENV_TYPE.COLLAPSIBLE) {
            CollapsibleGround cg = (CollapsibleGround) g;
            if (!cg.getIsActivated()) {
                cg.setIsActivated();
            }
        }
    }

    private void elevator(GameObject g) {
        if (g.getType() == ENV_TYPE.ELEVATOR) {
            Elevator el = (Elevator) g; // cast ground object to Elevator instance

            isOnElevator = true;

            updateElevatorPassengerCount(el);

            el.setIsMoving(true);
            if (el.getIsMoving()) {
                // setCanMoveHorizontally(false);
                el.moveVertically();
                setY_pos(el.getY_pos());
            }
        }
    }

    private void updateElevatorPassengerCount(Elevator el) {
        if (!isOnElevator) {
            el.updatePassengerCount();
        }
    }

    private boolean isOverlapping(Rectangle Char, Rectangle g) {
        Rectangle r = this.getBounds();
        Rectangle ground = g.getBounds();

        return (r.y + r.height >= ground.y && r.y + r.height <= ground.y + ground.height
                && r.x < ground.x + ground.width && r.x + r.width > ground.x);
    }

    private void setLastGround(Ground newGround) {
        if (this.lastGround != newGround) {

            this.lastGround = newGround;
        }
    }

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

    public boolean detectPortal(WarpPortal portal) {
         if(detectCollision(portal)){
            portal.playSound("src/main/resources/warp.wav");
            return true;
         }
         return false;
    }

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
        // TODO Auto-generated method stub

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