package src.main.com.lemmings.Models;

/**
 * Ground
 */
public class Ground extends GameObject {


    public Ground(int x, int y, int width, int height) {
        super(x, y, width, height);
        setImage("ground_tile_02.png");
        setType(ENV_TYPE.GROUND);
        
    }

}