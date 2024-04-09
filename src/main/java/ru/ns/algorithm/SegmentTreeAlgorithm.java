package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Point;
import ru.ns.model.Rectangle;
import ru.ns.segmenttree.PersistentSegmentTree;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SegmentTreeAlgorithm implements Algorithm {

    private List<Change> changes;
    private List<Long> differentY;
    private List<Long> differentX;
    private PersistentSegmentTree tree;

    @Override
    public void prepare(List<Rectangle> rectangles) {
        prepareDifferentY(rectangles);
        prepareDifferentX(rectangles);
        prepareChanges(rectangles);
        prepareTree();
    }

    private void prepareDifferentY(List<Rectangle> rectangles) {
        differentY = rectangles.stream()
                .flatMap(r -> Stream.of(r.leftBottom().y(), r.rightTop().y()))
                .distinct()
                .sorted()
                .toList();
    }

    private void prepareDifferentX(List<Rectangle> rectangles) {
        differentX = rectangles.stream()
                .flatMap(r -> Stream.of(r.leftBottom().x(), r.rightTop().x()))
                .distinct()
                .sorted()
                .toList();
    }

    private void prepareChanges(List<Rectangle> rectangles) {
        changes = rectangles.stream()
                .flatMap(r -> {
                    var yRange = r.getYRange();
                    long startX = r.leftBottom().x();
                    long endX = r.rightTop().x();

                    return Stream.of(new Change(startX, yRange, 1), new Change(endX, yRange, -1));
                })
                .sorted()
                .toList();
    }

    private void prepareTree() {
        tree = new PersistentSegmentTree(new long[differentY.size() - 1]);
        changes.forEach(this::applyChange);
    }

    private void applyChange(Change change) {
        tree.addToSegment(change.change, yIndex(change.yRange.first()), yIndex(change.yRange.second()), change.x);
    }

    public int xIndex(long x) {
        return binSearch(differentX, x);
    }

    @Override
    public List<Long> solve(List<Point> points) {
        return points.stream().map(this::calculateForPoint).toList();
    }

    public long calculateForPoint(Point point) {
        toStateByX(point.x());
        int yIndex = yIndex(point.y());
        return tree.get(yIndex);
    }

    private void toStateByX(long x) {
        tree.toState(xIndex(x));
    }

    public int yIndex(long y) {
        return binSearch(differentY, y);
    }

    private int binSearch(List<Long> list, long target) {
        int index = Collections.binarySearch(list, target);

        if (index >= 0) return index;
        return -(index + 2);
    }

    private static class Change implements Comparable<Change> {
        long x;
        Pair<Long, Long> yRange;
        long change;

        Change(long x, Pair<Long, Long> yRange, long change) {
            this.x = x;
            this.yRange = yRange;
            this.change = change;
        }

        @Override
        public int compareTo(Change o) {
            return x - o.x < 0 ? -1 : (x == o.x ? 0 : 1);
        }
    }
}
