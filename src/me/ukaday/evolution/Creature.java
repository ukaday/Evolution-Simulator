package me.ukaday.evolution;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static me.ukaday.evolution.CreatureState.*;
import static me.ukaday.evolution.EntityType.*;
import static me.ukaday.evolution.Level.*;
import static me.ukaday.evolution.Settings.*;
import static me.ukaday.evolution.Stat.*;

public class Creature extends Entity{

    Map<Stat, Double> stats;
    private double health;
    private double energy;
    private final double breedThreshold;
    private double mateTime = 0;
    private final double[] vel = new double[]{0, 0};
    private final double[] acel = new double[]{0, 0};
    private CreatureState state = SEARCHING_FOR_FOOD;
    private final Foods foods;
    private final Creatures creatures;
    private Color color;

    public Creature(double x, double y, Map<Stat, Double> stats, Foods foods, Creatures creatures) {
        super(x, y);
        this.stats = stats;
        this.health = stats.get(MAX_HEALTH);
        this.energy = stats.get(MAX_ENERGY) / 2;
        this.breedThreshold = 2 * stats.get(MAX_ENERGY) / 2;
        this.foods = foods;
        this.creatures = creatures;
        createCreatureColor();
    }

    public void move() {
        Entity target = state == SEARCHING_FOR_MATE ? seekEntity(CREATURE) : seekEntity(FOOD);

        double dx, dy;
        if (target != null) {
            dx = target.getX() - getX();
            dy = target.getY() - getY();
            acel[0] = dx - vel[0] * stats.get(SPEED);
            acel[1] = dy - vel[1] * stats.get(SPEED);
            normalize(acel);
        } else {
            acel[0] = acel[1] = 0;
        }

        vel[0] += acel[0] * stats.get(STEER_FORCE) * DELTA_TIME;
        vel[1] += acel[1] * stats.get(STEER_FORCE) * DELTA_TIME;
        normalize(vel);

        double newX = getX() + vel[0] * stats.get(SPEED) * DELTA_TIME;
        double newY = getY() + vel[1] * stats.get(SPEED) * DELTA_TIME;
        setX(newX);
        setY(newY);

        handleBorderCollision();

        if (target != null && state == SEARCHING_FOR_MATE && getEntityDistance(target) - getR() - ((Creature)target).getR() < CREATURE_MATING_DISTANCE) {
            breed((Creature)target);
        }

        energy -= CREATURE_MOVING_ENERGY_DEPRECIATION * DELTA_TIME;
    }

    public void attack(Creature creature) {
        creature.setHealth(creature.getHealth() - stats.get(STRENGTH) * DELTA_TIME);
    }

    private void handleBorderCollision() {
        if (getX() < stats.get(RADIUS)) {
            setX(stats.get(RADIUS));
        } else if (getX() > WINDOW_W - stats.get(RADIUS)) {
            setX(WINDOW_W - stats.get(RADIUS));
        }
        if (getY() < stats.get(RADIUS)) {
            setY(stats.get(RADIUS));
        } else if (getY() > WINDOW_H - stats.get(RADIUS)) {
            setY(WINDOW_H - stats.get(RADIUS));
        }
    }

    public boolean isColliding(Entity target) {
        if (target instanceof Food food) {
            return getEntityDistance(food) < stats.get(RADIUS);
        } else if (target instanceof Creature creature) {
            return getEntityDistance(creature) < stats.get(RADIUS) + creature.getR();
        }
        return false;
    }

    public Collection<Entity> testEntityCollisions() {
        Collection<Entity> collisions = new HashSet<>();
        for (Entity target: foods.getFoodContainer()) {
            if (isColliding(target)) {
                collisions.add(target);
            }
        }
        for (Entity target: creatures.getCreatures()) {
            if (isColliding(target) && target != this) {
                collisions.add(target);
            }
        }
        return collisions;
    }

    public void handleEntityCollisions() {
        Collection<Entity> collisions = testEntityCollisions();
        for (Entity target : collisions) {
            if (target instanceof Food food) {
                foods.remove(food);
                if (energy < stats.get(MAX_ENERGY)) { energy += food.getEnergy(); }
            } else if (target instanceof Creature creature) {
                attack(creature);
                double dx = creature.getX() - getX();
                double dy = creature.getY() - getY();
                double dis = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                double overlap = Math.abs(creature.getR() + getR() - dis);
                double[] correction = new double[]{dx, dy};
                normalize(correction);
                setX(getX() - correction[0] / 2 * overlap);
                setY(getY() - correction[1] / 2 * overlap);
            }
        }
    }

    public Entity seekEntity(EntityType type) {
        double minDis = Double.MAX_VALUE;
        Entity close = null;
        var entities = type == CREATURE ? creatures.getMateSearchingCreatures() : foods.getFoodContainer();

        for (var entity: entities) {
            double dis = getEntityDistance(entity);
            if (dis < minDis && entity != this) {
                minDis = dis;
                close = entity;
            }
        }
        return close;
    }

    public void breed(Creature creature) {
        state = MATING;
        creature.setState(MATING);
        mateTime = System.currentTimeMillis();
        creature.setMateTime(mateTime);

        Map<Stat, Double> stats = createChildStatMap(creature);
        if (ThreadLocalRandom.current().nextDouble(0, 1) < CREATURE_MUTATION_CHANCE / 100) {
            CreatureMutation mutation = new CreatureMutation(stats);
            stats = mutation.getStatMap();
        }

        energy -= CREATURE_BREED_ENERGY_DEPRECIATION;
        creatures.add(new Creature(getX() - 15, getY() - 15, stats, foods, creatures));
    }

    public Map<Stat, Double> createChildStatMap(Creature creature) {
        Map<Stat, Double> stats = new HashMap<>();
        stats.put(RADIUS, chooseStat(this, creature, RADIUS));
        stats.put(SPEED, chooseStat(this, creature, SPEED));
        stats.put(STEER_FORCE, chooseStat(this, creature, STEER_FORCE));
        stats.put(STRENGTH, chooseStat(this, creature, STRENGTH));
        stats.put(MAX_ENERGY, chooseStat(this, creature, MAX_ENERGY));
        stats.put(MAX_HEALTH, chooseStat(this, creature, MAX_HEALTH));
        return stats;
    }


    public static double chooseStat(Creature creature1, Creature creature2, Stat stat) {
        double parentChoice = ThreadLocalRandom.current().nextDouble(0, 1);
        return parentChoice < .5 ? creature2.getStat(stat) : creature1.getStat(stat);
    }

    public double getPercentageMutated(Stat stat) {
        return stats.get(stat) / STARTING_STAT_MAP.get(stat) * 100;
    }

    public BetterColor getColorContribution(Stat stat) {
        BetterColor contribution = STAT_COLOR_MAP.get(stat).copy();
        contribution.multiply(getPercentageMutated(stat) / 100);
        contribution.divide(stats.size());
        return contribution;
    }

    public void createCreatureColor() {
        BetterColor color = new BetterColor(0, 0, 0);
        for (var statEntry : stats.entrySet()) {
            BetterColor contribution = getColorContribution(statEntry.getKey());
            color.add(contribution);
        }
        color.brighten();
        this.color = color.getColorObject();
    }

    public void update() {
        handleEntityCollisions();
        if (state != MATING) {
            if (energy > breedThreshold) {
                state = SEARCHING_FOR_MATE;
            } else if (energy > 0) {
                state = SEARCHING_FOR_FOOD;
            } else {
                health -= CREATURE_HUNGRY_HEALTH_DEPRECIATION * DELTA_TIME;
            }
            move();
        } else if (System.currentTimeMillis() - mateTime > CREATURE_BREED_DELAY_MILLIS) {
            state = SEARCHING_FOR_FOOD;
            vel[0] *= -1;
            vel[1] *= -1;
        }
    }

    public CreatureState getState() {
        return state;
    }

    public void setState(CreatureState state) {
        this.state = state;
    }

    public void setMateTime(double mateTime) {
        this.mateTime = mateTime;
    }

    public double getStat(Stat stat) {
        return stats.get(stat);
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(Double h) {
        health = h;
    }

    public double getSpeed() {
        return stats.get(SPEED);
    }

    public double getR() {
        return stats.get(RADIUS);
    }

    public void paint(Graphics g, double xOffSet, double yOffSet, double zoom) {
        double x = (getX() - xOffSet) * zoom;
        double y = (getY() - yOffSet) * zoom;
        g.setColor(color);
        g.fillOval((int)(x - stats.get(RADIUS) * zoom), (int)(y - stats.get(RADIUS) * zoom), (int)(2 * stats.get(RADIUS) * zoom), (int)(2 * stats.get(RADIUS) * zoom));

        g.setColor(new Color(220, 0, 0));
        g.drawLine((int)x, (int)y, (int)(x + CREATURE_LINE_LENGTH * vel[0] * zoom), (int)(y + CREATURE_LINE_LENGTH * vel[1] * zoom));
    }

    @Override
    public String toString() {
        return "creature " + super.toString();
    }
}
