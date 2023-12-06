package src.main.com.lemmings.Models.GameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.sound.sampled.*;
import javax.swing.JLabel;

import src.main.com.lemmings.Models.Collidable;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.utilities.ImageLoader;
import src.main.com.lemmings.utilities.Utilities;

/**
 * GameObject
 */

public abstract class GameObject extends JLabel implements Collidable {
    public static enum ENV_TYPE {
        GROUND, ROCK, CLIFF, HOLE, WATER, ELEVATOR, PORTAL, COLLAPSIBLE
    };

    private int xPos, yPos;
    private BufferedImage image;
    private int width, height;
    private Point rowAndCol;
    private ENV_TYPE type;
    private GameObjectChangeListener listener;
    private boolean isPlayingSound = false;

    public GameObject() {

    };

    public GameObject(int x, int y, int width, int height, Point rowAndCol) {
        this();
        this.rowAndCol = rowAndCol;
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        setObjectBounds(x, y, width, height);
    }

    @Override
    public void setObjectBounds(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }

    @Override
    public int getX_pos() {
        return this.xPos;
    }

    @Override
    public void setX_pos(int x) {
        this.xPos = x;
    }

    @Override
    public int getY_pos() {
        return this.yPos;
    }

    @Override
    public void setY_pos(int y) {
        this.yPos = y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(String name) {
        this.image = ImageLoader.getImage(name);
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Point getRowAndCol() {
        return rowAndCol;
    }

    public void setRowAndCol(Point rowAndCol) {
        this.rowAndCol = rowAndCol;
    }

    /**
     * This method sets the instance variable mouseClickListern to an
     * instance of a GameObjectClickListener passed as an argument.
     * 
     * @param clickListener an instance of the GameObjectClickListener interface.
     */
    public void setGameObjectChangeListener(GameObjectChangeListener clickListener) {
        this.listener = clickListener;
    }

    public GameObjectChangeListener getGameObjectChangeListener() {
        return this.listener;
    }

    // TODO: REMOVE!
    /**
     * This method sets a mouseClicked listener to this GameObject instance. On
     * mouseclick,
     * it calls the gameObjectClicked method of the mouseClickListener and passes
     * this as an argument.
     */
    public void setMouseClickedListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                listener.removeGameObjectSelected(GameObject.this);
            }
        });
    }

    /**
     * Plays sound clip from the specified file path passed as an argument. 
     * 
     * This method includes checks for exiting if the audio clip is already playing. It includes a LineListener for listening for 
     * when the clip completes and sets a flag for isPlaying to false. 
     * @note: This method only plays audio files of type 'wav'
     * 
     * @param filePath the absolute path to the audio file. 
     */
    public void playSound(String filePath) {
        Utilities.playClip(filePath);
    }
}