package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;
import ru.ns.segmenttree.PersistentSegmentTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SegmentTreeAlgorithm {

    private List<Change> changes;
    private List<Long> differentY;
    private List<Long> differentX;
    private final List<Long> treeStateIndexes = new ArrayList<>();
    private PersistentSegmentTree tree;

    public SegmentTreeAlgorithm(List<Rectangle> rectangles) {
        prepareDifferentY(rectangles);
        prepareDifferentX(rectangles);
        prepareChanges(rectangles);
        prepareTree();
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
                    return Stream.of(
                            new Change(r.leftBottom().first(), yRange, 1),
                            new Change(r.rightTop().first(), yRange, -1));
                })
                .sorted((a, b) -> {
                    if (a.x != b.x) {
                        return Math.toIntExact(a.x - b.x);
                    }

                    return Math.toIntExact(a.change);
                })
                .toList();
    }

    private void prepareTree() {
        tree = new PersistentSegmentTree(new long[differentY.size() - 1]);

        long curX = changes.getFirst().x;
        for (var change : changes) {
            if (change.x != curX) {
                treeStateIndexes.add(tree.currentStateIndex());
                curX = change.x;
            }

            tree.addToSegment(change.change, yIndex(change.yRange.first()), yIndex(change.yRange.second()));
        }
        treeStateIndexes.add(tree.currentStateIndex());
    }

    public List<Long> calculateForPoints(List<Pair<Long, Long>> points) {
        var result = new ArrayList<Long>();

        for (var point : points) {
            result.add(calculateForPoint(point));
        }

        return result;
    }

    public long calculateForPoint(Pair<Long, Long> point) {
        var state = stateForX(point.first());
        return state.get(yIndex(point.second()));
    }

    public PersistentSegmentTree stateForX(long x) {
        long index = xIndex(x);
        if (index == -1) {
            return PersistentSegmentTree.ZERO_TREE;
        }

        long stateIndex = treeStateIndexes.get((int) index);
        tree.toState(stateIndex);
        return tree;
    }

    public long yIndex(long y) {
        return binSearch(differentY, y);
    }

    public long xIndex(long x) {
        return binSearch(differentX, x);
    }

    private long binSearch(List<Long> list, long target) {
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
