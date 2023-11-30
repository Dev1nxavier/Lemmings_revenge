package src.main.com.lemmings.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageLoader.java
 * 
 * @author Sean Greene
 * @date November 11, 2023
 * 
 * This class preloads game images and stores them in a static Map object
 */
public class ImageLoader {
    
    private static final Map<String, BufferedImage> GAME_IMAGES = new HashMap<>();

    static {
        //retrieve path for each resource
        try {
            File resourcesFile = new File("src/main/resources");
            if (!resourcesFile.exists()) {
                System.err.println("Unable to load file");
                System.exit(1);
            }
            loadImage(resourcesFile);
        } catch (Exception e) {
            System.err.println("Unable to load file" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadImage(File resourcesFile) {
        // resource file is flat
        File [] subFiles = resourcesFile.listFiles();
                // sort files first
        Arrays.sort(subFiles);
        for (File subFile : subFiles) {
            GAME_IMAGES.put( ""+subFile.getName(),Utilities.getGameImages(subFile.getPath()));
        }
    }

    public static BufferedImage getImage(String imageName){
        BufferedImage image = GAME_IMAGES.get(imageName);
        if (image ==null) {
            System.err.println("Image not found: " + imageName);
            return null;
        }
        return image;
    }
}