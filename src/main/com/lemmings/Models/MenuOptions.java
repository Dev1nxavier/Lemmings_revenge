package src.main.com.lemmings.Models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.Views.Components.GameButton;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * MenuModel
 */
public class MenuOptions {
    private SKILL_TYPE selectedType;
    private ArrayList<GameButton> menuButtons;

    public MenuOptions() {

        this.menuButtons = new ArrayList<>();

        initializeMenu();
    }

    private void initializeMenu() {
        // load skills
        for (SKILL_TYPE type : SKILL_TYPE.values()) {
            String image;
            switch (type) {
                case BLOCKER -> image = "blocker_icon.png";
                case BUILDER -> image = "bridge_icon.png";
                case EXCAVATOR -> image = "miner_icon.png";
                case MINER -> image = "miner_icon.png";
                case BOMBER -> image = "dynamite_icon.png";
                default-> image = "dynamite_icon.png";
            }

            GameButton gb = new GameButton(type.name(),image, type);
            menuButtons.add(gb);
        }

    }

    public SKILL_TYPE getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(SKILL_TYPE selectedType) {
        this.selectedType = selectedType;
    }

    public ArrayList<GameButton> getMenuButtons() {
        return this.menuButtons;
    }

    public void setMenuButtons(ArrayList<GameButton> menuButtons) {
        this.menuButtons = menuButtons;
    }

}