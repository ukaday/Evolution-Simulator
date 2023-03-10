package me.ukaday.evolution;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.awt.event.KeyEvent.*;
import static me.ukaday.evolution.Direction.*;
import static me.ukaday.evolution.Evolution.WINDOW_H;
import static me.ukaday.evolution.Evolution.WINDOW_W;
import static me.ukaday.evolution.Settings.*;
import static me.ukaday.evolution.Stat.*;

public class Level extends Canvas implements KeyListener, Runnable {

    public static long DELTA_TIME = 1;
    public Ball ball;
    public Foods foods = new Foods();
    public Creatures creatures = new Creatures();
    public static final Random r = new Random();
    public static Creature focusedCreature;
    private BufferedImage back;
    private final Map<Integer, Boolean> keyStates = new HashMap<>();

    public Level() {
        setBackground(new Color(50, 50, 70));
        addKeyListener(this);
        setVisible(true);

        HashMap<Stat, Double> stats = new HashMap<>();
        stats.put(RADIUS, STARTING_CREATURE_RADIUS);
        stats.put(SPEED, STARTING_CREATURE_SPEED);
        stats.put(STEER_FORCE, STARTING_CREATURE_STEER_FORCE);
        stats.put(STRENGTH, STARTING_CREATURE_STRENGTH);
        stats.put(MAX_ENERGY, STARTING_CREATURE_MAX_ENERGY);
        stats.put(MAX_HEALTH, STARTING_CREATURE_MAX_HEALTH);

        //ball = new Ball(50, 50, 15);
        for (int i = 0; i < 3; i++) {
            int x = r.nextInt(SPAWN_BEZEL, WINDOW_W - SPAWN_BEZEL);
            int y = r.nextInt(SPAWN_BEZEL, WINDOW_H - SPAWN_BEZEL);
            creatures.add(new Creature(x, y, stats, foods, creatures));
        }
        for (int i = 0; i < 20; i++) {
            int x = r.nextInt(SPAWN_BEZEL,WINDOW_W - SPAWN_BEZEL);
            int y = r.nextInt(SPAWN_BEZEL,WINDOW_H - SPAWN_BEZEL);
            int energy = r.nextInt(1, 3);
            foods.add(new Food(x, y, energy));
        }
    }

    public static void normalize(double[] v) {
        double dis = Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2));
        v[0] /= dis;
        v[1] /= dis;
    }

    //OBJECT HANDLERS
    public void handleInput() {
        for (var keyState: keyStates.entrySet()) {
            if (keyState.getValue()) {
                switch (keyState.getKey()) {
                    case VK_W -> ball.move(FORWARD);
                    case VK_S -> ball.move(BACKWARD);
                    case VK_A -> ball.turn(LEFT);
                    case VK_D -> ball.turn(RIGHT);
                }
            }
        }
    }

    //KEY LISTENER INTERFACE
    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        keyStates.put(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        keyStates.put(e.getKeyCode(), false);
    }

    //MAIN LOOP FUNCTIONS
    public void update(Graphics g) {
        handleInput();
        foods.update();
        creatures.update();
        paint(g);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(back==null)
            back = (BufferedImage)(createImage(getWidth(),getHeight()));
        Graphics gBack = back.createGraphics();

        foods.paint(gBack);
        creatures.paint(gBack);
        //ball.paint(gBack);

        g2.drawImage(back, null, 0, 0);
        back = null;
    }

    public void run() {
        try {
            long prevTime = 0;
            while(true) {
                long time = System.currentTimeMillis();
                DELTA_TIME = time - prevTime;
                Thread.sleep(5);
                repaint();
                prevTime = time;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
