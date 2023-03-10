package me.ukaday.evolution;

import java.awt.*;

public class Food extends Entity{

    private final double energy;
    private static final double s = 8;

    public Food(double x, double y, int energy) {
        super(x, y);
        this.energy = energy;
    }

    public boolean isColliding(Entity target) {
        return false;
    }

    public double getEnergy() {
        return energy;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(130, 80, 130));
        g.fillRect((int)(getX() - s/2), (int)(getY() - s/2), (int)s, (int)s);
    }
}
