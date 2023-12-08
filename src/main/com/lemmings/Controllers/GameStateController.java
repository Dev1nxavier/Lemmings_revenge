package src.main.com.lemmings.Controllers;

import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Views.StatsPanelView;

/**
 * GameStateController
 */
public class GameStateController {

    private GameState gameState;
    private StatsPanelView panel;

    public GameStateController(StatsPanelView panel, GameState gameState){
        this.panel = panel;
        this.gameState = gameState;
        updateScore(0);
        updateCharacterCount(0);
        updateLevel(1);
    }

    private void updateLevel(int i) {
        gameState.updateLevel(i);
        panel.updateLevelDisplay(gameState.getLevel());
    }

    public void updateScore(int points){
        gameState.updateScore(points);
        panel.updateScoreDisplay(gameState.getScore());
    }

    public void updateCharacterCount(int i){
        gameState.updateCharacterCount(i);
        panel.updateCharacterCountDisplay(gameState.getChCount());
    }

    public int getCharacterCount() {
        return gameState.getChCount();
    }

    public int getScore() {
        return gameState.getScore();
    }

    public void updateCharactersDead(int count){
        gameState.updateCharactersDead(count);
        updateCharacterCount(count);
    }

    public void updateCharactersThroughPortal(int count){
        System.out.println("GameStateCtrl.updateCharacterPortal" + count);
        gameState.updateCharactersThroughPortal(count);
        updateCharacterCount(count);
    }
}