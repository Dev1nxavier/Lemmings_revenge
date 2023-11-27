package src.main.com.lemmings.Models.GameObjects;

import java.awt.Point;
import java.awt.Rectangle;
import src.main.com.lemmings.Models.GameObjectChangeListener;

/**
 * WarpPortal.java
 * 
 * @author Sean Greene
 * @date November 26, 2023
 * 
 *       This class defines the GameObject WarpPortal. It contains methods for
 *       checking
 *       win conditions for the level.
 */
public class WarpPortal extends GameObject {
    private GameObjectChangeListener listener;

    public WarpPortal(int x, int y, Point rowAndCol) {
        super(x, y, 75, 75, rowAndCol);
        setImage("warp_portal.png");
        setGameObjectChangeListener(listener);
        setType(ENV_TYPE.PORTAL);
    }

    public void setGameObjectChangeListener(GameObjectChangeListener listener){
        this.listener = listener;
    }

    // public void detectCharacterCollisions(ArrayList<Character> characters) {
    //     ArrayList<Character>remove = new ArrayList<>();
    //     for (Character character : characters) {
    //         if (isOverlapping(character.getBounds())) {
    //             remove.add(character);
    //             System.out.printf("Characters to remove: %d\n", remove.size());
    //         }
    //     }

    //     for (Character character : remove) {
    //         listener.removeCharacter(character);
    //     }
    // }

    private boolean isOverlapping(Rectangle ob) {
        Rectangle r = this.getBounds();

        boolean xOverlap = (r.x < ob.x + ob.width) && (r.x + r.width > ob.x);
        boolean yOverlap = (r.y < ob.y + ob.height) && (r.y + r.height > ob.y);

        if (xOverlap && yOverlap) {
            return true;
        }
        return false;
    }
}
