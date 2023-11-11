package src.main.com.lemmings.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * LevelView
 */
public class LevelView extends JPanel {
    private final int HEIGHT = 600;
    private final int WIDTH = 600;
    private ArrayList<BufferedImage> obstacles = new ArrayList<>();
    private BufferedImage background;
    private CharacterView cView;

    int[][] map;

    public LevelView() {

        layoutComponents();
        obstacles.add(loadImage("src/main/com/lemmings/Views/stalagmite_01.png"));
        obstacles.add(loadImage("src/main/resources/ground_tile_02.png"));
    }

    public void updateView() {
        // cView.setBounds(cView.getX_pos(), cView.getY_pos(),
        // 10, 20);
        // this.add(cView);
        // cView.updateFrame(x_pos, y_pos);
        repaint();

    }

    private void layoutComponents() {
        background = loadImage("src/main/resources/cave_background.png");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new BorderLayout());
        // this.setLayout(null);

    }

    // adds a new instance of CharacterView to the game window
    public void addCharacterView(CharacterView cView, int x, int y) {
        this.cView = cView;
        // if using null layout, must explicitly define bounds
        cView.setBounds(cView.getX_pos(), x, y, cView.getCharacterHeight());
        this.add(cView, BorderLayout.CENTER);
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

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return image;
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("Unable to laod image");
            return null;
        }
    }

}