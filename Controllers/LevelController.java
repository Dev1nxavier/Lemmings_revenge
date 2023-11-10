package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
// import java.util.Timer;
import java.awt.*;
import javax.swing.*;

import javax.swing.event.MouseInputListener;

import Models.LevelModel;
import Views.LevelView;

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
    LevelController(LevelModel lvl, LevelView gamView){
        this.lvl = lvl;
        this.gameView = gameView;

        layoutComponents();
        addListeners();
    }


    private void addListeners() {
        // refresh at frame rate
        Timer timer = new Timer(1600, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Update view
                gameView.updateView();
            }
            
        });

        timer.start();

        this.gameView.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
            }
            
        });
    }
    private void layoutComponents() {
    }
    
}