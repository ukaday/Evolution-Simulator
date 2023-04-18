package me.ukaday.evolution;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.ukaday.evolution.InputListener.KEY_STATES;
import static me.ukaday.evolution.Level.CREATURES;

public class CreatureCamera extends Camera{

    private final Map<Integer, Boolean> previousKeyStates = new HashMap<>();

    public CreatureCamera() {
        super(((List<Creature>)(CREATURES.getCreatures())).get(0));
    }

    public CreatureCamera(Entity creature) {
        super(creature);
    }

    public void update() {
        if (!CREATURES.getCreatures().contains((Creature)getFocus())) {
            setFocus(((List<Creature>) (CREATURES.getCreatures())).get(0));
        }
        handleInput();
        super.update();
    }

    public void handleInput() {
        for (var keyState : KEY_STATES.entrySet()) {
            if (keyState.getValue()) {
                switch (keyState.getKey()) {
                    case KeyEvent.VK_RIGHT:
                        if (previousKeyStates.get(keyState.getKey()) == null
                                || !previousKeyStates.get(keyState.getKey()))
                            switchToNextFocusedCreature();
                        break;
                    case KeyEvent.VK_LEFT:
                        if (previousKeyStates.get(keyState.getKey()) == null
                                || !previousKeyStates.get(keyState.getKey()))
                            switchToPreviousFocusedCreature();
                        break;
                }
            }
        }
        previousKeyStates.put(KeyEvent.VK_RIGHT, KEY_STATES.get(KeyEvent.VK_RIGHT));
        previousKeyStates.put(KeyEvent.VK_LEFT, KEY_STATES.get(KeyEvent.VK_LEFT));
    }

    private void switchToNextFocusedCreature() {
        var creatures = (List<Creature>) CREATURES.getCreatures();
        int index = creatures.indexOf((Creature)getFocus());

        setFocus(index < creatures.size() - 1 ? creatures.get(index + 1) :
                creatures.get(0));
    }

    private void switchToPreviousFocusedCreature() {
        var creatures = (List<Creature>)CREATURES.getCreatures();
        int index = creatures.indexOf((Creature)getFocus());

        setFocus(index > 0 ? creatures.get(index - 1) :
                creatures.get(creatures.size() - 1));
    }

}
