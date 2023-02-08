package me.ukaday.evolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Creatures {

    private final List<Creature> creatureContainer = new ArrayList<>();

    public Creatures() {
    }

    public void add(Creature c) {
        creatureContainer.add(c);
    }

    public void remove(Creature c) {
        creatureContainer.remove(c);
    }

    public void update() {
        for (Creature c : creatureContainer) {
            c.update();
        }
    }

    public List<Creature> getCreatureContainer() {
        return creatureContainer;
    }

    public void paint(Graphics g) {
        for (Creature c : creatureContainer) {
            c.paint(g);
        }
    }
}
