package ru.ns.util;

import ru.ns.algorithm.Algorithm;
import ru.ns.model.Pair;
import ru.ns.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Benchmark {
    public static List<Pair<Long, Long>> measureFullAlgorithm(Algorithm algorithm, long amountOfRectangles, long maxAmountOfPoints) {
        var result = new ArrayList<Pair<Long, Long>>();

        var rectangles = Generator.generateRectangles(amountOfRectangles);
        for (long m = 2; m <= maxAmountOfPoints; m *= 2) {
            var points = Generator.generatePoints(m);

            long time = measureAverageTimeExecution(() -> {
                algorithm.prepare(rectangles);
                algorithm.solve(points);
            }, 10000);

            result.add(Pair.of(m, time));
        }

        return result;
    }

    private static long measureAverageTimeExecution(Runnable exec, int repeatTimes) {
        for (int i = 0; i < 100 && repeatTimes > 1; i++) exec.run();

        double res = 0;

        for (int i = 0; i < repeatTimes; i++) {
            res += ((double) measureTimeExecution(exec)) / repeatTimes;
        }

        return (long) res;
    }

    private static long measureTimeExecution(Runnable exec) {
        long start = System.nanoTime();
        exec.run();
        return System.nanoTime() - start;
    }
}
