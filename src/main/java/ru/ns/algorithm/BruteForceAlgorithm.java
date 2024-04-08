package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;

import java.util.List;

public class BruteForceAlgorithm {
    private final List<Rectangle> rectangles;

    public BruteForceAlgorithm(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public List<Long> calculateForPoints(List<Pair<Long, Long>> points) {
        return points.stream()
                .map(this::calculateForPoint)
                .toList();
    }

    public long calculateForPoint(Pair<Long, Long> point) {
        long count = 0;
        for (var rect : rectangles) {
            if (rect.isPointInside(point)) count++;
        }
        return count;
    }
}
