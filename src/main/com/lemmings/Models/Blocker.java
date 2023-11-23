package src.main.com.lemmings.Models;

import src.main.com.lemmings.Controllers.CharacterController;
import src.main.com.lemmings.Views.CharacterView;

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

    @Override
    public boolean useSkill(Character c, GameObject obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
    }

    /**
     * This method creates a new GameObject in the same location as the Character object. 
     */
    @Override
    public boolean useSkill(Character c, CharacterController controller) {
        // prevent this Character from updating its position
        c.setIsWalking(false);
        //new GameObject of type "Rock"
        
        return true;
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

	@Override
	public boolean useSkill(Character c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'useSkill'");
	}
    
}