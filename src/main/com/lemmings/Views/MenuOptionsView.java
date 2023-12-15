package src.main.com.lemmings.Views;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * MenuView.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 * Represents the game's menu options panel.
 * 
 * This class displays and manages the menu options.
 */
public class MenuOptionsView extends JPanel {


    /**
     * Constructs a new MenuOptionsView with default layout and background settings
     */
    public MenuOptionsView() {

        layoutComponents();
        this.setBackground(new Color(0, 0, 0, 0.8f));
    }

    /**
     * Sets up the layout and border of the menu panel
     */
    private void layoutComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    /**
     * Adds a Panel Button to the menu. 
     * 
     * @param button The JPanel representing a button to be added to the menu.
     */
    public void setButtonOnMenu(JPanel button) {
        this.add(button);
    }

}