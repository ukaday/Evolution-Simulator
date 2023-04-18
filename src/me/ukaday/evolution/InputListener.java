package me.ukaday.evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class InputListener implements KeyListener, MouseListener {

    public static final Map<Integer, Boolean> KEY_STATES = new HashMap<>();
    public static final Map<Integer, Point> MOUSE_STATES = new HashMap<>();
    private final JFrame frame;

    public InputListener(JFrame frame) {
        this.frame = frame;
    }

    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        KEY_STATES.put(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        KEY_STATES.put(e.getKeyCode(), false);
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        MOUSE_STATES.put(e.getButton(), frame.getMousePosition());
    }

    public void mouseReleased(MouseEvent e) {
        MOUSE_STATES.put(e.getButton(), null);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
