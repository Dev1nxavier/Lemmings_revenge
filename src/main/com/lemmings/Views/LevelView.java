package src.main.com.lemmings.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.image.BufferedImage;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import src.main.com.lemmings.Models.GameObject;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * LevelView
 */
public class LevelView extends JPanel {
    private JLayeredPane layeredPane; // for rendering characters
    private final int HEIGHT = 600;
    private final int WIDTH = 600;
    // TODO: Delete after testing
    public Point pointer;

    private BufferedImage background;
    CharacterView lemming;

    int[][] map;

    public LevelView() {

        layoutComponents();
    }
    
    private void layoutComponents() {
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        background = ImageLoader.GAME_IMAGES.get("cave_background.png");
        this.setLayout(new BorderLayout());
        this.add(layeredPane, BorderLayout.CENTER);
    }

    public void addCharacterToView(CharacterView chView, int layer) {

        layeredPane.add(chView, Integer.valueOf(layer));
        chView.setBounds(chView.getPosX(), chView.getPosY(), chView.getWIDTH(), chView.getHEIGHT());

        repaint();
    }

    public void addGameObjectsToView(GameObject go, int layer) {
        layeredPane.add(go, Integer.valueOf(layer));
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
  
        // typecast to Graphics2D for finer control
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void showMouseLocation(Point mouseLoc) {
        TextArea mouseLocation = new TextArea(1, 20);
        mouseLocation.setForeground(Color.WHITE);
        mouseLocation.setBackground(Color.BLACK);
        mouseLocation.setText(""+mouseLoc.x+","+mouseLoc.y);
       layeredPane.add(mouseLocation, 1);
       mouseLocation.setBounds(500, 100, 100, 200);
        
    }

    public void clearGameObjectsFromView() {
        this.layeredPane.removeAll();
    }

}
