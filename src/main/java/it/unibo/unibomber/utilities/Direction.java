package it.unibo.unibomber.utilities;

public enum Direction {
    CENTER(0, 0),
    UP(0, 1),
    LEFT(-1, 0),
    DOWN(0, -1),
    RIGHT(1, 0);

    private int x;
    private int y;

    /**
     * @param x
     * @param y
     */
    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return y
     */
    public int getY() {
        return this.y;
    }

    public static Direction extractDirecion(final Pair<Float, Float> movement) {
        if (movement.getX() > 0 && movement.getY() == 0) {
            return RIGHT;
        } else if (movement.getX() < 0 && movement.getY() == 0) {
            return LEFT;
        } else if (movement.getX() == 0 && movement.getY() < 0) {
            return UP;
        } else if (movement.getX() == 0 && movement.getY() > 0) {
            return DOWN;
        }
        return CENTER;
    }

}
