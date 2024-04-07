package ru.ns;

import ru.ns.algorithm.Algorithm;
import ru.ns.algorithm.Algorithms;
import ru.ns.algorithm.PreparedAlgorithmFactory;

import static ru.ns.util.Benchmark.measureFullAlgorithm;
import static ru.ns.util.Benchmark.measureWithoutPrepAlgorithm;
import static ru.ns.util.ResultPrinter.showResult;

public class Main {
    private static final long N = (long) Math.pow(2, 3);

    public static void main(String[] args) {
        testFullAlgorithms();
        testWithoutPrepAlgorithms();
    }

    private static void testFullAlgorithms() {
        System.out.printf("""
                _________________ FULL ALGORITHM TESTING _________________
                Info:
                    Rectangles = %d
                    Points = 1 2 4 8 .. n^2
                
                """, N);

        testFullAlgorithm("BRUTE FORCE", Algorithms.BRUTE_FORCE);
        testFullAlgorithm("MATRIX", Algorithms.MATRIX);
        testFullAlgorithm("SEGMENT TREE", Algorithms.SEGMENT_TREE);
    }

    private static void testWithoutPrepAlgorithms() {
        System.out.println("""
                _________ WITH OUT PREPARATION ALGORITHM TESTING _________
                Info:
                    Points = 1
                    Rectangles = 1 2 3 4 .. n
                
                """);

        testWithoutPrepAlgorithm("BRUTE FORCE", Algorithms::createPreparedBruteForceAlgorithm);
        testWithoutPrepAlgorithm("MATRIX", Algorithms::createPreparedMatrixAlgorithm);
        testWithoutPrepAlgorithm("SEGMENT TREE", Algorithms::createPreparedSegmentTreeAlgorithm);
    }

    private static void testFullAlgorithm(String title, Algorithm algorithm) {
        var result = measureFullAlgorithm(algorithm, N);

        showResult(title, result.first(), result.second(), "Rectangles", "points");
    }

    private static void testWithoutPrepAlgorithm(String title, PreparedAlgorithmFactory preparedAlgorithmFactory) {
        var result = measureWithoutPrepAlgorithm(preparedAlgorithmFactory, (long) Math.pow(N, 4));

        showResult(title, result.first(), result.second(), "Points", "rectangles");
    }
}
