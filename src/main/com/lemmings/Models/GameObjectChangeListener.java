package src.main.com.lemmings.Models;

import java.awt.Point;

/**
 * GameObjectClickListener
 * 
 * @author Sean Greene
 * @date November 17, 2023
 * 
 * This interface defines a click listener.
 */
public interface GameObjectChangeListener {

    public void removeGameObjectSelected(GameObject object);
    public void removeGameObjectSelected(Point point);
    public void modifyGameObject(GameObject object);
    public void updateGameState();
}