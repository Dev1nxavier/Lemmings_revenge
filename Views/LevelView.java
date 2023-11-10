package Views;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * LevelView
 */
public class LevelView extends JPanel {

    LevelView() {
        layoutComponents();
    }

    private void layoutComponents() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        // typecast to Graphics2D for finer control
        Graphics2D g2d = (Graphics2D) g;

        // TODO: Paint stuff
    }

    public void updateView() {
        repaint();
    }

}