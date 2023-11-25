package src.main.com.lemmings.Models;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

import src.main.com.lemmings.utilities.ImageLoader;


/**
 * Blocker.java
 * 
 * @author Sean Greene
 * @date November 22, 2023
 * 
 * This class defines the Blocker skill. The character
 * assigned to this skill will act as an impassible object for other characters
 * similar to a Game Obstacle. 
 * 
 */
public class Blocker implements Skill{
    private ArrayList<BufferedImage> animationFrames;
    
    public Blocker(){
        animationFrames = new ArrayList<>();
        animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_police_1.png"));
        animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_police_3.png"));
        animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_police_2.png"));
        
    }

    @Override
    public boolean useSkill(Character c, GameObject obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
    }

    public ArrayList<BufferedImage> getAnimationFrames(){
        return this.animationFrames;
    }
    /**
     * This method creates a new GameObject in the same location as the Character object. 
     */
    @Override
    public void useSkill(Character c) {
        // prevent this Character from updating its position
        c.setCanMoveHorizontally(false);
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return SKILL_TYPE.BLOCKER;
    }

    @Override
    public int decrementCount() {
        return getCount() - 1;
    }

    @Override
    public int getCount() {
       return 5;
    }
    
}