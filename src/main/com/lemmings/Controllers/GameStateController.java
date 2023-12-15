package src.main.com.lemmings.Controllers;

import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Views.StatsPanelView;

/**
 * GameStateController.java
 * 
 * @author Sean Greene
 * @date November 23, 2023
 * 
 * Controls and synchronizes game state between the GameState and the StatsPanelView
 * This controller updates the game state and reflects changes on the StatsPanelView
 */
public class GameStateController {

    private GameState gameState;
    private StatsPanelView panel;

    /**
     * Constructs a GameStateController with specified StatsPanelView and GameState.
     * Initializes the game with default values
     * 
     * @param panel the StatsPanelView to display game state information
     * @param gameState the GameState model holding the current state
     */
    public GameStateController(StatsPanelView panel, GameState gameState){
        this.panel = panel;
        this.gameState = gameState;
        updateScore(0);
        updateCharacterCount(0);
        updateLevel(1);
    }

    /**
     * Updates the game level both in model and view
     * @param level the next level to be set in the model
     */
    private void updateLevel(int level) {
        gameState.updateLevel(level);
        panel.updateLevelDisplay(gameState.getLevel());
    }

    /**
     * Updates the score in the model and view
     * @param points the additional points to be added to the current score
     */
    public void updateScore(int points){
        gameState.updateScore(points);
        panel.updateScoreDisplay(gameState.getScore());
    }

    /**
     * Updates the character count in the model and view
     * 
     * @param count the current count of remaining characters on screen
     */
    public void updateCharacterCount(int count){
        gameState.updateCharacterCount(count);
        panel.updateCharacterCountDisplay(gameState.getChCount());
    }

    /**
     * Retrieves the current character count from the game state
     * @return the current character count
     */
    public int getCharacterCount() {
        return gameState.getChCount();
    }

    /**
     * Retrieves the current score from the game state
     * @return the current score
     */
    public int getScore() {
        return gameState.getScore();
    }

    /**
     * Updates the count of dead characters in the model and view
     * @param count the count of dead characters
     */
    public void updateCharactersDead(int count){
        gameState.updateCharactersDead(count);
        updateCharacterCount(count);
    }

    /**
     * Updates the count of characters that have passed through the level's portal
     * 
     * @param count the count of characters through the portal
     */
    public void updateCharactersThroughPortal(int count){
        gameState.updateCharactersThroughPortal(count);
        updateCharacterCount(count);
    }
}