package src.main.com.lemmings.Views.Components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * An innerclass displays the level's background image as a separate component
 * in
 * the LevelView class.
 */
public class BackgroundPanel extends JPanel {
    private BufferedImage image;

    public BackgroundPanel() {
        setOpaque(false);
    }

    public BackgroundPanel(BufferedImage image) {
        this();
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}