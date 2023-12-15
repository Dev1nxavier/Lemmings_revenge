package src.main.com.lemmings.Models.Skills;

import java.awt.image.BufferedImage;

import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameObjects.Ground;
import src.main.com.lemmings.utilities.ImageLoader;
import src.main.com.lemmings.utilities.Utilities;

/**
 * Miner
 */
public class Miner extends Excavator {
    private final SKILL_TYPE type = SKILL_TYPE.MINER;
    private Ground lastRemoved;
    private boolean hasInvokedSkill = false;

    // zero-arg constructor
    public Miner() {
        super();
    }

    public Miner(GameObjectChangeListener listener) {
        super(listener);
        setCount(2);
    };

        @Override
    public void setImage() {
        try {
            BufferedImage newIMage = ImageLoader.getImage("miner_icon.png");
            super.setImage(newIMage);
        } catch (Exception e) {
            System.err.println("Unable to load image: " + e.getMessage());
        }
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return this.type;
    }

    @Override
    public void useSkill(Character c) {
        if (hasInvokedSkill) {
            return;
        }

        Utilities.playClip("src/main/resources/jackhammer_sound.wav");

        try {
            if (getCount() > 0) {
                hasInvokedSkill = true;
                Ground currentGround = c.getCurrentGround();
                Ground lastGround = c.getLastGround();
                if (lastRemoved == null && currentGround !=null) {
                    lastRemoved = currentGround;
                }
                // only remove ground if its one row lower and one block over than last
                if (Math.abs(currentGround.getRowAndCol().x - lastGround.getRowAndCol().x) >= 0
                        && Math.abs(currentGround.getRowAndCol().y - lastGround.getRowAndCol().y) > 0) {
                    if (!lastRemoved.equals(currentGround)) {
                        lastRemoved = currentGround;
                        decrementCount();
                    }
                    getListener().removeGameObjectSelected(currentGround);
                }
                hasInvokedSkill = false;

            }else if (getCount()<=0) {
                c.removeSkill();
            }
        } catch (Exception e) {
            System.err.println("unable to invoke useSkill method");
        }
    }
}