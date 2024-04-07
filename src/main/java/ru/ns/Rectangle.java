package ru.ns;

public record Rectangle(
        Pair<Integer, Integer> leftBottom,
        Pair<Integer, Integer> rightTop
) {
}
