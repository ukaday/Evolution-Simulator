package me.ukaday.evolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Foods {

    private final List<Food> foodContainer = new ArrayList<>();

    public Foods() {
    }

    public void add(Food food) {
        foodContainer.add(food);
    }

    public void remove(Food food) {
        foodContainer.remove(food);
    }

    public List<Food> getFoodContainer() {
        return foodContainer;
    }

    public void update() {
    }

    public void paint(Graphics g) {
        for (Food food : foodContainer) {
            food.draw(g);
        }
    }
}
