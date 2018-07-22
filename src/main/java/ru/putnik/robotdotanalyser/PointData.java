package ru.putnik.robotdotanalyser;

import java.awt.*;

public class PointData {
    private int x;
    private int y;
    private Color color;

    public PointData(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
