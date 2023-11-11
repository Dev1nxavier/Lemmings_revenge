package src.main.com.lemmings.Models;

/**
 * Lemming.java
 */
public class Lemming extends Character {

    public Lemming() {
        super();
    }

    // TODO: Handle updates in y
    @Override
    public void update() {
        System.out.println("Inside Lemming's update method!");
        if (isMovingRight) {
            // if we are moving right
            x_pos += speed;
        } else {
            x_pos -= speed;
        }

    }
}