package ru.ns.algorithm;

import ru.ns.model.Point;
import ru.ns.model.Rectangle;

import java.util.List;

public interface Algorithm {
    void prepare(List<Rectangle> rectangles);
    List<Long> solve(List<Point> points);
}
