package me.ukaday.evolution;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static me.ukaday.evolution.Evolution.*;

public class ViewInitializer{

    public Collection<View> views = new ArrayList<>();
    public InputListener inputListener;

    public ViewInitializer(JFrame frame) {
        inputListener = new InputListener(frame);
        MainView mainView = new MainView(inputListener);
        FocusedView focusedView = new FocusedView(inputListener);

        mainView.setPreferredSize(new Dimension(WINDOW_W, WINDOW_H));
        focusedView.setPreferredSize(new Dimension (500, 600));
        views.add(mainView);
        views.add(focusedView);
        frame.add(focusedView, BorderLayout.EAST);
        frame.add(mainView, BorderLayout.WEST);

    }

    public void run() {
        while (true) {
            for (View view : views) {
                view.repaint();
            }
        }
    }
}
