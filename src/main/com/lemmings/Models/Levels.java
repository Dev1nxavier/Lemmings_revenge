package src.main.com.lemmings.Models;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Sean Greene
 * @date December 06, 2023
 * 
 * 
 */

public class Levels implements Serializable{
    private int[][] map;
    private int winCondition;
    private final long serializedUID = 1L; // for identifying serialized level
    private final String id;
    private final int level;

    public Levels(int level, int[][]map, int winCondition){
        this.id = UUID.randomUUID().toString();
        this.level = level;
        this.map = map;
        this.winCondition = winCondition;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public long getSerializedUID() {
        return serializedUID;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    
}
