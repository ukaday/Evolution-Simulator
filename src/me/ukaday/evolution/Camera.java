package me.ukaday.evolution;

import java.awt.*;

import static me.ukaday.evolution.Level.*;
import static me.ukaday.evolution.Settings.*;
import static me.ukaday.evolution.Settings.FOCUS_WINDOW_H;

public class Camera extends Entity {

    private Entity focus;
    private boolean transitioning = false;
    public volatile double xOffset;
    public volatile double yOffset;
    private final double[] v = new double[]{0, 0};
    private final double[] d = new double[]{0, 0};

    public Camera(Entity focus) {
        super(focus.getX(), focus.getY());
        this.focus = focus;
    }

    public void setFocus(Entity focus) {
        this.focus = focus;
        transitioning = true;
    }

    protected void move() {
        if (!transitioning) {
            setX(focus.getX());
            setY(focus.getY());
        } else {
            d[0] = focus.getX() - getX();
            d[1] = focus.getY() - getY();
            normalize(d);

            double dis = getEntityDistance(focus);
            v[0] = d[0] * getSpeed(dis) * DELTA_TIME;
            v[1] = d[1] * getSpeed(dis) * DELTA_TIME;

            setX(getX() + v[0]);
            setY(getY() + v[1]);
            transitioning = !(dis < 1);
        }
    }

    public void update() {
        move();
    }

    private double getSpeed(double dis) {
        if (focus instanceof Creature) {
            return Math.max(CREATURE_CAMERA_SPEED_FACTOR * dis, ((Creature)focus).getSpeed() * 1.1);
        }
        return  Math.max(CREATURE_CAMERA_SPEED_FACTOR * dis, CREATURE_CAMERA_SPEED_FACTOR);
    }

    public Entity getFocus() {
        return focus;
    }

    public void paint(Graphics g, double xOffSet, double yOffSet, double zoom) {
        //invisible
    }
}
