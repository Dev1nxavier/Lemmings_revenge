package src.main.com.lemmings.Controllers;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Views.CharacterView;

/**
 * CharacterController.java
 * 
 * @author Sean Greene
 * @date November 11, 2023
 * 
 * This class is responsible for updating the CharacterView and the 
 * Character model
 */
public class CharacterController {
    private Character ch;
    private CharacterView chView;

    CharacterController(Character ch, CharacterView chView){
        this.ch= ch;
        this.chView = chView;
    }

    public void updateCharacter(){
        ch.updatePosition();
        chView.update(ch.getXPosition());
    }
    
}