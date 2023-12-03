package src.main.com.lemmings.Controllers;

import src.main.com.lemmings.Models.GameObjectChangeListener;
import src.main.com.lemmings.Models.GameState;
import src.main.com.lemmings.Views.StatsPanelView;

/**
 * GameStateController
 */
public class GameStateController {

    private GameState gameState;
    private StatsPanelView panel;
    private GameObjectChangeListener listener;

    public GameStateController(StatsPanelView panel, GameState gameState){
        this.panel = panel;
        this.gameState = gameState;
        updateScore(0);
        updateCharacterCount();
    }

    public void setGameObjectChangeListener(GameObjectChangeListener listener){
        this.listener = listener;
    }

    public void updateScore(int points){
        gameState.updateScore(points);
        panel.updateScoreDisplay(gameState.getScore());
    }

    public void updateCharacterCount(){
        gameState.updateCharacterCount();
        panel.updateCharacterCountDisplay(gameState.getChCount());
    }
}