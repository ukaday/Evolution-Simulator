package me.ukaday.evolution;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static me.ukaday.evolution.CreatureState.SEARCHING_FOR_MATE;

public class Creatures {

    private final HashSet<Creature> creatures = new HashSet<>();
    private final HashSet<Creature> mateSearchingCreatures = new HashSet<>();

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

    public void checkMateSearching(Creature creature) {
        if (creature.getState() == SEARCHING_FOR_MATE) {
            mateSearchingCreatures.add(creature);
        } else {
            mateSearchingCreatures.remove(creature);
        }
    }

    public void update() {
        for (Creature creature : Set.copyOf(creatures)) {
            creature.update();
            checkDead(creature);
            checkMateSearching(creature);
        }
    }

    public HashSet<Creature> getCreatures() {
        return creatures;
    }
    public HashSet<Creature> getMateSearchingCreatures() {
        return mateSearchingCreatures;
    }

    public void paint(Graphics g) {
        for (Creature c : creatures) {
            c.paint(g);
        }
    }
}
