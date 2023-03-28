package me.ukaday.evolution;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

//make highlight when hovering over creatures
//make mouse position update after it is put into map
// fix UI make both windows more visible
//make open and close function to window
//make a smooth camera movement from old focused creature to new focused creature

//breeding system should include stat balances (i.e. size increase, speed should decrease)

public class Evolution extends JFrame {
    public static final int WINDOW_W = 800;
    public static final int WINDOW_H = 600;

    public Evolution() {
        super("Evolution Simulator");
        new Level();

        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setFocusable(true);
        setVisible(true);

        ViewInitializer viewInitializer = new ViewInitializer(this);

        pack();
        viewInitializer.run();
    }


    public static void main( String args[] ) {
        new Evolution();
    }
}

