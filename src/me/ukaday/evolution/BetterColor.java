package me.ukaday.evolution;

import java.awt.*;

public class BetterColor {

    private int r, g, b;

    public BetterColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void add(BetterColor color) {
        r = Math.min(r + color.getR(), 255);
        g = Math.min(g + color.getG(), 255);
        b = Math.min(b + color.getB(), 255);
    }

    public void add(Color color) {
        r = Math.min(r + color.getRed(), 255);
        g = Math.min(g + color.getGreen(), 255);
        b = Math.min(b + color.getBlue(), 255);
    }

    public void add(int value) {
        r = Math.min(r + value, 255);
        g = Math.min(g + value, 255);
        b = Math.min(b + value, 255);
    }

    public void divide(double factor) {
        r = (int) Math.min(r / factor, 255);
        g = (int) Math.min(g / factor, 255);
        b = (int) Math.min(b / factor, 255);
    }

    public void multiply(double factor) {
        r = (int) Math.min(r * factor, 255);
        g = (int) Math.min(g * factor, 255);
        b = (int) Math.min(b * factor, 255);
    }

    public void brighten() {
        int largestValue = b;
        if (r > g) {
            largestValue = r;
        } else if (g > b) {
            largestValue = g;
        }
        double factor = 255.0 / largestValue;
        multiply(factor);
    }

    public BetterColor copy() {
        return new BetterColor(r, g, b);
    }

    public Color getColorObject() {
        return new Color(r, g, b);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Color[" + r + ", " + g + ", " + b + "]";
    }
}
