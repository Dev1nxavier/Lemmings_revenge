package src.main.com.lemmings.Models;

import java.util.ArrayList;

import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.Views.Components.GameButton;

/**
 * MenuModel
 */
public class MenuModel {
    private SKILL_TYPE selectedType;
    private ArrayList<GameButton> menuButtons;

    public MenuModel() {

        this.menuButtons = new ArrayList<>();

        initializeMenu();
    }

    private void initializeMenu() {
        // load skills
        for (SKILL_TYPE type : SKILL_TYPE.values()) {
            GameButton gb = new GameButton(type.name(), "miner_icon.png", type);
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