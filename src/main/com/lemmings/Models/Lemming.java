package src.main.com.lemmings.Models;

import java.awt.Rectangle;

/**
 * Lemming.java
 */
public class Lemming extends Character {

    public Lemming() {
        super();
    }

    @Override
    public void updatePosition() {
        if (isGround) {
            if (isMovingRight) {
                // if we are moving right
                x_pos += speed;
            } else {
                x_pos -= speed;
            }

        } else if (!isGround) {
            y_pos+=7.5;
        }

    }

    // this method detects if the character's rectangular hitbox has crossed the x,y
    // coordinates of an obstacle
    @Override
    public void detectCollision(Rectangle ob) {
        System.out.println("inside detectCollision");
        Rectangle r = this.getBounds();

        // Check if there is overlap along the X axis and Y axis
        boolean xOverlap = (r.x < ob.x + ob.width) && (r.x + r.width > ob.x);
        boolean yOverlap = (r.y < ob.y + ob.height) && (r.y + r.height > ob.y);

        if (xOverlap && yOverlap) {
            System.out.println("Collision Detected!");
            this.toggleDirection();
        }
    }
}