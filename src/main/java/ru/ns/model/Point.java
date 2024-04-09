package ru.ns.model;

public record Point(long x, long y) {

    public static Point of(long x, long y) {
        return new Point(x, y);
    }

    public boolean isInside(Rectangle r) {
        long rMinX = r.leftBottom().x;
        long rMaxX = r.rightTop().x;
        long rMinY = r.leftBottom().y;
        long rMaxY = r.rightTop().y;

        return (rMinX <= x && rMaxX > x)
                && (rMinY <= y && rMaxY > y);
    }
}
