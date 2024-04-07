package ru.ns.model;

public record Rectangle(
        Pair<Long, Long> leftBottom,
        Pair<Long, Long> rightTop
) {
    public boolean isPointInside(Pair<Long, Long> point) {
        long rMinX = leftBottom.first();
        long rMaxX = rightTop.first();
        long rMinY = leftBottom.second();
        long rMaxY = rightTop.second();

        return (rMinX <= point.first() && rMaxX > point.first())
                && (rMinY <= point.second() && rMaxY > point.second());
    }
}
