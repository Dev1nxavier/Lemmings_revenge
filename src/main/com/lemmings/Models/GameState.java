package src.main.com.lemmings.Models;

/**
 * GameState.java
 * 
 * @author Sean Greene
 * @date November 27, 2023
 * 
 * This class contains information on gamestate including lives remaining, total score, elapsed time, Current Level
 */
public class GameState {
    private int score;
    private int lives;
    private int level;
    private int chCount;

    public GameState(){
        this.score = 0;
        this.lives = 5;
        this.level = 1;
        this.chCount = 11; // start with +1 extra. 
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public int getLives() {
        return lives;
    }

    public int updateLives() {
        return this.lives--;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getChCount() {
        return chCount;
    }

    public int updateCharacterCount() {
        return this.chCount--;
    }

    
    
}