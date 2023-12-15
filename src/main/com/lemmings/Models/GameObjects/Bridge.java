package src.main.com.lemmings.Models.GameObjects;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Bridge.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 * Defines a Bridge object. 
 */
public class Bridge extends Ground {
    String text;

    public Bridge(int x, int y, Point rowAndCol) {
        super(x, y, 150, 40, rowAndCol);
        setImage("bridge_03.png");
        setType(ENV_TYPE.GROUND);
        this.text = "Bridge";
    }

    public boolean shouldDestroy(double y){
        Rectangle ground = this.getBounds();
        return (ground.y > y);
    }
}