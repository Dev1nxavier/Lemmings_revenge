package src.main.com.lemmings.Views.Components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * GameButton.java
 * 
 * @author Sean Greene
 * @date December 05, 2023
 * 
 *       This class defines a custom game button. It includes methods for
 *       setting the images, tooltips and onClick methods.
 */
public class GameButton extends JPanel {
    private String name;
    private BufferedImage image;
    private int width, height;
    private GameObjectChangeListener listener;

    public GameButton(String name, String imagePath, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        setImage(imagePath);
        layoutComponents();
    }

    public GameButton(String imagePath) {
        this(null, imagePath, 50, 50); // default values
    }

    private void layoutComponents() {
        this.setOpaque(false);
    }

    private void setImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageLoader.getImage(imagePath);
        } catch (Exception e) {
            System.err.println("unable to retrieve image: " + imagePath);
            e.printStackTrace();
        }
        this.image = image;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public String getName(){
        return this.name;
    }

    public void setGameObjectChangeListener(GameObjectChangeListener listener) {
        this.listener = listener;
    }

    public GameObjectChangeListener getListener() {
        return this.listener;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, 0, 0, null);
    }
}