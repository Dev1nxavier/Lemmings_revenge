package src.main.com.lemmings.Views.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * GameButton.java
 * 
 * @author Sean Greene
 * @date November 29, 2023
 * 
 *       This class defines a game button. It lays out the visual components of
 *       the button
 */
public class GameButton extends JPanel {
    private String name;
    private BufferedImage image;
    private SKILL_TYPE type;
    private int width, height;
    private boolean isSelected = false;

    public GameButton() {
        this("New Skill", null, SKILL_TYPE.BLOCKER);
    }

    public GameButton(String name, String imageName, SKILL_TYPE type) {
        this.name = name;
        this.width = 300;
        this.height = 40;
        this.type = type;
        setImage(imageName);
        layoutComponent();
    }

    private void layoutComponent() {
        this.image = getImage();
        this.setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    private void setImage(String name) {
        BufferedImage image = null;
        try {
            image = ImageLoader.getImage(name);
        } catch (Exception e) {
            System.err.println("unable to retrieve image: " + e.getMessage());
            e.printStackTrace();
        }
        this.image = image;
    }

    private BufferedImage getImage() {
        return this.image;
    }

    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
    }

    public SKILL_TYPE getSelectedSkillType(){
        return this.type;
    }

    public void unSelect(){
        this.setBorder(null);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, 0, 0, null);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospace", Font.BOLD, 18));
        g2d.drawString(this.name, 100, 40);
    }
}
