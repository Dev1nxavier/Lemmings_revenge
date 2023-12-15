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
 *       This class defines the game statistics screen. It displays the score,
 *       characters remaining, elapsed time, Skills remaining, and character
 *       count
 */
public class StatsPanelView extends JPanel {
    private transient BufferedImage image;
    private final int WIDTH, HEIGHT;
    private ScoreTextPane score, level, chCount;

    /**
     * Constructs a new StatsPanelView with default width and height.
     * Sets up the layout components including score, level, and character count.
     */
    public StatsPanelView() {
        this.WIDTH = 400;
        this.HEIGHT = 200;
        this.setOpaque(false);
        addLayoutComponents();
    }

    /**
     * sets the preferred size of the panel
     * 
     * @return the preferred panel dimensions
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(this.WIDTH, this.HEIGHT);
    }

    /**
     * Sets up the layout components for the panel.
     * Initiliazies and adds the score, level, and character count text panes. 
     */
    private void addLayoutComponents() {
        this.setLayout(new GridBagLayout());

        this.setImage("gamePanel_02.png");

        // place textfields on panel image
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(2, 2, 2, 2);
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        score = new ScoreTextPane("Score");
        this.add(score, c);

        c.gridx = 1; // next column
        level = new ScoreTextPane("Level");
        this.add(level, c);

        c.gridx = 2;
        chCount = new ScoreTextPane("Characters");
        this.add(chCount, c);
    }

    /**
     * Sets the background image of the panel
     * 
     * @param imageName the filename of the background image
     */
    public void setImage(String imageName) {
        try {
            BufferedImage image = ImageLoader.getImage(imageName);
            this.image = image;
        } catch (Exception e) {
            System.err.println("Unable to load image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the display of the current score.
     * 
     * @param points the current score to be displayed
     */
    public void updateScoreDisplay(int points) {
        score.update("" + points);
    }

     /**
     * Updates the display of the current level.
     * 
     * @param level the current level to be displayed
     */
    public void updateLevelDisplay(int level) {
        this.level.update("" + level);
    }

     /**
     * Updates the display of the current character count.
     * 
     * @param count the current character count to be displayed
     */
    public void updateCharacterCountDisplay(int count) {
        chCount.update("" + count);
    }

    /**
     * Paints the background image onto this panel
     * 
     * @param g the Graphic object to be painted on
     */
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
     * ScoreTextPane
     * 
     * Inner class defining a custom JTextPane which defines two rows
     * holding a label and a text field centered horizontally
     */
    public class ScoreTextPane extends JTextPane {
        private String name;

        ScoreTextPane(String name) {
            this.name = name;
            this.setFont(new Font("SansSerif", Font.BOLD, 14));
            this.setForeground(Color.WHITE);
            update("");
            this.setOpaque(false);
        }

        /**
         * Updates the value displayed in this text pane.
         * 
         * @param value the new value to be displayed
         */
        public void update(String value) {
            this.setText("" + name + "\n" + value);
        }

    }

}
