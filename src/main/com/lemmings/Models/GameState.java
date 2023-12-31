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
    private int charactersThroughPortal = 0;
    private int charactersDead = 0;

    public GameState(){
        this.score = 0;
        this.lives = 5;
        this.level = 1;
        this.chCount = 10;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        this.score = points;
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

    public int updateCharacterCount(int i) {
        return this.chCount -=i;
    }

    public void updateCharactersThroughPortal(int count){
        this.charactersThroughPortal +=count;
    }

    public int getCharactersThroughPortal(){
        return this.charactersThroughPortal;
    }

    public void updateCharactersDead(int count){
        this.charactersDead+=count;
    }
    
    public int getCharactersDead(){
        return this.charactersDead;
    }

    public void updateLevel(int i) {
        this.level = i;
    }
    
}