package me.ukaday.evolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static me.ukaday.evolution.CreatureState.*;
import static me.ukaday.evolution.FocusedView.*;

public class Creatures {

    private final Collection<Creature> creatures = new ArrayList<>();
    private final Collection<Creature> mateSearchingCreatures = new ArrayList<>();

    public Creatures() {
    }

    public void add(Creature c) {
        creatures.add(c);
    }

    public void remove(Creature c) {
        creatures.remove(c);
    }

    private void checkDead(Creature creature) {
        if (creature.getHealth() <= 0) {
            creatures.remove(creature);
            mateSearchingCreatures.remove(creature);
        }
    }

    private void checkMateSearching(Creature creature) {
        if (creature.getState() == SEARCHING_FOR_MATE) {
            mateSearchingCreatures.add(creature);
        } else {
            mateSearchingCreatures.remove(creature);
        }
    }

    public Creature getTouchingCreature(double x, double y) {
        for (Creature creature : creatures) {
            double x2 = Math.pow(creature.getX() - x, 2);
            double y2 = Math.pow(creature.getY() - y, 2);
            if (Math.sqrt(x2 + y2) < creature.getR() + 10) {
                return creature;
            }
        }
        return null;
    }

    public void update() {
        for (Creature creature : Set.copyOf(creatures)) {
            creature.update();
            checkDead(creature);
            checkMateSearching(creature);
        }
    }

    public Collection<Creature> getCreatures() {
        return creatures;
    }
    public Collection<Creature> getMateSearchingCreatures() {
        return mateSearchingCreatures;
    }

    public void paint(Graphics g, double xOffSet, double yOffSet, double zoom) {
        for (Creature c : Set.copyOf(creatures)) {
            c.paint(g, xOffSet, yOffSet, zoom);
        }
    }
}
