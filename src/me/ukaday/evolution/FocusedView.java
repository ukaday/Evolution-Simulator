package me.ukaday.evolution;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.ukaday.evolution.InputListener.KEY_STATES;
import static me.ukaday.evolution.InputListener.MOUSE_STATES;
import static me.ukaday.evolution.Level.CREATURES;
import static me.ukaday.evolution.Level.FOODS;
import static me.ukaday.evolution.Settings.FOCUSED_VIEW_ZOOM_FACTOR;

public class FocusedView extends View{

    private Creature focusedCreature;
    private final Map<Integer, Boolean> previousKeyStates = new HashMap<>();

    public FocusedView(InputListener inputListener) {
        super(new Color(50, 50, 70), inputListener);
    }

    public void handleInput() {
        for (var mouseState: MOUSE_STATES.entrySet()) {
            if (mouseState.getValue() != null) {
                if (mouseState.getKey() == 1) {
                    System.out.println(mouseState.getValue());
                    Creature creature =
                            CREATURES.getTouchingCreature(mouseState.getValue().x, mouseState.getValue().y);
                    if (creature != null) {
                        focusedCreature = creature;
                    }
                }
            }
        }
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
        var creatures = (List<Creature>)CREATURES.getCreatures();
        int index = creatures.indexOf(focusedCreature);

        focusedCreature = index < creatures.size() - 1 ?
                creatures.get(index + 1) :
                creatures.get(0);
    }

    private void switchToPreviousFocusedCreature() {
        var creatures = (List<Creature>)CREATURES.getCreatures();
        int index = creatures.indexOf(focusedCreature);

        focusedCreature = index > 0 ?
                creatures.get(index - 1) :
                creatures.get(creatures.size() - 1);
    }

    public void draw() {
        handleInput();
        if (!CREATURES.getCreatures().contains(focusedCreature)) {
            focusedCreature = ((List<Creature>)(CREATURES.getCreatures())).get(0);
        }

        double xOffSet = focusedCreature.getX() - this.getWidth() / 2.0 / FOCUSED_VIEW_ZOOM_FACTOR;
        double yOffSet = focusedCreature.getY() - this.getHeight() / 2.0 / FOCUSED_VIEW_ZOOM_FACTOR;

        FOODS.paint(gBack, xOffSet, yOffSet, FOCUSED_VIEW_ZOOM_FACTOR);
        CREATURES.paint(gBack, xOffSet, yOffSet, FOCUSED_VIEW_ZOOM_FACTOR);
    }
}
