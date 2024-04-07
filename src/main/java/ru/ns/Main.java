package ru.ns;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int n = 5;
        List<Rectangle> rectangles = generateRectangles(n);
        var algo = new SegmentTreeSolution(rectangles);
        List<Pair<Integer, Integer>> points = generatePoints(n);
        var res = algo.calcForPoints(points);
        System.out.println(res);
    }

    private static List<Rectangle> generateRectangles(int n) {
        var arr = new ArrayList<Rectangle>();
        for (int i = 0; i < n; i++) {
            arr.add(new Rectangle(Pair.of(10 * i, 10 * i), Pair.of(10 * (2 * n - i), 10 * (2 * n - i))));
        }
        return arr;
    }

    private static List<Pair<Integer, Integer>> generatePoints(int n) {
        var arr = new ArrayList<Pair<Integer, Integer>>();
        int pX = 75254857;
        int pY = 40509479;
        for (int i = 0; i < n; i++) {
            int first = hashPoint(i, pX, n);
            int second = hashPoint(i, pY, n);
            arr.add(Pair.of(first, second));
        }

        return arr;
    }

    private static int hashPoint(int i, int p, int n) {
        return (int) (Math.pow((i * p), 31) % (20 * n));
    }
}
