package src.main.com.lemmings.Views;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * MenuView.java
 */
public class MenuOptionsView extends JPanel {


    public MenuOptionsView() {

        layoutComponents();
        this.setBackground(new Color(0, 0, 0, 0.8f));
    }

    private void layoutComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public void setButtonOnMenu(JPanel button) {
        this.add(button);
    }

}