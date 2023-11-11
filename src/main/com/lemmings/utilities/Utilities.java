package src.main.com.lemmings.utilities;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * helpers.java
 * 
 * @author Sean Greene
 * @date November 11, 2023
 * 
 * This class contains helper methods
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
    
}