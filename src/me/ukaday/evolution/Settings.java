package me.ukaday.evolution;

import java.util.*;

import static me.ukaday.evolution.Stat.*;

public class Settings {
    public static final int WINDOW_W = 800;
    public static final int WINDOW_H = 600;
    public static final int FOCUS_WINDOW_W = 600;
    public static final int FOCUS_WINDOW_H = 500;
    public static final int SPAWN_BEZEL = 15;
    public static final int CREATURE_LINE_LENGTH = 30;
    public static final double FOOD_SPAWN_DELAY = 50;

    public static final double STARTING_CREATURE_RADIUS = 10; // pixels
    public static final double STARTING_CREATURE_SPEED = .1; // pixels
    public static final double STARTING_CREATURE_STEER_FORCE = .004;
    public static final double STARTING_CREATURE_STRENGTH = .001; //health units
    public static final double STARTING_CREATURE_MAX_ENERGY = 15; //energy units
    public static final double STARTING_CREATURE_MAX_HEALTH = 20; //health units
    public static final List<Stat> ACTIVE_STATS = new ArrayList<>() {{
        add(RADIUS);
        add(SPEED);
        add(STEER_FORCE);
        add(STRENGTH);
        add(MAX_ENERGY);
        add(MAX_HEALTH);
    }};
    public static final Map<Stat, Double> STARTING_STAT_MAP = new HashMap<>() {{
        put(RADIUS, STARTING_CREATURE_RADIUS);
        put(SPEED, STARTING_CREATURE_SPEED);
        put(STEER_FORCE, STARTING_CREATURE_STEER_FORCE);
        put(STRENGTH, STARTING_CREATURE_STRENGTH);
        put(MAX_ENERGY, STARTING_CREATURE_MAX_ENERGY);
        put(MAX_HEALTH, STARTING_CREATURE_MAX_HEALTH);
    }};
    public static final Map<Stat, BetterColor> STAT_COLOR_MAP = new HashMap<>() {{
        put(RADIUS,      new BetterColor(255, 150, 0  ));
        put(SPEED,       new BetterColor(0,   0,   255));
        put(STEER_FORCE, new BetterColor(150, 0,   255));
        put(STRENGTH,    new BetterColor(0,   255, 0  ));
        put(MAX_ENERGY,  new BetterColor(0,   255, 150));
        put(MAX_HEALTH,  new BetterColor(255, 0,   0  ));
    }};


    public static final double CREATURE_MOVING_ENERGY_DEPRECIATION = .001; //energy units
    public static final double CREATURE_BREED_ENERGY_DEPRECIATION = 8; //energy units
    public static final double CREATURE_HUNGRY_HEALTH_DEPRECIATION = .5; //health units

    public static final double CREATURE_BREED_DELAY_MILLIS = 1000;
    public static final double CREATURE_MATING_DISTANCE = 20; // pixels
    public static final double CREATURE_MUTATION_CHANCE = 90; // percentage
    public static final double CREATURE_MUTATION_EFFECT = 20; // percentage
    public static final double CREATURE_COLLATERAL_MUTATION_EFFECT = 10; // percentage
    public static final Map<Stat, Map<Stat, Integer>> COLLATERAL_MUTATION_GRAPH = new HashMap<>() {{
        put(RADIUS,      new HashMap<>() {{ put(SPEED, -1); put(STRENGTH, 1); put(STEER_FORCE, -1); put(MAX_HEALTH, 1);}});
        put(SPEED,       new HashMap<>() {{ put(MAX_ENERGY, -1);}});
        put(STEER_FORCE, new HashMap<>() {{ put(MAX_ENERGY, -1);}});
        put(STRENGTH,    new HashMap<>() {{ put(MAX_ENERGY, -1);}});
        put(MAX_HEALTH,  null);
        put(MAX_ENERGY,  null);

    }};

    public static final double FOCUSED_VIEW_ZOOM_FACTOR = 1.7;
    public static final double CREATURE_CAMERA_SPEED_FACTOR = .015;
}
