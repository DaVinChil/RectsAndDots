package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;

import java.util.List;

public interface Algorithm {
    List<Long> solve(List<Rectangle> rectangles, List<Pair<Long, Long>> points);
}
