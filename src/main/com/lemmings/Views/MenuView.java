package src.main.com.lemmings.Views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import src.main.com.lemmings.utilities.ImageLoader;

/**
 * MenuView.java
 */
public class MenuView extends JPanel {
    private BufferedImage image; // the image on the inner panel
    private int width, height; // the width and height of the inner panel
    private JPanel innerPanel; // the inner panel to display menu options

    public MenuView() {
        layoutComponents();
        this.width = 400;
        this.height = 600;
        this.setOpaque(false);
    }

    private void layoutComponents() {
        this.image = loadImage("vertical_panel.png");

        // create an inner component to display additional objects
        innerPanel = new JPanel(new GridBagLayout()) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                try {
                    if (image !=null) {
                        g2d.drawImage(image, 0, 0, width, height, null);
                    }else{
                        System.err.println("Unable to draw image");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        innerPanel.setOpaque(false);
        this.add(innerPanel);
        this.setVisible(true);
    }

    private BufferedImage loadImage(String name) {
        BufferedImage image = null;
        try {
            image = ImageLoader.GAME_IMAGES.get(name);
        } catch (Exception e) {
            System.err.println("Unable to load panel image: " + e.getMessage());
            e.printStackTrace();
        }

        return image;
    }

}