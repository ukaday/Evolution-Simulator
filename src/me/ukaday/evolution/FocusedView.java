package me.ukaday.evolution;

import java.awt.Color;

import static me.ukaday.evolution.Level.CREATURES;
import static me.ukaday.evolution.Level.FOODS;
import static me.ukaday.evolution.Settings.FOCUSED_VIEW_ZOOM_FACTOR;

public class FocusedView extends View{

    private final Entity camera = new CreatureCamera();
    public volatile double xOffset;
    public volatile double yOffset;

    public FocusedView(InputListener inputListener) {
        super(new Color(50, 50, 70), inputListener);
    }

    public double getXOffset() {
        xOffset = camera.getX() - this.getWidth() / 2.0 / FOCUSED_VIEW_ZOOM_FACTOR;
        return xOffset;
    }

    public double getYOffset() {
        yOffset = camera.getY() - this.getHeight() / 2.0 / FOCUSED_VIEW_ZOOM_FACTOR;
        return yOffset;
    }

    public void draw() {
        camera.update();

        FOODS.paint(gBack, getXOffset(), getYOffset(), FOCUSED_VIEW_ZOOM_FACTOR);
        CREATURES.paint(gBack, getXOffset(), getYOffset(), FOCUSED_VIEW_ZOOM_FACTOR);
    }
}
