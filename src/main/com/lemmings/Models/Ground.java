package src.main.com.lemmings.Models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Ground
 */
public class Ground extends GameObject {
    String text;

    public Ground(int x, int y, int width, int height, Point rowAndCol) {
        super(x, y, width, height, rowAndCol);
        setImage("ground_tile_02.png");
        setType(ENV_TYPE.GROUND);
        this.text = ""+this.getRowAndCol().x +", "+ this.getRowAndCol().y;
    }

    public boolean shouldDestroy(double y){
        Rectangle ground = this.getBounds();
        return (ground.y > y);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g.drawString(this.text, 10, 20);
    }
}