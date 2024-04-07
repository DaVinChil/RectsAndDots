package ru.ns.util;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ResultPrinter {
    private final String title;
    private final List<Long> times;
    private final List<Long> inputs;
    private final String constantType;
    private final String variableType;
    private final List<Integer> maxWidths;

    ResultPrinter(String title, List<Long> times, List<Long> inputs, String constantType, String variableType) {
        this.title = title;
        this.times = times;
        this.inputs = inputs;
        this.constantType = constantType;
        this.variableType = variableType;
        this.maxWidths = calculateMaxWidths();
    }

    public static void showResult(String title, List<Long> times, List<Long> inputs, String constantType, String variableType) {
        new ResultPrinter(title, times, inputs, constantType, variableType).showResult();
    }

    private void showResult() {
        System.out.printf("─────────── %s%n", title);
        printCeil();
        printTimes();
        printMiddle();
        printInputs();
        printBottom();
    }

    private void printBottom() {
        System.out.print("└──────────────");
        for (int size : maxWidths) {
            System.out.print("┴");
            for (int i = 0; i < size + 2; i++) {
                System.out.print("─");
            }
        }
        System.out.println("┘\n");
    }

    private void printMiddle() {
        System.out.print("├──────────────");
        for (long width : maxWidths) {
            System.out.print("┼─");
            for (int i = 0; i < width; i++) {
                System.out.print("─");
            }
            System.out.print("─");
        }

        System.out.print("┤");
        System.out.println();
    }

    private void printTimes() {
        System.out.print ("│ time (nanos) │");
        for (int i = 0; i < times.size(); i++) {
            printInCenter(maxWidths.get(i), String.valueOf(times.get(i)));
        }
        System.out.println();
    }

    private void printInputs() {
        System.out.print("│");
        printInCenter(12, variableType);
        for (int i = 0; i < inputs.size(); i++) {
            printInCenter(maxWidths.get(i), String.valueOf(inputs.get(i)));
        }
        System.out.println();
    }

    private void printInCenter(int maxWidth, String value) {
        int width = maxWidth - value.length();

        for (int k = 0; k < (int) Math.ceil(width / 2.0) + 1; k++) System.out.print(" ");

        System.out.print(value);

        for (int k = 0; k < width / 2 + 1; k++) System.out.print(" ");

        System.out.print("│");
    }

    private List<Integer> calculateMaxWidths() {
        AtomicLong m = new AtomicLong(2);
        return times.stream()
                .map(t -> {
                    String strTime = String.valueOf(t);
                    String strAmountOfPoints = String.valueOf(m.get());
                    m.updateAndGet(v -> v * 2);

                    return Math.max(strTime.length(), strAmountOfPoints.length());
                })
                .toList();
    }

    private void printCeil() {
        System.out.print("┌──────────────");
        for (int size : maxWidths) {
            System.out.print("┬");
            for (int i = 0; i < size + 2; i++) {
                System.out.print("─");
            }
        }
        System.out.println("┐");
    }
}
