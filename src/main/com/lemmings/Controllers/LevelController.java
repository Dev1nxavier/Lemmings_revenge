package src.main.com.lemmings.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// import java.util.Timer;
import java.awt.*;
import javax.swing.*;

import javax.swing.event.MouseInputListener;

import src.main.com.lemmings.Models.LevelModel;
import src.main.com.lemmings.Views.LevelView;

/**
 * LevelController.java
 * 
 * @author Sean Greene
 * @date November 10, 2023
 * 
 * This class controlls the levels of the game "Lemmings REVENGE!". The class
 * uupdates the game state, and includes
 * methods for registering mouseClick events and making updates to the environment and characters
 * 
 */
public class LevelController{
    private LevelModel lvl;
    private LevelView gameView;

    public LevelController(LevelView gameView){
        this.lvl = new LevelModel();
        this.gameView = gameView;

        // pass map to gameview
        gameView.setMap(lvl.getMap());

        layoutComponents();
        addListeners();
    }


    private void addListeners() {
        // refresh at frame rate
        Timer timer = new Timer(1600, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Update view
                gameView.setMap(lvl.getMap()); // pass level map each time
                gameView.updateView();
            }
            
        });

        timer.start();

        this.gameView.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent event){
                JOptionPane.showMessageDialog(null, "You Clicked the Mouse!");
            }
        });
    }
    private void layoutComponents() {
    }
    
}