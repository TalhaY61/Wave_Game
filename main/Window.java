package com.tutorial.main;


import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    private static final long serialVersionUID = -24084060053372834L;

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);  //The Frame of our Window

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //X Button will work
        frame.setResizable(false);                          // Resizable window
        frame.setLocationRelativeTo(null);                 //Macht den Window in der Mitte des Bildschirm
        frame.add(game);             //Adding Game class to our frame
        frame.setVisible(true);      //To see the frame
        game.start();                //Start it

    }


}

