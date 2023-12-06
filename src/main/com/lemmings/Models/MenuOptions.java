package src.main.com.lemmings.Models;

import java.util.ArrayList;

import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.Views.Components.GameButton;
import src.main.com.lemmings.Views.Components.MenuButton;

/**
 * MenuModel
 */
public class MenuOptions {
    private SKILL_TYPE selectedType;
    private ArrayList<MenuButton> menuButtons;
    private GameButton pauseButton;

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
                default-> image = "pauseButton.png";
            }

            MenuButton gb = new MenuButton(type.name(),image, type);
            menuButtons.add(gb);
        }

        // add pause button
        pauseButton = new GameButton("pause", "pauseButton.png", 50, 50);
         

    }

    public SKILL_TYPE getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(SKILL_TYPE selectedType) {
        this.selectedType = selectedType;
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return this.menuButtons;
    }

    public void setMenuButtons(ArrayList<MenuButton> menuButtons) {
        this.menuButtons = menuButtons;
    }

    public GameButton getPauseButton(){
        return this.pauseButton;
    }

}