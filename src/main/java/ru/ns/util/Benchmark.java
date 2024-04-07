package ru.ns.util;

import ru.ns.algorithm.Algorithm;
import ru.ns.algorithm.PreparedAlgorithmFactory;
import ru.ns.model.Pair;

import java.util.ArrayList;
import java.util.List;

public class Benchmark {

    public static Pair<List<Long>, List<Long>> measureWithoutPrepAlgorithm(PreparedAlgorithmFactory preparedAlgorithmFactory, long maxAmountOfRectangles) {
        var times = new ArrayList<Long>();
        var inputs = new ArrayList<Long>();

        for (long m = 1; m <= maxAmountOfRectangles; m *= 2) {
            inputs.add(m);
            var point = new Pair<>(m / 2, m / 2);
            var rectangles = Generator.generateRectangles(m);
            var algorithm = preparedAlgorithmFactory.createAlgorithm(rectangles);

            times.add(measureAverageTimeExecution(() -> algorithm.accept(point)));
        }

        return Pair.of(times, inputs);
    }

    public static Pair<List<Long>, List<Long>> measureFullAlgorithm(Algorithm algorithm, long amountOfRectangles) {
        var times = new ArrayList<Long>();
        var inputs = new ArrayList<Long>();

        var rectangles = Generator.generateRectangles(amountOfRectangles);

        for (long m = 2; m <= (int) Math.pow(amountOfRectangles, 2); m *= 2) {
            var points = Generator.generatePoints(m);
            inputs.add(m);

            times.add(measureAverageTimeExecution(() -> algorithm.solve(rectangles, points)));
        }

        return Pair.of(times, inputs);
    }

    private static long measureAverageTimeExecution(Runnable exec) {
        for (int i = 0; i < 10; i++) exec.run();

        double res = 0;
        int times = 1000;

        for (int i = 0; i < times; i++) {
            res += ((double) measureTimeExecution(exec)) / times;
        }

        return (long) res;
    }

    private static long measureTimeExecution(Runnable exec) {
        long start = System.nanoTime();
        exec.run();
        return System.nanoTime() - start;
    }
}
