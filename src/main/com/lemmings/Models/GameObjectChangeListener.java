package src.main.com.lemmings.Models;

import java.awt.Point;

import src.main.com.lemmings.Controllers.CharacterController;
import src.main.com.lemmings.Models.GameObjects.GameObject;

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
    public void addGameObject(GameObject object);
    public void updateGameState();
    public void removeCharacter(CharacterController character);
}