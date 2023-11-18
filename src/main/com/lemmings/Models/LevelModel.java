package src.main.com.lemmings.Models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import src.main.com.lemmings.Views.CharacterView;

/**
 * LevelModel.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 */
public class LevelModel {

    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int MAX_CHARS = 10;
    // FIXME: Use generateMap function after testing!
    int[][] map = {
            { 0, 0, 0, 0, 0, 0, 0, 0 }, // Row 0
            { 0, 0, 0, 0, 0, 0, 0, 0 }, // Row 1
            { 1, 1, 0, 0, 0, 0, 0, 0 }, // Row 2
            { 0, 0, 0, 0, 0, 0, 0, 0 }, // Row 3
            { 0, 2, 0, 0, 0, 0, 2, 0 }, // Row 4
            { 1, 1, 1, 1, 1, 0, 1, 1 }, // Row 5
            { 1, 1, 1, 1, 2, 0, 2, 1 }, // Row 6
            { 1, 1, 1, 1, 1, 1, 1, 1 } // Row 7
    };
    // private int[][] map = new int[8][8]; // Ground: 1, Air: 0, Obstacle: 2
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<CharacterView> characterViews = new ArrayList<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int score = 0;
    private long timer;

    public LevelModel() {

        loadLevel();

    }

    // FIXME: the sizes of the obstacles is incorrect
    // primes the map with obstacles
    private void loadLevel() {

        // // generateMap(); // random level generator
        // generateEnvironmentObjects();
        generateCharacters();
    }

    public void updateGameState(){
        gameObjects.clear();

    }

    public void setCharacterViews(CharacterView cView){
        characterViews.add(cView);
    }

    public ArrayList<CharacterView>getCharacterViews(){
        return this.characterViews;
    }

    public void generateCharacters() {
        for (int i = 0; i < MAX_CHARS; i++) {
            Character ch = new Lemming();
            // slightly offset each character
            ch.setPosition((i + 2) * 20, 100);
            characters.add(ch);
        }
    }

    // FIXME: dont hardcode! Calculate!
    public void createGameObjectsFromMap() {
        // add obstacles
        int px = 0; // start at top of screen
        int py = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                if (map[row][col] == 1) {
                    // add ground
                    gameObjects.add(new Ground(px, py, 75, 75, new Point(row, col)));
                    px += 75; // advance by width of object
                } else if (map[row][col] == 2) {
                    gameObjects.add(new Rock(px, py, 50, 75, new Point(row, col)));
                    px += 150;
                } else {
                    // blanks count!
                    px += 75;
                }
            }
            px = 0;
            py += 75;
        }
    }

    private void generateMap() {

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
        return this.map;
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

    /**
     * This method sets the integer value of the map array to 0.
     * The method sets the array value at row x, col y to 0. It then
     * returns a copy of the map.
     * 
     * @param xy the row and column values of the integer member to remove, stored
     *           as a Point object
     * @return a copy of the updated map array.
     */
    public int[][] removePointFromMap(Point xy) {
        this.getMap()[xy.x][xy.y] = 0;

        return getMap();
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