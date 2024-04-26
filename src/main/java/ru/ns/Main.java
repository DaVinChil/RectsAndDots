package ru.ns;

import ru.ns.algorithm.Algorithm;
import ru.ns.algorithm.Algorithms;

import static ru.ns.util.Benchmark.measureFullAlgorithm;
import static ru.ns.util.ResultPrinter.showResult;

public class Main {
    private static long N = (long) Math.pow(2, 7);

    public static void main(String[] args) {
        testUntilN2();
        testUntilN3();
    }

    private static void testUntilN2() {
        System.out.printf("""
                _________________ APPROACH 1 _________________
                Info:
                    Rectangles = %d
                    Points = 1 2 4 8 .. n^2
                
                """, N);

        testUntilN2("BRUTE FORCE", Algorithms.BRUTE_FORCE);
        testUntilN2("MATRIX", Algorithms.MATRIX);
        testUntilN2("SEGMENT TREE", Algorithms.SEGMENT_TREE);
    }

    private static void testUntilN3() {
        System.out.println("""
                _________________ APPROACH 2 _________________
                Info:
                    Rectangles = %d
                    Points = 1 2 4 8 .. n^3
                
                """);

        testUntilN3("BRUTE FORCE", Algorithms.BRUTE_FORCE);
        testUntilN3("MATRIX", Algorithms.MATRIX);
        testUntilN3("SEGMENT TREE", Algorithms.SEGMENT_TREE);
    }

    private static void testUntilN2(String title, Algorithm algorithm) {
        var result = measureFullAlgorithm(algorithm, N, (long) Math.pow(N, 2));

        showResult(title, result, "points");
    }

    private static void testUntilN3(String title, Algorithm algorithm) {
        var result = measureFullAlgorithm(algorithm, N, (long) Math.pow(N, 3));

        showResult(title, result, "points");
    }
}
