package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;

import java.util.List;
import java.util.function.Consumer;

public class Algorithms {
    public static final Algorithm BRUTE_FORCE =
            (rectangles, points) -> new BruteForceAlgorithm(rectangles).calculateForPoints(points);
    public static final Algorithm MATRIX =
            (rectangles, points) -> new MatrixAlgorithm(rectangles).calculateForPoints(points);
    public static final Algorithm SEGMENT_TREE =
            (rectangles, points) -> new SegmentTreeAlgorithm(rectangles).calculateForPoints(points);

    public static Consumer<Pair<Long, Long>> createPreparedBruteForceAlgorithm(List<Rectangle> rectangles) {
        return new BruteForceAlgorithm(rectangles)::calculateForPoint;
    }

    public static Consumer<Pair<Long, Long>> createPreparedMatrixAlgorithm(List<Rectangle> rectangles) {
        return new MatrixAlgorithm(rectangles)::calculateForPoint;
    }

    public static Consumer<Pair<Long, Long>> createPreparedSegmentTreeAlgorithm(List<Rectangle> rectangles) {
        return new SegmentTreeAlgorithm(rectangles)::calculateForPoint;
    }
}
