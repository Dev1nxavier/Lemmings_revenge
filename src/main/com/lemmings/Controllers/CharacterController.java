package src.main.com.lemmings.Controllers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.Lemming;
import src.main.com.lemmings.Views.CharacterView;

/**
 * CharacterController.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 * This class controlls the Character object. The class updates the Character states
 * and the Character view. This class is a 1:1:1 of the Character model, Character view, and this Character controller.
 */
public class CharacterController {
    private BufferedImage[] animationFrames;
    private Character character;
    private CharacterView view;
    
   public CharacterController(Character type, BufferedImage[] animationFrames){
    this.animationFrames = animationFrames; //specific to character type
    this.character = type;
    this.view = new CharacterView(this.animationFrames);

   }

   public void update(){
     // update model
     character.update();
     // update view
     System.out.printf("Inside CharController update: x: %d, y%d", character.getXPosition(), character.getYPosition());
     view.updateFrame(character.getXPosition(), character.getYPosition());
   }
    
}