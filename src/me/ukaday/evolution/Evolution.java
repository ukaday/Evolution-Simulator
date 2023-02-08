package me.ukaday.evolution;

import javax.swing.*;
import java.awt.*;

public class Evolution extends JFrame{
    public static final int WINDOW_W = 800;
    public static final int WINDOW_H = 600;

    public Evolution()
    {
        super("me.ukaday.evolution.Evolution Simulator");

        Level panel = new Level();
        panel.setPreferredSize(new Dimension(WINDOW_W , WINDOW_H));
        getContentPane().add(panel);
        this.pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setFocusable(true);
        setVisible(true);

        panel.run();
    }

    public static void main( String args[] ) {
        Evolution run = new Evolution();
    }
}

//
