package me.ukaday.evolution;

import java.awt.*;
import java.awt.image.BufferedImage;

import static me.ukaday.evolution.Evolution.*;

public abstract class View extends Canvas{

    protected Graphics2D gBack;
    private BufferedImage back;

    public View(Color color, InputListener inputListener) {
        addMouseListener(inputListener);
        addKeyListener(inputListener);
        setBackground(color);
        setVisible(true);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public abstract void draw();

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(back==null)
            back = (BufferedImage)(createImage(getWidth(),getHeight()));
        gBack = back.createGraphics();

        draw();

        g2.drawImage(back, null, 0, 0);
        back = null;
    }
}
