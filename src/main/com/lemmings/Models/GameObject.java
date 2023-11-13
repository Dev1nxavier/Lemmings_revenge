package src.main.com.lemmings.Models;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import src.main.com.lemmings.utilities.ImageLoader;

/**
 * GameObject
 */


public class GameObject extends JLabel {

public static enum ENV_TYPE{ GROUND,ROCK,CLIFF, HOLE, WATER};

    private int xPos;
    private int yPos;
    private BufferedImage image;
    private final int WIDTH = 175;
    private final int HEIGHT = 175;
    private String name;
    private static int id;
    private ENV_TYPE type;

    public GameObject(int x, int y) {
        id++;
        this.name = "GO ID: "+id;
        this.xPos = x;
        this.yPos = y;

        image = ImageLoader.GAME_IMAGES.get("ground_tile_02.png");
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.setBounds(getxPos(), getyPos(), WIDTH, HEIGHT);
        this.setIcon(new ImageIcon(image));
        this.type = ENV_TYPE.GROUND;
    }

    public String getName(){
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    // returns the obect's hitbox
    public Rectangle getBounds(){
        return new Rectangle(xPos, yPos, WIDTH, HEIGHT);
        
    }

    public ENV_TYPE getType(){
        return this.type;
    }
    
}