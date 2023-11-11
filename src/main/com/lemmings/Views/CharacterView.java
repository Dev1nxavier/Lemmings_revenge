package src.main.com.lemmings.Views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * CharacterView.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       This class defines the Characters visual representation
 */
public class CharacterView extends JPanel {
    private BufferedImage[] animationFrames;
    private int currentFrame;
    private int x_pos = 0;
    private int y_pos = 0;
    private final int C_HEIGHT = 20;
    private final int C_WIDTH = 10;

    public CharacterView(BufferedImage[] animationFrames) {
        this.animationFrames = animationFrames;
        this.setPreferredSize(new Dimension(C_WIDTH, C_HEIGHT));
        this.setOpaque(false);
        currentFrame = 0;
        // this.x_pos = 200;
        // this.y_pos = 250;
    }

    public void updateCharacterPosition(int x, int y) {
        // Swing is not thread safe. Ensure update runs on
        // event dispatch thread
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                System.out.printf("Inside updateCharPos: x: %d, y: %d\n", x, y);
                updateFrame(x, y);
            }

        });
    }

    // updates character's position on screen
    public void updateFrame(int x, int y) {
        this.setX_pos(x);
        this.setY_pos(y);
        currentFrame = (currentFrame + 1) % animationFrames.length;
        setBounds(this.x_pos, this.y_pos, C_WIDTH, C_HEIGHT);
        System.out.printf("Updated Ch. View: x: %d, y: %d \ndx: %d\n", this.getX_pos(), this.getY_pos(), x);
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)  {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        System.out.println("Repainting character!");

        // draw current image at character position
        g2d.drawImage(animationFrames[0], x_pos, y_pos, C_WIDTH, C_HEIGHT, null);
        System.out.printf("Inside CView paintComponent(x: %d, y: %d)\n", x_pos, y_pos);
    }

    public BufferedImage[] getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(BufferedImage[] animationFrames) {
        this.animationFrames = animationFrames;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getX_pos() {
        return x_pos;
    }

    public void setX_pos(int x) {
        this.x_pos = x;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setY_pos(int y) {
        this.y_pos = y;
    }

    public int getCharacterHeight() {
        return C_HEIGHT;
    }

    public int getCharacterWidth() {
        return C_WIDTH;
    }

}
