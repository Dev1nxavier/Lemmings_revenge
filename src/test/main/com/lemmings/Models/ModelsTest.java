package src.test.main.com.lemmings.Models;
import src.main.com.lemmings.Models.*;
import src.main.com.lemmings.Views.CharacterView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JFrame;

public class ModelsTest {
    JFrame window;

    @BeforeEach
    public void createJFrame(){
        window = new JFrame("Test Window");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
    }

    @Test
    public void getModelWidth(){
        // get character view
        CharacterView lemming = new CharacterView(20, 20);
        assertEquals(20, lemming.getHEIGHT());

        Ground ground = new Ground(20, 20, 125, 75);
        assertEquals(125, ground.getWidth());
        
      
    }
}
