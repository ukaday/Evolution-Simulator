package me.ukaday.evolution;

import java.awt.*;

import static me.ukaday.evolution.Direction.*;
import static me.ukaday.evolution.Evolution.WINDOW_H;
import static me.ukaday.evolution.Evolution.WINDOW_W;
import static me.ukaday.evolution.Level.DELTA_TIME;

public class Ball {

    private double x, y;
    private final int r;
    private double speed;
    private double theta = 0;
    private static final double maxSpeed = .4;
    private static final double stepTheta = .006;
    private static final double lineLen = 50;

    public Ball(double x, double y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void turn(Direction d) {
        if (d == LEFT)  { theta -= stepTheta * DELTA_TIME; }
        if (d == RIGHT) { theta += stepTheta * DELTA_TIME; }
    }

    public void move(Direction d) {
        speed = 0;
        if (d == FORWARD)   { speed += maxSpeed;}
        if (d == BACKWARD) { speed -= maxSpeed;}

        double stepX = Math.cos(theta) * speed;
        double stepY = Math.sin(theta) * speed;
        double nextX = x + stepX * DELTA_TIME;
        double nextY = y + stepY * DELTA_TIME;

        if (nextX <= r || r + nextX >= WINDOW_W) { nextX = x; }
        if (nextY <= r || r + nextY >= WINDOW_H) { nextY = y; }

        x = nextX;
        y = nextY;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(200, 200, 240));
        g.fillOval((int)x - r, (int)y - r, 2 * r, 2 * r);

        g.setColor(new Color(220, 0, 0));
        g.drawLine((int)x, (int)y, (int)(x + lineLen * Math.cos(theta)), (int)(y + lineLen * Math.sin(theta)));
    }
}
