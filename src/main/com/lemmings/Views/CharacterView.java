package src.main.com.lemmings.Views;

import src.main.com.lemmings.utilities.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharacterView extends GameView {
    private ArrayList<BufferedImage> animationFrames;
    private int currentFrame;
    private SkillIcon icon;
    private int DELAY;
    private int countUp = 0;

    public CharacterView(int x, int y) {
        super(10, 20, x, y);
        currentFrame = 0;
        initializeAnimationFrames();
        this.DELAY = 0;
    }

    private void initializeAnimationFrames() {
        animationFrames = new ArrayList<>();
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-two.png"));
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-three.png"));
        this.animationFrames.add(ImageLoader.GAME_IMAGES.get("Lemming_pose-0.png"));
    }

    public ArrayList<BufferedImage> getAnimationFrames() {
        return this.animationFrames;
    }

    @Override
    public void update(int xPos, int yPos) {
        super.setPosX(xPos);
        super.setPosY(yPos);
        setCurrentFrame();
        setCharacterViewBounds(xPos, yPos);
    }

    /**
     * Sets the CharacterView's Layout bounds to the size of the Character Model's
     * rectangular bounds.
     * 
     * @param xPos the current x-coordinate of this view
     * @param yPos the current y-coordinate of this view
     */
    private void setCharacterViewBounds(int xPos, int yPos) {
        this.setBounds(getPosX(), getPosY(), WIDTH, HEIGHT);

        if (icon != null) {
            icon.update(xPos, yPos - 40);
        }
    }

    public void setCurrentFrame() {
        if (++countUp >= DELAY) {
            currentFrame = (currentFrame + 1) % animationFrames.size();
        }

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

    public void resetCharacterFrame() {
        this.currentFrame = 0;
    }

    public void setDelay(int delay){
        this.DELAY = delay;
    }
}