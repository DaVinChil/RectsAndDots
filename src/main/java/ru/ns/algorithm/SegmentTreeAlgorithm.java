package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;
import ru.ns.segmenttree.PersistentSegmentTree;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;

public class SegmentTreeAlgorithm {

    private List<Change> changes;
    private int lastAddedChangeIndex = -1;
    private List<Long> differentY;
    private List<Long> differentX;
    private final PersistentSegmentTree tree;

    public SegmentTreeAlgorithm(List<Rectangle> rectangles) {
        prepareDifferentY(rectangles);
        prepareDifferentX(rectangles);
        prepareChanges(rectangles);
        tree = new PersistentSegmentTree(new long[differentY.size() - 1]);
    }

    private void prepareDifferentY(List<Rectangle> rectangles) {
        differentY = rectangles.stream()
                .flatMap(r -> Stream.of(r.leftBottom().second(), r.rightTop().second()))
                .distinct()
                .sorted()
                .toList();
    }

    private void prepareDifferentX(List<Rectangle> rectangles) {
        differentX = rectangles.stream()
                .flatMap(r -> Stream.of(r.leftBottom().first(), r.rightTop().first()))
                .distinct()
                .sorted()
                .toList();
    }

    private void prepareChanges(List<Rectangle> rectangles) {
        changes = rectangles.stream()
                .flatMap(r -> {
                    var yRange = new Pair<>(r.leftBottom().second(), r.rightTop().second());
                    return Stream.of(new Change(r.leftBottom().first(), yRange, 1),
                            new Change(r.rightTop().first(), yRange, -1));
                })
                .sorted((a, b) -> {
                    if (a.x != b.x) return Math.toIntExact(a.x - b.x);
                    return Math.toIntExact(a.change);
                })
                .toList();
    }

    public List<Long> calculateForPoints(List<Pair<Long, Long>> points) {
        var map = new HashMap<Pair<Long, Long>, Long>();
        points.stream().sorted(comparingLong(Pair::first)).forEach(p -> map.put(p, calculateForPoint(p)));
        return points.stream().map(map::get).toList();
    }

    public long calculateForPoint(Pair<Long, Long> point) {
        toStateByX(point.first());
        int index = yIndex(point.second());
        return tree.get(index);
    }

    private void toStateByX(long x) {
        int index = xIndex(x);

        if (index >= amountOfProcessedX() - 1) {
            prepareTreeUntil(x);
        } else {
            tree.toState(index);
        }
    }

    private void prepareTreeUntil(long x) {
        if (amountOfProcessedX() == differentX.size()) return;

        for (int i = lastAddedChangeIndex + 1;
             i < changes.size() && changes.get(i).x <= x;
             lastAddedChangeIndex = i, i++
        ) {
            applyChange(changes.get(i));
        }
    }

    private int amountOfProcessedX() {
        return tree.amountOfStates();
    }

    private void applyChange(Change change) {
        tree.addToSegment(change.change, yIndex(change.yRange.first()), yIndex(change.yRange.second()), change.x);
    }

    public int yIndex(long y) {
        return binSearch(differentY, y);
    }

    public int xIndex(long x) {
        return binSearch(differentX, x);
    }

    private int binSearch(List<Long> list, long target) {
        int index = Collections.binarySearch(list, target);
        if (index >= 0) {
            return index;
        } else if (index == -1 || -index == list.size() + 1) {
            return -1;
        }

        return -(index + 2);
    }

    private static class Change {
        long x;
        Pair<Long, Long> yRange;
        long change;

        Change(long x, Pair<Long, Long> yRange, long change) {
            this.x = x;
            this.yRange = yRange;
            this.change = change;
        }
    }
}
