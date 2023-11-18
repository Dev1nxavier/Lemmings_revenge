package src.main.com.lemmings.Controllers;
import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObject;
import src.main.com.lemmings.Views.CharacterView;

/**
 * CharacterController.java
 * 
 * @author Sean Greene
 * @date November 11, 2023
 * 
 *       This class is responsible for updating the CharacterView and the
 *       Character model
 */
public class CharacterController {
    private Character ch;
    private CharacterView chView;

    CharacterController(Character ch, CharacterView chView) {
        this.ch = ch;
        this.chView = chView;
    }

    public void updateCharacter() {
        ch.updatePosition();
        ch.detectBounds();
        chView.update(ch.getXPosition(), ch.getYPosition());
    }

    /**
     * This method checks if the character has an assigned skill. If it does, it
     * checks the result of the useSkill method and updates the game model
     */
    public GameObject invokeSkill() {
        if (ch.getSkill() != null) {
            if (ch.useSkill()) { // if returns true
                return ch.getCurrentGround();
            }
        }
        return null;
    }
}