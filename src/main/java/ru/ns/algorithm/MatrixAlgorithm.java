package ru.ns.algorithm;

import ru.ns.model.Point;
import ru.ns.model.Rectangle;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class MatrixAlgorithm implements Algorithm {
    private List<Long> differentX;
    private List<Long> differentY;
    private long[][] matrix;

    @Override
    public void prepare(List<Rectangle> rectangles) {
        prepareDifferentY(rectangles);
        prepareDifferentX(rectangles);
        prepareMatrix(rectangles);
    }

    @Override
    public List<Long> solve(List<Point> points) {
        return points.stream()
                .map(this::calculateForPoint)
                .toList();
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

    private long calculateForPoint(Point point) {
        int xIndex = xIndex(point.x());
        int yIndex = yIndex(point.y());

        return getByIndexes(xIndex, yIndex);
    }

    private long getByIndexes(int i, int j) {
        try {
            return matrix[i][j];
        } catch (Exception e) {
            return 0;
        }
    }

    private void prepareMatrix(List<Rectangle> rectangles) {
        matrix = new long[differentX.size() - 1][differentY.size() - 1];
        rectangles.forEach(this::applyRectangle);
    }

    private void applyRectangle(Rectangle r) {
        int minXInd = xIndex(r.leftBottom().x());
        int maxXInd = xIndex(r.rightTop().x());
        int minYInd = yIndex(r.leftBottom().y());
        int maxYInd = yIndex(r.rightTop().y());

        for (int y = minYInd; y < maxYInd; y++) {
            for(int x = minXInd; x < maxXInd; x++) {
                matrix[x][y]++;
            }
        }
    }

    private int yIndex(long y) {
        return binSearch(differentY, y);
    }

    private int xIndex(long x) {
        return binSearch(differentX, x);
    }

    private int binSearch(List<Long> list, long target) {
        int index = Collections.binarySearch(list, target);
        if (index >= 0) {
            return index;
        }

        return -(index + 2);
    }
}
