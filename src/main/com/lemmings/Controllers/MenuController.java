package src.main.com.lemmings.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.MenuModel;
import src.main.com.lemmings.Models.Skills.Blocker;
import src.main.com.lemmings.Models.Skills.Builder;
import src.main.com.lemmings.Models.Skills.Excavator;
import src.main.com.lemmings.Models.Skills.Miner;
import src.main.com.lemmings.Models.Skills.Ordinance;
import src.main.com.lemmings.Models.Skills.Skill;
import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.Views.MenuView;
import src.main.com.lemmings.Views.Components.GameButton;

/**
 * MenuController
 */
public class MenuController {
    private MenuView menuView;
    private MenuModel menuModel;
    private GameButton isSelected;
    private GameObjectChangeListener listener;

    public MenuController(MenuView menuView, MenuModel menuModel) {
        this.menuView = menuView;
        this.menuModel = menuModel;
        // add components;
        layoutComponents();
        addListeners();
    }

    private void layoutComponents() {
        for (GameButton button : menuModel.getMenuButtons()) {
            menuView.setButtonOnMenu(button);
        }
    }

    private void addListeners() {

        for (GameButton button : menuModel.getMenuButtons()) {
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
                        onMenuSelection(button.getSelectedSkillType());
                    }
                }
            });
        }
    }

    public void addGameObjectChangeListener(GameObjectChangeListener listener){
        this.listener = listener;
    }

    public void setIsSelected(GameButton button){
        this.isSelected = button;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    private void onMenuSelection(SKILL_TYPE type){
        
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