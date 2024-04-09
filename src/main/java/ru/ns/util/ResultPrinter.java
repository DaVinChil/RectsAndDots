package ru.ns.util;


import ru.ns.model.Pair;

import java.util.List;

public class ResultPrinter {
    private final String title;
    private final List<Pair<Long, Long>> inputResults;
    private final String parameterType;
    private final List<Integer> maxWidths;

    ResultPrinter(String title, List<Pair<Long, Long>> inputResults, String parameterType) {
        this.title = title;
        this.inputResults = inputResults;
        this.parameterType = parameterType;
        this.maxWidths = calculateMaxWidths();
    }

    public static void showResult(String title, List<Pair<Long, Long>> inputResults, String parameterType) {
        new ResultPrinter(title, inputResults, parameterType).showResult();
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
        for (int i = 0; i < inputResults.size(); i++) {
            printInCenter(maxWidths.get(i), String.valueOf(inputResults.get(i).second()));
        }
        System.out.println();
    }

    private void printInputs() {
        System.out.print("│");
        printInCenter(12, parameterType);
        for (int i = 0; i < inputResults.size(); i++) {
            printInCenter(maxWidths.get(i), String.valueOf(inputResults.get(i).first()));
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
        return inputResults.stream()
                .map(pair -> {
                    String strTime = String.valueOf(pair.second());
                    String strAmountOfPoints = String.valueOf(pair.first());

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
