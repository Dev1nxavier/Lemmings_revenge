package src.main.com.lemmings.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import src.main.com.lemmings.Controllers.CharacterController;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * LevelView
 */
public class LevelView extends JPanel {
    private JLayeredPane layeredPane; // for rendering characters and game objects
    private final int HEIGHT = 6000;
    private final int WIDTH = 1000;
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

    public void addObjectToView(JLabel go, int layer) {
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
        mouseLocation.setText("" + mouseLoc.x + "," + mouseLoc.y);
        layeredPane.add(mouseLocation, 1);
        mouseLocation.setBounds(500, 100, 100, 200);

    }

    public void clearGameObjectsFromView() {
        this.layeredPane.removeAll();
        revalidate();
    }

    public void redrawView(ArrayList<GameObject>gameObjects, ArrayList<CharacterController> characters){
        clearGameObjectsFromView();
        for (GameObject go : gameObjects) {
            addObjectToView(go, JLayeredPane.DEFAULT_LAYER);
        }
        for (CharacterController ch: characters) {
            addObjectToView(ch.getCharacterView(), JLayeredPane.MODAL_LAYER);
        }
    }
}
