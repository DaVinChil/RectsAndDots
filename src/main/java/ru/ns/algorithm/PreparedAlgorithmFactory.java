package ru.ns.algorithm;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;

import java.util.List;
import java.util.function.Consumer;

public interface PreparedAlgorithmFactory {
    Consumer<Pair<Long, Long>> createAlgorithm(List<Rectangle> rectangles);
}
