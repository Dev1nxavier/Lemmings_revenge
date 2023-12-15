package src.main.com.lemmings.Views;

import src.main.com.lemmings.utilities.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.main.com.lemmings.Models.Character;

/**
 * CharacterView.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       This class is responsible for the character view. It manages the
 *       character's animations, displays skill icons,
 *       and highlights the character on mouseover. It synchronizes the visual
 *       representation with the CharacterModel's state.
 */

public class CharacterView extends GameView {
    private transient ArrayList<BufferedImage> animationFrames;
    private int currentFrame;
    private int imageOffset; // adjust image position in JLabel
    private int characterHeight, characterWidth; // the height of the character image which differs from the viewbounds
    private transient BufferedImage skillIcon;
    private transient BufferedImage arrowIcon;
    private boolean isHighlighted = false;
    private Character character; // a reference to the Character model this view responds to

    /**
     * Default constructor for CharacterView.
     * Initializes the character with default position, animation frames, and arrow
     * icon.
     */
    public CharacterView() {
        super(20, 40, 100, 200); // initial Character position (100,200)
        currentFrame = 0;
        imageOffset = 20;
        this.characterHeight = 20;
        this.characterWidth = 10;
        initializeAnimationFrames();
        setArrowIcon();
    }

    /**
     * Constructs a CharacterView associated with a given Character model.
     * 
     * @param character The Character model that this view represents.
     */
    public CharacterView(Character character) {
        this();
        this.character = character;
        this.setPosX(this.character.getX_pos());
        this.setPosY(this.character.getY_pos());
    }

    /**
     * sets the arrowicon for this character view.
     */
    private void setArrowIcon() {
        try {
            this.arrowIcon = ImageLoader.getImage("arrow_01.png");
        } catch (Exception e) {
            System.err.println("unable to load arrow image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * loads the animation frames for the characterview to an array
     */
    private void initializeAnimationFrames() {
        animationFrames = new ArrayList<>();
        this.animationFrames.add(ImageLoader.getImage("Lemming_pose-two.png"));
        this.animationFrames.add(ImageLoader.getImage("Lemming_pose-three.png"));
        this.animationFrames.add(ImageLoader.getImage("Lemming_pose-0.png"));
    }

    /**
     * Getter for the animationframes array
     * 
     * @return an array containing the animation images
     */
    public ArrayList<BufferedImage> getAnimationFrames() {
        return this.animationFrames;
    }

    /**
     * Setter for the animationframes array.
     * 
     * @param animationFrames an Arraylist of animation images
     */
    public void setAnimationFrames(ArrayList<BufferedImage> animationFrames) {
        this.animationFrames = animationFrames;
    }

    /**
     * Updates the character's position and animation frame.
     * 
     * @param xPos The updated x-coordinate of the character.
     * @param yPos The updated y-coordinate of the character.
     */
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
        this.setBounds(getPosX(), getPosY() - imageOffset, this.getWidth(), this.getHeight() + imageOffset);
    }

    public void setCurrentFrame() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
    }

    public void showArrow() {
        this.isHighlighted = true;
    }

    public void hideArrow() {
        this.isHighlighted = false;
    }

    public void setSkillIcon(BufferedImage icon) {

        this.skillIcon = icon;
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
                g2d.drawImage(animationFrames.get(currentFrame), 5, imageOffset, this.characterWidth,
                        this.characterHeight, null); // draw it relative to this

                if (skillIcon != null) {
                    g2d.drawImage(skillIcon, 0, 0, this.getWidth(), imageOffset, null);
                }

                if (isHighlighted) {
                    g2d.drawImage(arrowIcon, 0, 0, this.getWidth(), imageOffset, null);
                }

            } else {
                System.err.println("Unable to load character images");
            }
        } catch (Exception e) {
            System.out.println("Unable to load characters: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetCharacterFrame() {
        this.currentFrame = 0;
    }
}