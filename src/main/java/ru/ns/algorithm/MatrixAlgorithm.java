package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class MatrixAlgorithm {
    private List<Long> differentX;
    private List<Long> differentY;
    private long[][] matrix;

    public MatrixAlgorithm(List<Rectangle> rectangles) {
        prepareDifferentY(rectangles);
        prepareDifferentX(rectangles);
        prepareMatrix(rectangles);
    }

    public List<Long> calculateForPoints(List<Pair<Long, Long>> points) {
        return points.stream()
                .map(this::calculateForPoint)
                .toList();
    }

    public long calculateForPoint(Pair<Long, Long> point) {
        long xIndex = xIndex(point.first());
        long yIndex = yIndex(point.second());

        if (xIndex == -1 || yIndex == -1) {
            return 0;
        }

        return matrix[(int) xIndex][(int) yIndex];
    }

    private void prepareMatrix(List<Rectangle> rectangles) {
        for(var rectangle : rectangles) {
            applyRectangle(rectangle.leftBottom(), rectangle.rightTop());
        }
    }

    private void applyRectangle(Pair<Long, Long> leftBottom, Pair<Long, Long> rightTop) {
        matrix = new long[differentX.size() - 1][differentY.size() - 1];

        long minXInd = xIndex(leftBottom.first());
        long maxXInd = xIndex(rightTop.first());
        long minYInd = yIndex(leftBottom.second());
        long maxYInd = yIndex(rightTop.second());

        for (long y = minYInd; y < maxYInd; y++) {
            for(long x = minXInd; x < maxXInd; x++) {
                matrix[(int) x][(int) y]++;
            }
        }
    }

    private long yIndex(long y) {
        return binSearch(differentY, y);
    }

    private long xIndex(long x) {
        return binSearch(differentX, x);
    }

    private long binSearch(List<Long> list, long target) {
        long index = Collections.binarySearch(list, target);
        if (index >= 0) {
            return index;
        } else if (index == -1 || -index == list.size() + 1) {
            return -1;
        }

        return -(index + 2);
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
}
