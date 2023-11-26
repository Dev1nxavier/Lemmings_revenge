package src.main.com.lemmings.Models.GameObjects;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;

import src.main.com.lemmings.utilities.ImageLoader;

/**
 * Elevator
 * 
 * @author Sean Greene
 * @date November 26, 2023
 * 
 *       This class defines the Elevator gameobject. It contains methods for
 *       moving Character objects in the positive y-direction.
 */
public class Elevator extends Ground {
    private boolean isMoving = false;
    private int init_y_pos;

    public Elevator(int x, int y, Point rowAndCol) {
        super(x, y, 150, 100, rowAndCol);
        this.init_y_pos = y;
        setType(ENV_TYPE.ELEVATOR);
        setImage("elevator_03.png");
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setObjectBounds();
    }

    @Override
    protected void setObjectBounds() {
        this.setBounds(getxPos(), getyPos() - 25, getWidth(), getHeight());
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public boolean getIsMoving() {
        return this.isMoving;
    }

    // move elevator negative Y direction
    public void moveVertically() {
        if (isMoving) {
            int y_pos = getyPos();
            if (y_pos > init_y_pos - 125) {
                y_pos -= 5;
                setyPos(y_pos);
                setBounds(getxPos(), y_pos, 150, 100);
            }
        }
    }
}