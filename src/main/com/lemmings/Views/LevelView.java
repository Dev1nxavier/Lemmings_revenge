package src.main.com.lemmings.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import src.main.com.lemmings.Controllers.CharacterController;
import src.main.com.lemmings.Models.GameObjects.GameObject;
import src.main.com.lemmings.Views.Components.BackgroundPanel;
import src.main.com.lemmings.utilities.ImageLoader;

/**
 * LevelView
 */
public class LevelView extends JPanel {
    private JLayeredPane gamePlayPane; // for rendering characters and game objects
    private JPanel rootPanel; // for layout of gamepane and menus. 
    private BackgroundPanel backgroundPanel; // renders backgroundImage only
    private StatsPanelView statsView; // for displaying the current score, level, lives
    private MenuOptionsView menuView; // for providing the moves available on the level

    public LevelView() {
        this.setDoubleBuffered(true); // explicitly enable double buffering
        layoutComponents();
    }

    private void layoutComponents() {
        this.setLayout(new OverlayLayout(this));

        gamePlayPane = new JLayeredPane(){
            @Override
            public Dimension getPreferredSize(){
                return new Dimension(600, 600);
            }
        };

        rootPanel = new JPanel(); //JPanel holds Game window, Stats Window, Options Menu
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
        rootPanel.add(gamePlayPane, BorderLayout.CENTER); //Game window takes up all space remaining after menus

        this.add(rootPanel);

        backgroundPanel = new BackgroundPanel(ImageLoader.getImage("cave_background.png"));
        this.add(backgroundPanel); // bottom of stack

    }

    /**
     * Adds JLabel objects to the JLayeredPane of the main view. 
     * 
     * This method adds GameObjects and CharacterViews to the LevelView by placing them directly on the LevelView's
     * JLayeredPane. A second argument passed to the method specifys the layer of the JLayeredPane to add the object to. 
     * 
     * @param objectView the JLabel object to be added to the LevelView's JLayeredPane
     * @param layer the integer value of the JLayeredPane's layer to add the object to. 
     * 
     */
    public void addObjectToView(JLabel objectView, int layer) {
        gamePlayPane.add(objectView, Integer.valueOf(layer));
        gamePlayPane.revalidate();
        gamePlayPane.repaint();
    }

    public StatsPanelView getStatsPanelView(){
        return this.statsView;
    }

    public MenuOptionsView getMenuOptionsView(){
        return this.menuView;
    }

    /**
     * This method removes all components from the LayeredPane container of the
     * LevelView. It then calls revalidate on the LevelView.
     */
    public void clearGameObjectsFromView() {
        this.gamePlayPane.removeAll();
    }

    public void redrawView(ArrayList<GameObject> gameObjects, ArrayList<CharacterController> characters) {
        clearGameObjectsFromView();
        for (GameObject go : gameObjects) {
            addObjectToView(go, JLayeredPane.DEFAULT_LAYER);
        }
        for (CharacterController ch : characters) {
            addObjectToView(ch.getCharacterView(), JLayeredPane.MODAL_LAYER);
        }
    }
}
