package me.ukaday.evolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Creatures {

    private final ArrayList<Creature> creatureContainer = new ArrayList<>();

    public Creatures() {
    }

    public void add(Creature c) {
        creatureContainer.add(c);
    }

    public void remove(Creature c) {
        creatureContainer.remove(c);
    }

    public void update() {
        for (Creature creature : List.copyOf(creatureContainer)) {
            creature.update();
            if (creature.getHealth() <= 0) {
                creatureContainer.remove(creature);
            }
        }
    }

    public ArrayList<Creature> getCreatureContainer() {
        return creatureContainer;
    }

    public void paint(Graphics g) {
        for (Creature c : creatureContainer) {
            c.paint(g);
        }
    }
}
