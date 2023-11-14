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

    public void updateView() {
        repaint();
    }

    private void layoutComponents() {
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        background = ImageLoader.GAME_IMAGES.get("cave_background.png");
        // this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new BorderLayout());
        this.add(layeredPane, BorderLayout.CENTER);
    }

    public void addCharacterView(CharacterView chView, int layer) {

        layeredPane.add(chView, Integer.valueOf(layer));
        chView.setBounds(chView.getPosX(), chView.getPosY(), chView.getWIDTH(), chView.getHEIGHT());

        updateView();
    }

    public void addGameObjectsToView(GameObject go, int layer) {
        layeredPane.add(go, Integer.valueOf(layer));
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (map == null) {
            System.out.println("NO MAP!");
            return;
        }

        // typecast to Graphics2D for finer control
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void showMouseLocation(Point mouseLoc) {
        TextArea mouseLocation = new TextArea(1, 1);
        mouseLocation.setBackground(Color.WHITE);
        mouseLocation.setText(mouseLocation.toString());
        this.add(mouseLocation, BorderLayout.SOUTH);
    }

}
