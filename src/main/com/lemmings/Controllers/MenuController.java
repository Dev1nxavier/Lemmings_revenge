package src.main.com.lemmings.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.main.com.lemmings.Models.MenuModel;
import src.main.com.lemmings.Views.MenuView;
import src.main.com.lemmings.Views.Components.GameButton;

/**
 * MenuController
 */
public class MenuController {
    private MenuView menuView;
    private MenuModel menuModel;
    private GameButton isSelected;

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
                    }
                }
            });
        }
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

}