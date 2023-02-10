package me.ukaday.evolution;

import java.awt.*;

public class Food extends Entity{

    private double x, y;
    private double energy;
    private double s = 8;

    public Food(double x, double y, int energy) {
        this.x = x;
        this.y = y;
        this.energy = energy;

    }

    public double getEnergy() {
        return energy;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(130, 80, 130));
        g.fillRect((int)(x - s/2), (int)(y - s/2), (int)s, (int)s);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
