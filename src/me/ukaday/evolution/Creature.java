package me.ukaday.evolution;

import java.awt.*;
import java.util.ArrayList;

import static me.ukaday.evolution.Level.DELTA_TIME;
import static me.ukaday.evolution.Level.normalize;

public class Creature {

    private double x, y;
    private final int r;
    private final double speed;
    private final double steerForce;
    private final int maxEnergy;
    private final int maxHealth;
    private int energy;
    private final double[] vel = new double[]{0, 0};
    private final double[] accel = new double[]{0, 0};
    private final Foods foods;
    private final Creatures creatures;
    private final double lineLen = 50;

    public Creature(double x, double y, int r, double speed, double steerForce, int maxEnergy, int maxHealth, Foods foods, Creatures creatures) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.speed = speed;
        this.steerForce = steerForce;
        this.maxEnergy = maxEnergy;
        this.maxHealth = maxHealth;
        this.foods = foods;
        this.creatures = creatures;
    }

    public void move() {
        Food nearestFood = seek();

        double dx, dy;
        if (nearestFood != null) {
            dx = nearestFood.getX() - x;
            dy = nearestFood.getY() - y;
        } else {
            dx = dy = 0;
        }

        accel[0] = dx - vel[0] * speed;
        accel[1] = dy - vel[1] * speed;
        normalize(accel);

        vel[0] += accel[0] * steerForce * DELTA_TIME;
        vel[1] += accel[1] * steerForce * DELTA_TIME;
        normalize(vel);

        double nextX = x + vel[0] * speed * DELTA_TIME;
        double nextY = y + vel[1] * speed * DELTA_TIME;

        if (nextX - r <= 0 || r + nextX >= Evolution.WINDOW_W) { nextX = x; }
        if (nextY - r <= 0 || r + nextY >= Evolution.WINDOW_H) { nextY = y; }

        x = nextX;
        y = nextY;
    }

    public ArrayList<Food> testFoodCollisions() {
        ArrayList<Food> collisions = new ArrayList<>();
        double cx = x;
        double cy = y;
        for (Food food: foods.getFoodContainer()) {
            if (Math.sqrt(Math.pow((food.getX() - x),2) + Math.pow((food.getY() - y),2)) < r) {
                collisions.add(food);
            }
        }
        return collisions;
    }

    public void handleFoodCollisions() {
        ArrayList<Food> collisions = testFoodCollisions();
        for (Food food : collisions) {
            foods.remove(food);
        }
    }

    public double getFoodDis(Food food) {
        return Math.sqrt(Math.pow((food.getX() - x),2) + Math.pow((food.getY() - y),2));
    }

    public Food seek() {
        double minDis = Double.MAX_VALUE;
        Food close = null;
        for (Food food: foods.getFoodContainer()) {
            double x1 = food.getX();
            double y1 = food.getY();

            double dis = Math.pow((x1 - x),2) + Math.pow((y1 - y),2);
            if (dis < minDis) {
                minDis = dis;
                close = food;
            }
        }
        return close;
    }

    public void breed(Creature c) {

    }

    public void update() {
        move();
        handleFoodCollisions();
    }

    public void paint(Graphics g) {
        g.setColor(new Color(172, 248, 159));
        g.fillOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);

        g.setColor(new Color(220, 0, 0));
        g.drawLine((int)x, (int)y, (int)(x + lineLen * vel[0]), (int)(y + lineLen * vel[1]));
    }
}
