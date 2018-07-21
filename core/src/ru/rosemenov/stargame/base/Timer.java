package ru.rosemenov.stargame.base;

/**
 * Таймер
 */

public class Timer {
    private float generateInterval;
    private volatile float generateTimer;
    private ActionListener actionListener;

    public Timer(float generateInterval, ActionListener actionListener) {
        this.actionListener = actionListener;
        this.generateInterval = generateInterval;
    }

    synchronized public void update(float delta) {
        generateTimer += delta;
        if (generateInterval <= generateTimer) {
            generateTimer = 0f;
            actionListener.actionPerformed(this);
        }
    }

}
