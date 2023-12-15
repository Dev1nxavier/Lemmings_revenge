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
 * MenuController.java
 * 
 * @author Sean Greene
 * @date November 23, 2023
 * 
 * Controls the interactions betwen the menu options view and the corresponding model
 * This controller manages user input on menu buttons, including selection and action handling
 */
public class MenuOptionsController {
    private MenuOptionsView menuView;
    private MenuOptions menuModel;
    private MenuButton isSelected;
    private GameObjectChangeListener listener;

    /**
     * Constructs a MenuOptionsController with a specified view and model
     * Initializes the layout and adds listeners to the menu buttons
     * 
     * @param menuView the view for displaying the menu options
     * @param menuModel the model representing the menu options data
     */
    public MenuOptionsController(MenuOptionsView menuView, MenuOptions menuModel) {
        this.menuView = menuView;
        this.menuModel = menuModel;
        // add components;
        layoutComponents();
        addListeners();
    }

    /**
     * Sets up the menu components from the menu model
     * Adds menu buttons to the view. 
     */
    private void layoutComponents() {
        for (MenuButton button : menuModel.getMenuButtons()) {
            menuView.setButtonOnMenu(button);
        }
        menuView.setButtonOnMenu(menuModel.getPauseButton());
    }

    /**
     * Adds mouse event listeners to the menu buttons for handling user interactions
     */
    private void addListeners() {

        for (MenuButton button : menuModel.getMenuButtons()) {
            button.addMouseListener(new MouseAdapter() {
                /**
                 * When a button is selected the button displays a border. Handles setting the corresponding
                 * skill selected. 
                 */
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
                handlePauseButtonPress();
            }
        });
    }

    /**
     * Registers a GameObjectChangeListern to handle game object events
     * 
     * @param listener the listener notified of a game objec change. 
     */
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

    /**
     * Called when the pause button is pressed. 
     */
    public void handlePauseButtonPress(){
        listener.pauseGame();
    }

    /**
     * Handles the selection of a menu option, creating and activating the corresponding skill.
     * 
     * @param type the skill type selected from the menu
     */
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