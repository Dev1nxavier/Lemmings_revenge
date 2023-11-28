package src.main.com.lemmings.Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import src.main.com.lemmings.utilities.ImageLoader;

/**
 * ScorePanel.java
 * 
 * @author Sean Greene
 * @date November 27, 2023
 * 
 *       This class defines the game information screen. It displays the score,
 *       characters remaining, elapsed time, Skills remaining, and character
 *       count
 */
public class GameStateView extends JPanel {
    private BufferedImage image;
    private final int WIDTH, HEIGHT;
    private ScoreTextPane score, level, chCount;

    public GameStateView() {
        this.WIDTH = 400;
        this.HEIGHT = 200;
        this.setOpaque(false);
        addLayoutComponents();
    }

    private void addLayoutComponents() {

        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.setImage("gamePanel_02.png");

        // place textfields on panel image
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(layout);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);

        c.insets = new Insets(2, 2, 2, 2);
        c.weightx = 1;
        c.weighty = 1;
        // c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        score = new ScoreTextPane("Score", layout, c);
        level = new ScoreTextPane("Level", layout, c);
        chCount = new ScoreTextPane("Characters", layout, c);
        this.add(spacer,c);
        this.add(score);
        this.add(level);
        this.add(chCount);
        this.add(spacer,c);

        c.gridy = 1; // next row
        c.weighty = 0; // labels in first 1/3 of panel
        c.fill = GridBagConstraints.BOTH; // from end to end
        this.add(spacer, c); // empty JPanel as filler
    }

    public void setImage(String imageName) {
        try {
            BufferedImage image = ImageLoader.GAME_IMAGES.get(imageName);
            this.image = image;
        } catch (Exception e) {
            System.err.println("Unable to load image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateScoreDisplay(int points){
        score.update(""+points);
    }

    public void updateCharacterCountDisplay(int count){
        chCount.update(""+count);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        try {
            if (image != null) {
                g2d.drawImage(image, 0, 0, this.WIDTH, this.HEIGHT, null);
            } else {
                System.err.println("Unable to laod image");
            }
        } catch (Exception e) {
            System.err.println("Unable to apply image: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * InnerScorePanel
     */
    public class ScoreTextPane extends JTextPane {
        private String name;

        ScoreTextPane(String name, GridBagLayout layout, GridBagConstraints c) {
            this.name = name;
            this.setFont(new Font("SansSerif", Font.BOLD, 14));
            this.setForeground(Color.WHITE);
            update("");
            this.setOpaque(false);
            layout.setConstraints(this, c);
        }

        public void update(String value) {
            this.setText("" + name + "\n" + value);
        }

    }

}
