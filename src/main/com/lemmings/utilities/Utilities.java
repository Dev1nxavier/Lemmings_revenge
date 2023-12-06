package src.main.com.lemmings.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
 * helpers.java
 * 
 * @author Sean Greene
 * @date November 11, 2023
 * 
 *       This class contains helper methods
 */
public class Utilities {

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

        }).start();;
    }

}