package src.main.com.lemmings.Views.Components;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * ArrowIcon
 */
public class ArrowIcon implements Icon{
    private int x;
    private int y;

    public ArrowIcon(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'paintIcon'");
    }

    @Override
    public int getIconWidth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIconWidth'");
    }

    @Override
    public int getIconHeight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIconHeight'");
    }
}