package ru.ns.algorithm;

import ru.ns.model.Point;
import ru.ns.model.Rectangle;

import java.util.List;

public class BruteForceAlgorithm implements Algorithm {
    private List<Rectangle> rectangles;

    @Override
    public void prepare(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    @Override
    public List<Long> solve(List<Point> points) {
        return points.stream()
                .map(this::calculateForPoint)
                .toList();
    }

    public long calculateForPoint(Point point) {
        return rectangles.stream()
                .filter(point::isInside)
                .count();
    }
}
