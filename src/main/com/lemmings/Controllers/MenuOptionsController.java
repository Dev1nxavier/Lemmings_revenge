package src.main.com.lemmings.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.MenuOptions;
import src.main.com.lemmings.Models.Skills.Blocker;
import src.main.com.lemmings.Models.Skills.Builder;
import src.main.com.lemmings.Models.Skills.Excavator;
import src.main.com.lemmings.Models.Skills.Miner;
import src.main.com.lemmings.Models.Skills.Ordinance;
import src.main.com.lemmings.Models.Skills.Skill;
import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.Views.MenuOptionsView;
import src.main.com.lemmings.Views.Components.MenuButton;

/**
 * MenuController
 */
public class MenuOptionsController {
    private MenuOptionsView menuView;
    private MenuOptions menuModel;
    private MenuButton isSelected;
    private GameObjectChangeListener listener;

    public MenuOptionsController(MenuOptionsView menuView, MenuOptions menuModel) {
        this.menuView = menuView;
        this.menuModel = menuModel;
        // add components;
        layoutComponents();
        addListeners();
    }

    private void layoutComponents() {
        for (MenuButton button : menuModel.getMenuButtons()) {
            menuView.setButtonOnMenu(button);
        }
        menuView.setButtonOnMenu(menuModel.getPauseButton());
    }

    private void addListeners() {

        for (MenuButton button : menuModel.getMenuButtons()) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent pressEvent){
                    if (isSelected == button) {
                        button.unSelect(); // if already selected, unselect
                        isSelected = null;
                    }else{
                        if (isSelected !=null) {
                            isSelected.unSelect();
                        }

                        button.select();
                        isSelected = button;
                        handleMenuSelection(button.getSelectedSkillType());
                    }
                }
            });
        }

        menuModel.getPauseButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent pressEvent){
                handleButtonPress();
            }
        });
    }

    public void addGameObjectChangeListener(GameObjectChangeListener listener){
        this.listener = listener;
    }

    public void setIsSelected(MenuButton button){
        this.isSelected = button;
    }

    public MenuOptionsView getMenuView() {
        return menuView;
    }

    public void setMenuView(MenuOptionsView menuView) {
        this.menuView = menuView;
    }

    public MenuOptions getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuOptions menuModel) {
        this.menuModel = menuModel;
    }

    public void handleButtonPress(){
        listener.pauseGame();
    }

    private void handleMenuSelection(SKILL_TYPE type){
        
        Skill newSkill = null;
        // create new skill by type and pass back to LevelController
        switch (type) {
            case BLOCKER-> newSkill = new Blocker();
            case BUILDER-> newSkill = new Builder();
            case EXCAVATOR->newSkill = new Excavator();
            case MINER-> newSkill = new Miner();
            case BOMBER-> newSkill = new Ordinance();
            default-> {
                newSkill = null;
                System.err.println("Unimplemented skill type: " + type);
            }
        }

        if (newSkill != null) {
          listener.updateMenuSelection(newSkill);  
        } else{
            System.err.println("Unable to assign selected skill: " + type);
        }
        
    }

}