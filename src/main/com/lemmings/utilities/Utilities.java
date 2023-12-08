package src.main.com.lemmings.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import src.main.com.lemmings.Models.LevelModel;
/**
 * helpers.java
 * 
 * @author Sean Greene
 * @date November 11, 2023
 * 
 *       This class contains helper methods
 */
public class Utilities {
    private static final Set<String> clipPaths = new HashSet<>(); // prevent duplicate paths from playing concurrently

    public static BufferedImage getGameImages(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            System.err.println("Unable to laod image");
            return null;
        }
    }

    // plays a sound fx
    public static void playClip(String pathToClip) {

        // play on a separate thread from the EDT
        new Thread(new Runnable() {

            @Override
            public void run() {
                // if we already have the path in the Set, dont play it
                if (!clipPaths.add(pathToClip)) {
                    return;
                }

                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(pathToClip));

                    if (clip == null || !clip.isRunning()) {
                        clip.open(audioStream);
                        clip.start();

                        // close clip when audio completes
                        clip.addLineListener(new LineListener() {

                            @Override
                            public void update(LineEvent event) {
                                if (event.getType() == LineEvent.Type.STOP) {
                                    clip.close();
                                    try {
                                        audioStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        clipPaths.remove(pathToClip); //remove from Set
                                    }
                                }
                            }

                        });
                    }
                } catch (Exception e) {
                    System.err.println("Unable to load sound: " + pathToClip);
                    e.printStackTrace();
                }
            }

        }).start();
        ;
    }

    public static void saveLevel(LevelModel[] levels, String filename){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(levels);
            objectOutputStream.close(); // flush and close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LevelModel[] loadLevels(String filename){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filename)));
            return (LevelModel[]) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createLevels() {
        // save level instances
        LevelModel[] levels = new LevelModel[5];

        levels[0] = new LevelModel(new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 2, 0, 0 },
                { 1, 1, 1, 0, 0, 1, 1, 5 },
                { 1, 1, 1, 5, 5, 5, 5, 1 },
                { 2, 4, 0, 0, 0, 0, 1, 1 },
                { 1, 1, 1, 0, 0, 3, 0, 1 },
                { 1, 1, 0, 3, 0, 1, 1, 1 },
                { 1, 1, 0, 0, 0, 1, 1, 1 }
        }, 1);

        levels[1] = new LevelModel(new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 2, 0, 0 },
                { 1, 1, 0, 0, 0, 1, 1, 5 },
                { 1, 1, 1, 1, 2, 0, 0, 1 },
                { 1, 0, 0, 0, 0, 0, 1, 0 },
                { 1, 1, 1, 0, 1, 3, 0, 0 },
                { 1, 1, 0, 3, 0, 4, 0, 0 },
                { 1, 1, 0, 0, 0, 1, 1, 1 }
        }, 2);

        levels[2] = new LevelModel(new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 2, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 5 },
                { 1, 1, 1, 1, 1, 0, 0, 1 },
                { 2, 4, 0, 0, 1, 0, 1, 1 },
                { 1, 1, 1, 0, 0, 3, 0, 1 },
                { 1, 1, 0, 3, 0, 1, 1, 1 },
                { 1, 1, 0, 0, 0, 1, 1, 1 }
        }, 3);

        levels[3] = new LevelModel(new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 2, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 5 },
                { 1, 1, 1, 0, 1, 0, 0, 0 },
                { 2, 0, 0, 0, 0, 0, 0, 1 },
                { 1, 1, 1, 0, 0, 3, 0, 0 },
                { 1, 1, 0, 3, 0, 4, 0, 0 },
                { 1, 1, 0, 0, 0, 1, 1, 1 }
        }, 4);

        levels[4] = new LevelModel(new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 2, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 5 },
                { 1, 1, 1, 1, 1, 0, 0, 1 },
                { 2, 4, 0, 0, 1, 0, 1, 1 },
                { 1, 1, 1, 0, 0, 3, 0, 1 },
                { 1, 1, 0, 3, 0, 1, 1, 1 },
                { 1, 1, 0, 0, 0, 1, 1, 1 }
        }, 5);

        Utilities.saveLevel(levels, "src/main/levels.dat");
    };

}