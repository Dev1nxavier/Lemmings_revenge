package src.main.com.lemmings.Models;

/**
 * Rock
 */
public class Rock extends GameObject {

    public Rock(int x, int y, int width, int height) {
        super(x, y, width, height);
        setImage("stalagmite_01.png");
        setType(ENV_TYPE.ROCK);
    }

}