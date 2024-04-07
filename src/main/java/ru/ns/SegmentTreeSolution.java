package ru.ns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SegmentTreeSolution {

    private List<Change> changes;
    private List<Integer> differentY;
    private List<Integer> differentX;
    private final List<Integer> stateIndexes = new ArrayList<>();
    private PersistentSegmentTree tree;

    public SegmentTreeSolution(List<Rectangle> rectangles) {
        differentY = rectangles.stream()
                .flatMap(r -> Stream.of(r.leftBottom().second(), r.rightTop().second()))
                .distinct()
                .sorted()
                .toList();

        differentX = rectangles.stream()
                .flatMap(r -> Stream.of(r.leftBottom().first(), r.rightTop().first()))
                .distinct()
                .sorted()
                .toList();

        changes = rectangles.stream()
                .flatMap(r -> {
                    var yRange = new Pair<>(r.leftBottom().second(), r.rightTop().second());
                    return Stream.of(
                            new Change(r.leftBottom().first(), yRange, 1),
                            new Change(r.rightTop().first(), yRange, -1));
                })
                .sorted((a, b) -> {
                    if (a.x != b.x) {
                        return a.x - b.x;
                    }

                    return a.change;
                })
                .toList();

        tree = new PersistentSegmentTree(new int[differentY.size() - 1]);

        prepare();
    }

    private void prepare() {
        int curX = changes.getFirst().x;

        for (var change : changes) {
            if (change.x != curX) {
                stateIndexes.add(tree.currentStateIndex());
                curX = change.x;
            }

            tree.addToSegment(change.change, yIndex(change.yRange.first()), yIndex(change.yRange.second()));
        }

        stateIndexes.add(tree.currentStateIndex());
    }

    public List<Integer> calcForPoints(List<Pair<Integer, Integer>> points) {
        points.sort((a, b) -> {
            if (!Objects.equals(a.first(), b.first())) {
                return a.first() - b.first();
            }

            return a.second() - b.second();
        });

        var result = new ArrayList<Integer>();

        int i = 0;
        int curX = changes.getFirst().x;
        var change = changes.get(i);

        for (var point : points) {
            while (i < changes.size() && changes.get(i).x <= point.first()) {
                change = changes.get(i);

                if (change.x != curX) {
                    stateIndexes.add(tree.currentStateIndex());
                    curX = change.x;
                }

                tree.addToSegment(change.change, yIndex(change.yRange.first()), yIndex(change.yRange.second()));
                i++;
            }

            if (i != changes.size() && changes.get(i).x != curX) {
                curX = changes.get(i).x;
            }

            stateIndexes.add(tree.currentStateIndex());

            result.add(tree.get(yIndex(point.second())));
        }

        return result;
    }

    public int yIndex(int y) {
        int index = Collections.binarySearch(differentY, y);
        if (index >= 0) {
            return index;
        } else if (index == -1 || -index == differentY.size() + 1) {
            return -1;
        }

        return -(index + 2);
    }

    public int xIndex(int x) {
        int index = Collections.binarySearch(differentX, x);
        if (index >= 0) {
            return index;
        } else if (index == -1 || -index == differentX.size() + 1) {
            return -1;
        }

        return -(index + 2);
    }

    private static class Change {
        int x;
        Pair<Integer, Integer> yRange;
        int change;

        Change(int x) {
            this.x = x;
        }

        Change(int x, Pair<Integer, Integer> yRange, int change) {
            this.x = x;
            this.yRange = yRange;
            this.change = change;
        }
    }
}
