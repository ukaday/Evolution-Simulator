package me.ukaday.evolution;

import java.awt.*;

public abstract class Entity {

    private double x, y;

    public abstract double getX();

    public abstract void setX(double x);

    public abstract double getY();

    public abstract void setY(double y);

    public abstract void paint(Graphics g);

    public abstract String toString();
}
