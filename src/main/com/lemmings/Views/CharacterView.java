package src.main.com.lemmings.Views;
import src.main.com.lemmings.utilities.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharacterView extends GameView {
    private ArrayList<BufferedImage> animationFrames;
    private int currentFrame;
    private SkillIcon icon;

    public CharacterView(int x, int y) {
        super(10, 20, x, y);
        currentFrame = 0;
        initializeAnimationFrames();
    }

    private void initializeAnimationFrames() {
        animationFrames = new ArrayList<>();
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-two.png"));
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-three.png"));
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-0.png"));

    }

    @Override
    public void update(int xPos, int yPos) {
        super.setPosX(xPos);
        super.setPosY(yPos);
        setCurrentFrame();
        
        setCharacterViewBounds(xPos, yPos);
    }

    private void setCharacterViewBounds(int xPos, int yPos) {
        this.setBounds(getPosX(), getPosY(), WIDTH, HEIGHT);

        if (icon != null) {
            icon.update(xPos, yPos - 40);
        }
    }

    public void setCurrentFrame() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
    }


    public void setSkillIcon(SkillIcon icon) {
        this.icon = icon;
    }

    public SkillIcon getSkillIcon() {
        return this.icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g;

        try {
            if (animationFrames.get(currentFrame) != null) {
                g2d.drawImage(animationFrames.get(currentFrame), 0, 0, WIDTH, HEIGHT, null); // draw it relative to this

            } else {
                System.err.println("Unable to load character images");
            }
        } catch (Exception e) {
            System.out.println("Unable to load characters!");
        }
    }
}