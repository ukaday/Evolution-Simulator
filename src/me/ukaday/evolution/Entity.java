package me.ukaday.evolution;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;

import static me.ukaday.evolution.Stat.RADIUS;

public abstract class Entity {

    protected volatile double x, y;

    public Entity(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getEntityDistance(Entity target) {
        double x2 = Math.pow(target.getX() - getX(), 2);
        double y2 = Math.pow(target.getY() - getY(), 2);
        return Math.sqrt(x2 + y2);
    }

    public boolean isColliding(Entity target) {
        return false;
    }

    public void update() {
    }

    public abstract void paint(Graphics g, double xOffSet, double yOffSet, double zoom);

    @Override
    public String toString()
    {
        return x + " " + y;
    }
}
