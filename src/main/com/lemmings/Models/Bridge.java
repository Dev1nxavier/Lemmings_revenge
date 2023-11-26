package src.main.com.lemmings.Models;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Ground
 */
public class Bridge extends Ground {
    String text;

    public Bridge(int x, int y, Point rowAndCol) {
        super(x, y, 150, 40, rowAndCol);
        setImage("bridge_02.png");
        setType(ENV_TYPE.GROUND);
        this.text = "Bridge";
    }

    public boolean shouldDestroy(double y){
        Rectangle ground = this.getBounds();
        return (ground.y > y);
    }
}