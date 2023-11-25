package src.main.com.lemmings.Views;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

/**
 * GameView.java
 * 
 * @author Sean Greene
 * @date November 17, 2023
 * 
 * This abstract class defines a basic GameView.
 */
public abstract class GameView extends JLabel {
    protected int posX, posY;
    protected int WIDTH;
    protected int HEIGHT;

    public GameView(int width, int height, int x, int y){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.posX = x;
        this.posY = y;

        setOpaque(false); // for a displaying an image on clear background

    }

    public abstract void update(int posX, int posY);

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(WIDTH, HEIGHT);
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getWIDTH(){
        return this.WIDTH;
    }

    public void setWIDTH(int width){
        this.WIDTH = width;
    }

    public void setHEIGHT(int height){
        this.HEIGHT = height;
    }

    public int getHEIGHT(){
        return this.HEIGHT;
    }

    @Override
    protected abstract void paintComponent(Graphics g);
    
}