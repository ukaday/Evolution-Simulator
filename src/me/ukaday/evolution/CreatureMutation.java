package me.ukaday.evolution;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static me.ukaday.evolution.Settings.*;

public class CreatureMutation {

    public Map<Stat, Double> statMap = new HashMap<>();
    public Stat mutatedStat;
    boolean evolved = true;
    double evolvedMultiplier = 1;

    public CreatureMutation(Map<Stat, Double> statMap) {
        this.statMap.putAll(statMap);
        mutate();
    }

    public void mutate() {
        mutateStat(chooseStat());
        adjustOtherStats();
    }

    public Stat chooseStat() {
        int choice = ThreadLocalRandom.current().nextInt(0, statMap.size());
        return ACTIVE_STATS.get(choice);
    }

    public void mutateStat(Stat stat) {
        double choice = ThreadLocalRandom.current().nextDouble(0, 1);
        evolved = choice < .5;
        double statValue = statMap.get(stat);
        if (evolved) {
            statValue += (statValue * CREATURE_MUTATION_EFFECT / 100);
        } else {
            statValue -= (statValue * CREATURE_MUTATION_EFFECT / 100);
            evolvedMultiplier = -1;
        }
        statMap.put(stat, statValue);
        mutatedStat = stat;
    }

    public void adjustOtherStats() {
        if (COLLATERAL_MUTATION_GRAPH.get(mutatedStat) != null) {
            for (var entry : COLLATERAL_MUTATION_GRAPH.get(mutatedStat).entrySet()) {
                double statValue = statMap.get(entry.getKey());
                statMap.put(entry.getKey(), statValue + (evolvedMultiplier * entry.getValue() * statValue * CREATURE_COLLATERAL_MUTATION_EFFECT / 100));
            }
        }
    }

    public Map<Stat, Double> getStatMap() {
        return statMap;
    }

    public Stat getMutatedStat() {
        return mutatedStat;
    }

    @Override
    public String toString() {
        return "Mutated " + mutatedStat.toString() + " : " + statMap.get(mutatedStat);
    }
}
