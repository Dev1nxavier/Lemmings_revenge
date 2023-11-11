package src.main.com.lemmings.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.main.com.lemmings.Models.Lemming;
import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Views.LevelView;

/**
 * LevelController.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 *       This class controlls the levels of the game "Lemmings REVENGE!". The
 *       class
 *       uupdates the game state, and includes
 *       methods for registering mouseClick events and making updates to the
 *       environment and characters
 * 
 */
public class LevelController {
    private LevelModel lvl;
    private LevelView gameView;
    private CharacterController cController;

    public LevelController(LevelView gameView) {
        this.lvl = new LevelModel();
        this.gameView = gameView;

        // pass map to gameview
        gameView.setMap(lvl.getMap());
        addListeners();
    }

    private void addListeners() {
        // FIXME: Adjust frame rate

        // refresh at frame rate
        Timer timer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO: Update view
                gameView.setMap(lvl.getMap()); // pass level map each time
                gameView.updateView();
                // update characters
                cController.update();
            }

        });

        timer.start();

        this.gameView.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent event) {
                JOptionPane.showMessageDialog(null, "You Clicked the Mouse!");
            }
        });
    }

    public void setCharacterController(CharacterController cController) {
        this.cController = cController;
    }

}