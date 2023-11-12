package src.main.com.lemmings.Views;

import javax.swing.*;

import src.main.com.lemmings.utilities.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharacterView extends JLabel {
    // load one image for now
    private ArrayList<BufferedImage> animationFrames;
    private int currentFrame;
    private int posX, posY;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;

    public CharacterView(int x, int y) {
        this.posX = x;
        this.posY = y;
        currentFrame = 0;
        layoutComponents();
    }

    public void update(int xPos, int yPos) {
        setCurrentFrame();
        setPosX(xPos);
        setPosY(yPos);
        this.setBounds(getPosX(), getPosY(), getWIDTH(), getHEIGHT());
        this.repaint();
    }

    public void setCurrentFrame() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    private void layoutComponents() {
        animationFrames = new ArrayList<>();
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-two.png"));
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-three.png"));
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-0.png"));

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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        try {
            if (animationFrames.get(currentFrame) != null) {
                g2d.drawImage(animationFrames.get(currentFrame), 0, 0, WIDTH, HEIGHT, null); // draw it relative to this                                                                               // frame
            }else{
                System.err.println("Unable to load character images");
            }
        } catch (Exception e) {
            System.out.println("Unable to load characters!");
        }

    }

}