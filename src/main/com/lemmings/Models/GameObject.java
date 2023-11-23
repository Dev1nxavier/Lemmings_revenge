package src.main.com.lemmings.Models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private Point rowAndCol;
    private static int id;
    private int uniqueID;
    private ENV_TYPE type;
    private GameObjectChangeListener mouseClickListener;

    public GameObject(int x, int y, int width, int height, Point rowAndCol) {
        id++;
        this.uniqueID = this.id;
        this.rowAndCol = rowAndCol;
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        setPreferredsize();
        setObjectBounds();
        setMouseClickedListener();

    }

    protected void setObjectBounds() {
        this.setBounds(getxPos(), getyPos(), width, height);
    }

    private void setPreferredsize() {
        this.setPreferredSize(new Dimension(this.width, this.height));
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Point getRowAndCol() {
        return rowAndCol;
    }

    public void setRowAndCol(Point rowAndCol) {
        this.rowAndCol = rowAndCol;
    }

    public int getUniqueId() {
        return this.uniqueID;
    }

    public void setUniqueId(int id) {
        this.uniqueID = id;
    }

    /**
     * This method sets the instance variable mouseClickListern to an 
     * instance of a GameObjectClickListener passed as an argument. 
     * @param clickListener an instance of the GameObjectClickListener interface.
     */
    public void setGameObjectClickListener (GameObjectChangeListener clickListener){
        this.mouseClickListener = clickListener;
    }

    /**
     * This method sets a mouseClicked listener to this GameObject instance. On mouseclick,
     * it calls the gameObjectClicked method of the mouseClickListener and passes this as an argument. 
     */
    public void setMouseClickedListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event){
                mouseClickListener.gameObjectClicked(GameObject.this);
            }
        });
    }

}