package src.main.com.lemmings.Models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

import src.main.com.lemmings.utilities.ImageLoader;

/**
 * GameObject
 */

public abstract class GameObject extends JLabel {
    public static enum ENV_TYPE {
        GROUND, ROCK, CLIFF, HOLE, WATER
    };

    private int xPos;
    private int yPos;
    private BufferedImage image;
    private int width;
    private int height;
    private String name;
    private static int id;
    private ENV_TYPE type;

    public GameObject(int x, int y, int width, int height) {
        id++;
        this.name = "GO ID: " + id;
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        setPreferredsize();
        setObjectBounds();

    }

    protected void setObjectBounds() {
        this.setBounds(getxPos(), getyPos(), width, height);
    }

    private void setPreferredsize() {
        this.setPreferredSize(new Dimension(this.width, this.height));
    }

    public String getName() {
        return this.name;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(String name) {
        this.image = ImageLoader.GAME_IMAGES.get(name);
        // this.setIcon(new ImageIcon(image));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // returns the obect's hitbox
    public Rectangle getBounds() {
        return new Rectangle(xPos, yPos, width, height);

    }

    public ENV_TYPE getType() {
        return this.type;
    }

    public void setType(ENV_TYPE type) {
        this.type = type;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        try {
            if (image != null) {
                g2d.drawImage(image, 0, 0, this.width, this.height, null);
            }
        } catch (Exception e) {
            System.out.println("Unable to load image");
        }
    }

}