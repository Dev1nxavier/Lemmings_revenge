package src.main.com.lemmings.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

import src.main.com.lemmings.utilities.Utilities;

/**
 * LevelView
 */
public class LevelView extends JPanel {

    private final int HEIGHT = 600;
    private final int WIDTH = 600;
    private ArrayList<BufferedImage> obstacles = new ArrayList<>();
    //TODO: Delete after testing
    public Point pointer;

    private BufferedImage background;
    CharacterView lemming;

    int[][] map;

    public LevelView() {

        layoutComponents();
        obstacles.add(Utilities.getGameImages("src/main/com/lemmings/Views/stalagmite_01.png"));
        obstacles.add(Utilities.getGameImages("src/main/resources/ground_tile_02.png"));
    }

    public void updateView() {
        repaint();
    }

    private void layoutComponents() {
        background = Utilities.getGameImages("src/main/resources/cave_background.png");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void addCharacterView(CharacterView chView){
        lemming = chView;
              // add a character
        this.add(lemming);
        this.setComponentZOrder(chView, 0);
        updateView();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (map == null) {
            System.out.println("NO MAP!");
            return;
        }

        final int OBSTACLE_WIDTH = this.WIDTH / 8; // the width of obstacle
        final int OBSTACLE_HEIGHT = this.HEIGHT / 8; // the height of an obstacle
        // lay out components starting with top left of panel
        int px = 0;
        int py = 0;

        // typecast to Graphics2D for finer control
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

        // TODO: Update to use imageIcons
        // for every 1 in model, ground, 2 = rock
        if (obstacles != null) {
            for (int[] row : map) {
                for (int obstacle : row) {
                    if (obstacle == 2) {
                        g2d.drawImage(obstacles.get(0), px, py, 75, OBSTACLE_HEIGHT, null);
                    } else if (obstacle == 1) {
                        g2d.drawImage(obstacles.get(1), px, py, OBSTACLE_WIDTH, OBSTACLE_HEIGHT, null);
                    }
                    // either way, advance along the x axis
                    px += OBSTACLE_WIDTH;
                }
                px = 0;
                // jump down to the next row
                // FIXME: Update to calculate next row based on dimensions
                py += OBSTACLE_HEIGHT;
            }

        } else {
            System.err.println("Image was null?");
        }

    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void showMouseLocation(Point mouseLoc){
        TextArea mouseLocation = new TextArea(1, 1);
        mouseLocation.setText(mouseLocation.toString());
        this.add(mouseLocation);
        System.out.println(mouseLoc.toString());
    }

}
