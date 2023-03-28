package me.ukaday.evolution;

import java.util.HashMap;
import java.util.Random;

import static me.ukaday.evolution.Evolution.*;
import static me.ukaday.evolution.InputListener.MOUSE_STATES;
import static me.ukaday.evolution.MainView.*;
import static me.ukaday.evolution.Settings.*;
import static me.ukaday.evolution.Stat.*;
import static me.ukaday.evolution.FocusedView.*;

public class Level implements Runnable {

    public static long DELTA_TIME = 1;
    public static Foods FOODS = new Foods();
    public static Creatures CREATURES = new Creatures();
    public static final Random r = new Random();
    private final Thread thread = new Thread(this);

    public Level() {
        HashMap<Stat, Double> stats = new HashMap<>();
        stats.put(RADIUS, STARTING_CREATURE_RADIUS);
        stats.put(SPEED, STARTING_CREATURE_SPEED);
        stats.put(STEER_FORCE, STARTING_CREATURE_STEER_FORCE);
        stats.put(STRENGTH, STARTING_CREATURE_STRENGTH);
        stats.put(MAX_ENERGY, STARTING_CREATURE_MAX_ENERGY);
        stats.put(MAX_HEALTH, STARTING_CREATURE_MAX_HEALTH);

        for (int i = 0; i < 30; i++) {
            int x = r.nextInt(SPAWN_BEZEL, WINDOW_W - SPAWN_BEZEL);
            int y = r.nextInt(SPAWN_BEZEL, WINDOW_H - SPAWN_BEZEL);
            CREATURES.add(new Creature(x, y, stats, FOODS, CREATURES));
        }

        for (int i = 0; i < 90; i++) {
            int x = r.nextInt(SPAWN_BEZEL,WINDOW_W - SPAWN_BEZEL);
            int y = r.nextInt(SPAWN_BEZEL,WINDOW_H - SPAWN_BEZEL);
            int energy = r.nextInt(1, 3);
            FOODS.add(new Food(x, y, energy));
        }

        thread.start();
    }

    public static void normalize(double[] v) {
        double dis = Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2));
        v[0] /= dis;
        v[1] /= dis;
    }

    public void handleInput() {

    }

    public void run() {
        try {
            while (true) {
                long startTime = System.currentTimeMillis();

                thread.sleep(5);
                updateStates();

                long currentTime = System.currentTimeMillis();
                DELTA_TIME = currentTime - startTime;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStates() {
        handleInput();
        FOODS.update();
        CREATURES.update();
    }
}
