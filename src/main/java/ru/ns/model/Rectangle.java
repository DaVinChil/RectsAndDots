package ru.ns.model;

public record Rectangle(
        Point leftBottom,
        Point rightTop
) {
    public Pair<Long, Long> getYRange() {
        return Pair.of(leftBottom.y(), rightTop.y());
    }
}
