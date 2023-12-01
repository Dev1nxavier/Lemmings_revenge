package src.main.com.lemmings.Models.GameObjects;

import java.awt.Point;

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
    private int passengerCount = 0;
    private final int PASSENGERS = 13;

    public Elevator(int x, int y, Point rowAndCol) {
        super(x, y, 150, 75, rowAndCol);
        this.init_y_pos = y;
        setType(ENV_TYPE.ELEVATOR);
        setImage("elevator_03.png");

        setObjectBounds(x, y, this.getWidth(), this.getHeight());
    }

    // @Override
    // protected void setObjectBounds() {
    //     this.setObjectBounds(getX_pos(), getY_pos() - 25, getWidth(), getHeight());

    // }
    @Override
    public void setObjectBounds(int x, int y, int width, int height) {
        super.setObjectBounds(x, y - 25, width, height);
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public boolean getIsMoving() {
        return this.isMoving;
    }

    public void updatePassengerCount() {
        passengerCount++;
    }

    public int getPassengerCount() {
        return this.passengerCount;
    }

    public int getPASSENGER() {
        return this.PASSENGERS;
    }

    // move elevator negative Y direction
    public void moveVertically() {
        if (isMoving) {
            int y_pos = getY_pos();
            if (y_pos > init_y_pos - 100) {
                y_pos -= 5;
                setY_pos(y_pos);
                setObjectBounds(getX_pos(), y_pos, 150, 100);
            }
        }
    }


}