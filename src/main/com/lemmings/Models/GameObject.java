package src.main.com.lemmings.Models;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * GameObject
 */
public class GameObject extends JLabel {

    private int xPos;
    private int yPos;
    private BufferedImage image;
    private final int WIDTH = 175;
    private final int HEIGHT = 175;
    private String name;
    private static int id;

    public GameObject(int x, int y) {
        id++;
        this.name = "GO ID: "+id;
        this.xPos = x;
        this.yPos = y;

        image = loadBufferedImage();
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.setBounds(getxPos(), getyPos(), WIDTH, HEIGHT);
        this.setIcon(new ImageIcon(image));
    }

    private BufferedImage loadBufferedImage() {

        try {
            return ImageIO.read(new File("src/main/resources/ground_tile.png"));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Unable to read file");
            return null;
        }
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

    
}