package src.main.com.lemmings.Models;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Ground
 */
public class Ground extends GameObject {


    public Ground(int x, int y, int width, int height, Point rowAndCol) {
        super(x, y, width, height, rowAndCol);
        setImage("ground_tile_02.png");
        setType(ENV_TYPE.GROUND);
        
    }


    public boolean shouldDestroy(Point mouseXY){
        Rectangle r = this.getBounds();
        boolean xOverlap = (mouseXY.x >= r.x) && (mouseXY.x <= r.x + r.width);
        boolean yOverlap = (mouseXY.y>=r.y) && (mouseXY.y<= r.y + r.width);
        return xOverlap && yOverlap;
    }

}