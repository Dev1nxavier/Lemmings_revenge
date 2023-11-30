package src.main.com.lemmings.Views.Components;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.main.com.lemmings.Views.GameView;

/**
 * Icon
 */
public class SkillIcon extends GameView{
    private BufferedImage image;

    public SkillIcon(BufferedImage image, int posX, int posY){
       super(40,40, posX, posY);
        this.image = image;
    }

    public void update(int posX, int posY) {
        super.setPosX(posX);
        super.setPosY(posY);
        this.setBounds(getPosX(), getPosY(), this.WIDTH, this.HEIGHT);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D)g;

        try {
            g2d.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        } catch (Exception e) {
            System.err.println("Unable to load image");
            e.printStackTrace();
        }
    }
}