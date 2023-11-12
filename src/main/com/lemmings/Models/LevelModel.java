package src.main.com.lemmings.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import src.main.com.lemmings.Models.GameObject;

/**
 * LevelModel.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 */
public class LevelModel {
    // TODO: Make randomly generated levels
    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int MAX_CHARS = 10;
    private int[][] map = new int[8][8]; // Ground: 1, Air: 0, Obstacle: 2
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int score = 0;
    private long timer;

    public LevelModel() {

        createLevel();

    }

    // primes the map with obstacles
    private void createLevel() {
        generateMap(); // random level generator
        // add obstacles
        int px = 0; // start at top of screen
        int py = 0;
        for (int[] row : map) {
            for (int obs : row) {
                if (obs == 1) {
                    // add ground
                    gameObjects.add(new GameObject(px, py));
                    //TODO: dont hardcode!
                    
                }
                px+=125;
            }
            px = 0;
            py+=125;
        }


        for (int i = 0; i < MAX_CHARS; i++) {
            Character ch = new Lemming();
            // slightly offset each character
            ch.setPosition(i * 20, 100);
            characters.add(ch);
        }

    }

    private void generateMap() {
        // TODO: update

        // last three rows are ground
        int isGround = map.length - 3;
        // randomly pick between air and obstacle
        Random rand = new Random();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                if (row >= isGround) {
                    map[row][col] = 1;
                } else {
                    if (rand.nextInt(2) == 1) {
                        map[row][col] = 0;
                    } else {
                        map[row][col] = 2;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : map) {
            for (int env : row) {
                sb.append("" + env + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int[][] getMap() {
        // return a copy of the level map
        return Arrays.copyOf(map, map.length);
    }

    public ArrayList<Character> getCharactersArray() {
        return this.characters;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getMAX_CHARS() {
        return MAX_CHARS;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(ArrayList<GameObject> obstacles) {
        this.gameObjects = obstacles;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    
}