package src.main.com.lemmings.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import src.main.com.lemmings.Models.GameObjects.GameObject.ENV_TYPE;
import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * MenuView.java
 */
public class MenuView extends JPanel {
    private BufferedImage image; // the image on the inner panel
    private int width, height; // the width and height of the inner panel
    private JPanel innerPanel; // the inner panel to display menu options

    public MenuView() {
        this.width = 400;
        this.height = 600;
        layoutComponents();
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        this.image = loadImage("vertical_panel.png");

        // create an inner component to display additional objects
        innerPanel = new JPanel() {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                try {
                    if (image != null) {
                        g2d.drawImage(image, 0, 0, width, height, null);

                    } else {
                        System.err.println("Unable to draw image");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // g2d.dispose();
            }
        };
        innerPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setOpaque(false);
        this.add(innerPanel, BorderLayout.CENTER);
    }

    public void setButtonOnMenu(JPanel button) {
        innerPanel.add(button);
    }

    private BufferedImage loadImage(String name) {
        BufferedImage image = null;

        try {
            image = ImageLoader.getImage(name);
        } catch (Exception e) {
            System.err.println("Unable to load panel image: " + e.getMessage());
            e.printStackTrace();
        }

        return image;
    }

}