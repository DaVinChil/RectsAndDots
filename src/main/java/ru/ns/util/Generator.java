package ru.ns.util;

import ru.ns.model.Pair;
import ru.ns.model.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static List<Rectangle> generateRectangles(long n) {
        var arr = new ArrayList<Rectangle>();
        for (long i = 0; i < n; i++) {
            arr.add(new Rectangle(Pair.of(10 * i, 10 * i), Pair.of(10 * (2 * n - i), 10 * (2 * n - i))));
        }
        return arr;
    }

    public static List<Pair<Long, Long>> generatePoints(long n) {
        var arr = new ArrayList<Pair<Long, Long>>();
        long pX = 75254857;
        long pY = 40509479;
        for (long i = 0; i < n; i++) {
            long first = hashPoint(i, pX, n);
            long second = hashPoint(i, pY, n);
            arr.add(Pair.of(first, second));
        }

        return arr;
    }

    private static long hashPoint(long i, long p, long n) {
        return (long) (Math.pow((i * p), 31) % (20 * n));
    }
}
