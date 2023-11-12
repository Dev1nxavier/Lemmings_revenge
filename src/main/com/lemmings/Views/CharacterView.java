package src.main.com.lemmings.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Innerclass for Character view TODO: Will move to own class
 */
public class CharacterView extends JPanel {
    //load one image for now
    private ArrayList <BufferedImage> animationFrames;
    private int currentFrame;
    private int posX, posY;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;

    public CharacterView(ArrayList<BufferedImage> frames, int x, int y){
        this.posX = x;
        this.posY = y;
        this.animationFrames = frames;
        currentFrame = 0;
        layoutComponents();
    }

    public void update(int xPos, int yPos){
        setCurrentFrame();
        setPosX(xPos);
        setPosY(yPos);
        this.repaint();
    }

    public void setCurrentFrame(){
        currentFrame = (currentFrame + 1) % animationFrames.size();
    }

    private void layoutComponents() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setOpaque(false);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // draw the thingy
        g2d.drawImage(animationFrames.get(currentFrame), posX, posY, WIDTH, HEIGHT, null);
    }

}