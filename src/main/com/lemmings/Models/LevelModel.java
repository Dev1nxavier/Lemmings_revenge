package src.main.com.lemmings.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * LevelModel.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 */
public class LevelModel {
    // TODO: Make randomly generated levels

    int[][] map = new int[8][8]; // Ground: 1, Air: 0, Obstacle: 2
    ArrayList<Character> characters = new ArrayList<>();
    int score = 0;
    long timer;

    public LevelModel() {

        generateMap();
        layoutComponents();

    }

    // primes the map with obstacles
    private void layoutComponents() {

    }

    private void generateMap() {
        // TODO: update

        // last three rows are ground
        int isGround = map.length - 3;
        // randomly pick between air and obstacle
        Random rand = new Random();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                if (row >=isGround) {
                    map[row][col] = 1;
                } else{
                    if (rand.nextInt(2) == 1) {
                        map[row][col] = 0;
                    }else{
                        map[row][col] = 2;
                    }
                }
            }
        }
        //TODO: Remove after testing
        System.out.printf(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : map) {
            for (int env : row) {
                sb.append(""+ env+" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int[][] getMap(){
        // return a copy of the level map
        return Arrays.copyOf(map, map.length);
    }

}