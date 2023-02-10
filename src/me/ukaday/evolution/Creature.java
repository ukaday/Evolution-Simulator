package me.ukaday.evolution;

import java.awt.*;
import java.util.ArrayList;

import static me.ukaday.evolution.CreatureState.SEARCHING_FOR_FOOD;
import static me.ukaday.evolution.CreatureState.SEARCHING_FOR_MATE;
import static me.ukaday.evolution.CreatureState.MATING;
import static me.ukaday.evolution.EntityType.CREATURE;
import static me.ukaday.evolution.EntityType.FOOD;
import static me.ukaday.evolution.Evolution.WINDOW_H;
import static me.ukaday.evolution.Evolution.WINDOW_W;
import static me.ukaday.evolution.Level.DELTA_TIME;
import static me.ukaday.evolution.Level.normalize;

public class Creature extends Entity{

    private double x, y;
    private final int r;
    private final double speed;
    private final double steerForce;
    private final double strength;
    private final double maxEnergy;
    private final double maxHealth;
    private double health;
    private double energy;
    private final double breedEnergyThreshold;
    private final double[] vel = new double[]{0, 0};
    private final double[] accel = new double[]{0, 0};
    private CreatureState state = SEARCHING_FOR_FOOD;
    private final Foods foods;
    private final Creatures creatures;
    private final double lineLen = 35;

    public Creature(double x, double y, int r, double speed, double steerForce, double strength, double maxEnergy, double maxHealth, Foods foods, Creatures creatures) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.speed = speed;
        this.steerForce = steerForce;
        this.strength = strength;
        this.maxEnergy = maxEnergy;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.energy = maxEnergy / 2;
        this.breedEnergyThreshold = maxEnergy / 2;
        this.foods = foods;
        this.creatures = creatures;
    }

    public double getHealth() {
        return health;
    }
    public void setHealth(Double h) {
        health = h;
    }
    public double getEnergy() {
        return energy;
    }
    public int getR() { return r; }

    public void move() {
        Entity target = (state == SEARCHING_FOR_MATE ? seekEntity(CREATURE) : seekEntity(FOOD));

        double dx, dy;
        if (target != null) {
            dx = target.getX() - getX();
            dy = target.getY() - getY();
        } else {
            dx = dy = 0;
        }

        accel[0] = dx - vel[0] * speed;
        accel[1] = dy - vel[1] * speed;
        normalize(accel);

        vel[0] += accel[0] * steerForce * DELTA_TIME;
        vel[1] += accel[1] * steerForce * DELTA_TIME;
        normalize(vel);

        double nextX = getX() + vel[0] * speed * DELTA_TIME;
        double nextY = getY() + vel[1] * speed * DELTA_TIME;

        if (nextX - r <= 0 || r + nextX >= WINDOW_W) { nextX = x; }
        if (nextY - r <= 0 || r + nextY >= WINDOW_H) { nextY = y; }

        setX(nextX);
        setY(nextY);
        energy -= .00003 * DELTA_TIME;
    }

    public ArrayList<Entity> testEntityCollisions() {
        ArrayList<Entity> collisions = new ArrayList<>();
        for (Food target: foods.getFoodContainer()) {
            if (Math.sqrt(Math.pow((target.getX() - getX()),2) + Math.pow((target.getY() - getY()),2)) < r) {
                collisions.add(target);
            }
        }
        for (Creature target: creatures.getCreatureContainer()) {
            if (Math.sqrt(Math.pow(target.getR() + Math.abs(target.getX() - getX()),2) + Math.pow(target.getR() + Math.abs(target.getY() - getY()),2)) < r + target.getR() && target != this) {
                collisions.add(target);
            }
        }
        System.out.println(collisions);
        return collisions;
    }

    public void handleEntityCollisions() {
        ArrayList<Entity> collisions = testEntityCollisions();
        for (Entity target : collisions) {
            if (target instanceof Food food) {
                foods.remove(food);
                if (energy < maxEnergy) { energy += food.getEnergy(); }
            } else if (target instanceof Creature creature) {
                attack(creature);
            }
        }
    }

    public Entity seekEntity(EntityType type) {
        double minDis = Double.MAX_VALUE;
        Entity close = null;
        var list = type == CREATURE ? creatures.getCreatureContainer() : foods.getFoodContainer();

        for (var entity: list) {
            double dis = Math.pow((entity.getX() - getX()),2) + Math.pow((entity.getY() - getY()),2);
            if (dis < minDis) {
                minDis = dis;
                close = entity;
            }
        }
        return close;
    }

    public void breed(Creature c) {

    }

    public void attack(Creature creature) {
        creature.setHealth(creature.getHealth() - strength * DELTA_TIME);
    }

    public void update() {

        if (energy <= 0) {
            health -= .5 * DELTA_TIME;
        }
        if (state != MATING) { move(); }
        handleEntityCollisions();
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

    public void paint(Graphics g) {
        g.setColor(new Color(172, 248, 159));
        g.fillOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);

        g.setColor(new Color(220, 0, 0));
        g.drawLine((int)x, (int)y, (int)(x + lineLen * vel[0]), (int)(y + lineLen * vel[1]));
    }

    @Override
    public String toString() {
        return "creature" + x + " " + y;
    }
}
