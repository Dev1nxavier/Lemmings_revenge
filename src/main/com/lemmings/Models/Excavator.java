package src.main.com.lemmings.Models;

/**
 * Excavator
 */
public class Excavator implements Skill {

    @Override
    public boolean useSkill(Character c) {
        // TODO: this skill removes a block of ground directly beneath character

        // get current position of character
        int xLoc = c.getXPosition();
        int yLoc = c.getYPosition();
        // check if object is ground and...
        if (c.isGround()) {
            System.out.println("Im inside useSkill: Excavator");
            return true;
            // YES: lvl.getGameObject.remove(ground)
            // lvl.setMap(lv.removePointFromMap(ground.getRowAndCol))
            // gamebiew.clearGameObjectsFromView()
            // lvlController.addObjectsToGameView(lvl.getGameObjects)
            // lvlController.addCharacterViewsToGameView(gameview, lvl.getCharacterViews);
        }

        return false;

    }

}