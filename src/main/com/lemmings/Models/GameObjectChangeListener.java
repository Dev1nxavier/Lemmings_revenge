package src.main.com.lemmings.Models;

/**
 * GameObjectClickListener
 * 
 * @author Sean Greene
 * @date November 17, 2023
 * 
 * This interface defines a click listener.
 */
public interface GameObjectChangeListener {

    public void gameObjectClicked(GameObject object);
    public void modifyGameObject(GameObject object);
    public void updateGameState();
}