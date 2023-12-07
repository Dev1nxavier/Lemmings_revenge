package src.main.com.lemmings.Models.Skills;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.utilities.ImageLoader;
import src.main.com.lemmings.utilities.Utilities;


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
    private GameObjectChangeListener listener;
    private int count;
    private BufferedImage image;
    private boolean isPlayingSound = false;
    
    public Blocker(){
        setCount(3);  
        setImage();
    }

    public Blocker(GameObjectChangeListener listener){
        this();
        setListener(listener);
    }

    /**
     * This method creates a new GameObject in the same location as the Character object. 
     */
    @Override
    public void useSkill(Character c) {
        // only play the sound one time!
        if (!isPlayingSound) {
          Utilities.playClip("src/main/resources/police_whistle_sound.wav"); 
          isPlayingSound = true; 
        }
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
        return this.count;
    }

    @Override
    public void useSkill(Character c, ArrayList<GameObject> env) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setListener(GameObjectChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public GameObjectChangeListener getListener() {
       return this.listener;
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public void setImage() {
        try {
            this.image = ImageLoader.getImage("blocker_icon.png");
        } catch (Exception e) {
            System.err.println("Unable to load image: " + e.getMessage());
        }
    }
    
}