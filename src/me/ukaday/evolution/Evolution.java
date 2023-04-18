package me.ukaday.evolution;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

//make highlight when hovering over creatures
//make mouse position update after it is put into map
// fix UI make both windows more visible
//make open and close function to window

public class Evolution extends JFrame {

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

