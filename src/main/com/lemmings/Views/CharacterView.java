package src.main.com.lemmings.Views;

import src.main.com.lemmings.utilities.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;

public class CharacterView extends GameView {
    private ArrayList<BufferedImage> animationFrames;
    private int currentFrame;
    private int imageOffset; // adjust image position in JLabel
    private BufferedImage skillIcon;
    private BufferedImage arrowIcon;
    private boolean isHighlighted = false;

    public CharacterView(int x, int y) {
        super(10, 20, x, y);
        currentFrame = 0;
        imageOffset = 20;
        initializeAnimationFrames();
        setArrowIcon();
    }

    private void setArrowIcon() {
        try {
            this.arrowIcon = ImageLoader.getImage("arrow_01.png");
        } catch (Exception e) {
            System.err.println("unable to load arrow image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeAnimationFrames() {
        animationFrames = new ArrayList<>();
        this.animationFrames.add(ImageLoader.getImage("Lemming_pose-two.png"));
        this.animationFrames.add(ImageLoader.getImage("Lemming_pose-three.png"));
        this.animationFrames.add(ImageLoader.getImage("Lemming_pose-0.png"));
    }

    public ArrayList<BufferedImage> getAnimationFrames() {
        return this.animationFrames;
    }

    public void setAnimationFrames(ArrayList<BufferedImage> animationFrames) {
        this.animationFrames = animationFrames;
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
        this.setBounds(getPosX(), getPosY()-imageOffset, WIDTH, HEIGHT+imageOffset);
    }

    public void setCurrentFrame() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
    }

    public void showArrow(){
        this.isHighlighted = true;
    }

    public void hideArrow(){
        this.isHighlighted = false;
    }

    public void setSkillIcon(String name) {
        BufferedImage icon;
        try {
            icon = ImageLoader.getImage(name);
            this.skillIcon = icon;
        } catch (Exception e) {
            System.err.println("Unable to load icon: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    public BufferedImage getSkillIcon() {
        return this.skillIcon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g;

        try {
            if (animationFrames.get(currentFrame) != null) {
                g2d.drawImage(animationFrames.get(currentFrame), 0, imageOffset, WIDTH, HEIGHT, null); // draw it relative to this

                if (skillIcon !=null) {
                    g2d.drawImage(skillIcon, 0, 0, WIDTH, imageOffset, null);
                }

                if (isHighlighted) {
                    g2d.drawImage(arrowIcon, 0, 0, WIDTH, imageOffset, null);
                }

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
}