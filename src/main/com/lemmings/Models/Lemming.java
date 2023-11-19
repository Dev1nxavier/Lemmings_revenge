package src.main.com.lemmings.Models;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Lemming.java
 */
public class Lemming extends Character {

    public Lemming() {
        super();
    }

    @Override
    public void updatePosition() {
        if (isGround()) {
            if (isMovingRight) {
                // if we are moving right
                x_pos += speed;
            } else {
                x_pos -= speed;
            }

        } else if (!isGround()) {
            y_pos += 7.5;
        }
    }

    @Override
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

    // this method detects the right and left bounds of the game panel.
    @Override
    public void detectBounds() {
        if (this.getXPosition() >= 550 || this.getXPosition() <= 0) {
            // update direction
            this.toggleDirection();
            return;
        }
    }
}