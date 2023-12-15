package src.main.com.lemmings.Views.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;

import src.main.com.lemmings.Models.Skills.Skill.SKILL_TYPE;

/**
 * GameButton.java
 * 
 * @author Sean Greene
 * @date November 29, 2023
 * 
 *       This class defines a menu button. It lays out the visual components of
 *       the button
 */
public class MenuButton extends GameButton {
    private SKILL_TYPE type;

    public MenuButton() {
        this("New Skill", null, SKILL_TYPE.BLOCKER);
    }

    public MenuButton(String name, String imageName, SKILL_TYPE type) {
        super(name, imageName, 300, 40);
        this.type = type;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 40);
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
        g2d.drawImage(this.getImage(), 0, 0, null);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospace", Font.BOLD, 18));
        g2d.drawString(this.getName(), 100, 40);
    }
}
