package me.ukaday.evolution;

import java.awt.*;

import static me.ukaday.evolution.Level.*;

public class MainView extends View {

    public MainView(InputListener inputListener) {
        super(new Color(50, 50, 70), inputListener);
    }

    public void draw() {
        FOODS.paint(gBack, 0, 0, 1);
        CREATURES.paint(gBack, 0, 0, 1);
    }
}
