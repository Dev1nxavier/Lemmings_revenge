package src.main.com.lemmings.Views;

import java.awt.Dimension;

import javax.swing.JLabel;

/**
 * GameView.java
 * 
 * @author Sean Greene
 * @date November 17, 2023
 * 
 *       This abstract class defines a basic GameView.
 */
public abstract class GameView extends JLabel {
    private int posX, posY;
    private int width;
    private int height;

    public GameView(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.posX = x;
        this.posY = y;
        setOpaque(false); // for a displaying an image on clear background

    }

    public abstract void update(int posX, int posY);

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.revalidate();
    }

    public void setHeight(int height) {
        this.height = height;
        this.revalidate();
    }

    public int getHeight() {
        return this.height;
    }
}