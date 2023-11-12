package src.main.com.lemmings.Models;

import java.awt.Rectangle;

/**
 * Lemming.java
 */
public class Lemming extends Character {

    public Lemming() {
        super();
    }

    // TODO: Handle updates in y
    @Override
    public void updatePosition() {
        if (isGround) {
            if (isMovingRight) {
                // if we are moving right
                x_pos += speed;
            } else {
                x_pos -= speed;
            }
        }else if (!isGround) {
            y_pos+=5;
        }

    }

    // this method detects if the character's rectangular hitbox has crossed the x,y
    // coordinates of an obstacle
    @Override
    public void detectCollision() {
        // chck if characters x position crosses an obstacle
        // TODO: Update to respond to all obstacles. DONT HARDCODE BOUNDS!

        if (this.getXPosition() >= 600 || this.getXPosition() <= 0) {
            // update direction
            this.toggleDirection();
        }
    }
}