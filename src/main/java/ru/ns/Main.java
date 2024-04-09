package ru.ns;

import ru.ns.algorithm.Algorithm;
import ru.ns.algorithm.Algorithms;

import static ru.ns.util.Benchmark.measureFullAlgorithm;
import static ru.ns.util.Benchmark.measureWithoutPrepAlgorithm;
import static ru.ns.util.ResultPrinter.showResult;

public class Main {
    private static final long N = (long) Math.pow(2, 10);

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

        testWithoutPrepAlgorithm("BRUTE FORCE", Algorithms.BRUTE_FORCE);
        testWithoutPrepAlgorithm("MATRIX", Algorithms.MATRIX);
        testWithoutPrepAlgorithm("SEGMENT TREE", Algorithms.SEGMENT_TREE);
    }

    private static void testFullAlgorithm(String title, Algorithm algorithm) {
        var result = measureFullAlgorithm(algorithm, N);

        showResult(title, result, "points");
    }

    private static void testWithoutPrepAlgorithm(String title, Algorithm algorithm) {
        var result = measureWithoutPrepAlgorithm(algorithm, N);

        showResult(title, result, "rectangles");
    }
}
