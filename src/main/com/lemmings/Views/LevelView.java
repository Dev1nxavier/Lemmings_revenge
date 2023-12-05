package src.main.com.lemmings.Views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import src.main.com.lemmings.Controllers.CharacterController;
import src.main.com.lemmings.Models.Character;
import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Views.Components.BackgroundPanel;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * LevelView
 */
public class LevelView extends JPanel {
    private WinLoseScreen winScreen;
    private WinLoseScreen loseScreen;
    private JLayeredPane gamePlayPane; // for rendering characters and game objects
    private JPanel rootPanel; // for layout of gamePlayPane and menus.
    private BackgroundPanel backgroundPanel; // renders backgroundImage only
    private StatsPanelView statsView; // for displaying the current score, level, lives
    private MenuOptionsView menuView; // for providing the moves available on the level
    private ArrayList<CharacterView> characterViews = new ArrayList<>();

    public LevelView() {
        this.setDoubleBuffered(true); // explicitly enable double buffering
        layoutComponents();
    }

    /**
     * This method calls a method to clear all elements from LevelView before
     * itterating over an ArrayList of gameObjects and CharacterViews and adding
     * each to the LevelView's JLayeredPane.
     * 
     * @param gameObjects
     */
    public void initializeWithLevelModel(LevelModel levelModel) {
        clearGameObjectsFromView();
        for (GameObject go : levelModel.getGameObjects()) {
            addObjectToView(go, JLayeredPane.DEFAULT_LAYER);
        }

        for (CharacterView characterView : characterViews) {
            addObjectToView(characterView, JLayeredPane.MODAL_LAYER);
        }
    }

    /**
     * Initializes character views and their controllers for each character.
     * 
     * @param characters an ArrayList of {@code Character} objects to be initialized
     * @param listener   a {@code GameObjectChangeListener} passed to each
     *                   CharacterController
     * @return an ArrayList of initialized CharacterController objects.
     */
    public ArrayList<CharacterController> initializeCharacterViews(ArrayList<Character> characters,
            GameObjectChangeListener listener) {
        ArrayList<CharacterController> controllers = new ArrayList<>();
        // create CharacterViews for each Character
        for (Character character : characters) {
            CharacterView characterView = new CharacterView(character);
            CharacterController characterController = new CharacterController(character, characterView, listener);
            characterViews.add(characterView);
            controllers.add(characterController);
        }
        return controllers;
    }

    private void layoutComponents() {
        this.setLayout(new OverlayLayout(this));

        gamePlayPane = new JLayeredPane() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 600);
            }
        };

        rootPanel = new JPanel(); // JPanel holds Game window, Stats Window, Options Menu
        rootPanel.setOpaque(false); // transparent to show background
        rootPanel.setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // center statsView in panel
        statsView = new StatsPanelView();
        statsPanel.add(statsView);

        rootPanel.add(statsPanel, BorderLayout.NORTH);
        menuView = new MenuOptionsView();
        rootPanel.add(menuView, BorderLayout.EAST);
        rootPanel.add(gamePlayPane, BorderLayout.CENTER); // Game window takes up all space remaining after menus

        winScreen = new WinLoseScreen(true);
        loseScreen = new WinLoseScreen(false);
        this.add(winScreen); // added to top of stack
        this.add(loseScreen); // added to top of stack
        this.add(rootPanel);

        backgroundPanel = new BackgroundPanel(ImageLoader.getImage("cave_background.png"));
        this.add(backgroundPanel); // bottom of stack

    }

    /**
     * Adds JLabel objects to the JLayeredPane of the main view.
     * 
     * This method adds GameObjects and CharacterViews to the LevelView by placing
     * them directly on the LevelView's
     * JLayeredPane. A second argument passed to the method specifys the layer of
     * the JLayeredPane to add the object to.
     * 
     * @param objectView the JLabel object to be added to the LevelView's
     *                   JLayeredPane
     * @param layer      the integer value of the JLayeredPane's layer to add the
     *                   object to.
     * 
     */
    public void addObjectToView(JLabel objectView, int layer) {
        gamePlayPane.add(objectView, Integer.valueOf(layer));
        gamePlayPane.revalidate();
        gamePlayPane.repaint();
    }

    public void removeObjectFromView(JLabel ObjectView, int zIndex) {
        this.characterViews.remove(ObjectView); // remove from list
        // get components in gamePlayPane
        Component[] components = gamePlayPane.getComponents();
        for (Component component : components) {
            if (component.equals(ObjectView)) {
                gamePlayPane.remove(component);
                gamePlayPane.repaint();
            }
        }
    }

    public StatsPanelView getStatsPanelView() {
        return this.statsView;
    }

    public MenuOptionsView getMenuOptionsView() {
        return this.menuView;
    }

    public WinLoseScreen getWinScreen() {
        return this.winScreen;
    }

    public WinLoseScreen getLoseScreen() {
        return this.loseScreen;
    }

    /**
     * This method removes all components from the LayeredPane container of the
     * LevelView. It then calls revalidate on the LevelView.
     */
    public void clearGameObjectsFromView() {
        this.gamePlayPane.removeAll();
    }

    public Rectangle getGamePanelBounds() {
        return gamePlayPane.getBounds();
    }

    /**
     * WinScreen.java
     * 
     * Inner class that defines the win screen. Instances of this class are made
     * visible when the
     * Level's win conditions are met.
     */
    public class WinLoseScreen extends JPanel {
        BufferedImage image;
        boolean isWinScreen;

        WinLoseScreen(boolean isWinScreen) {
            this.isWinScreen = isWinScreen;
            loadImage();
            layoutComponents();
        }

        private void loadImage() {
            String fileName = "";
            try {
                fileName = isWinScreen ? "winScreen.png" : "loseScreen.png";
                image = ImageLoader.getImage(fileName);
            } catch (Exception e) {
                System.err.println("Unable to load file: " + fileName);
                e.printStackTrace();
            }
        }

        protected void layoutComponents() {
            this.setVisible(false);
        }

        public void setIsVisible(boolean isVisible) {
            this.setVisible(isVisible);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }

    }
}
