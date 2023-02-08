package me.ukaday.evolution;

import java.awt.*;

public class Food {

    private int x, y, s;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
        s = 8;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(130, 80, 130));
        g.fillRect(x, y, s, s);
    }
}
