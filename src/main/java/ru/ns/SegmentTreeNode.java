package ru.ns;

import java.util.Arrays;

public class SegmentTreeNode {
    private int value;
    private int modifier;

    private final int leftBound;
    private final int rightBound;

    private SegmentTreeNode left;
    private SegmentTreeNode right;

    public SegmentTreeNode(int leftBound, int rightBound, int value) {
        this(leftBound, rightBound, value, null, null);
    }

    private SegmentTreeNode(int leftBound, int rightBound, int value, SegmentTreeNode left, SegmentTreeNode right) {
        this(leftBound, rightBound, value, left, right, 0);
    }

    private SegmentTreeNode(int leftBound, int rightBound, int value, SegmentTreeNode left, SegmentTreeNode right, int modifier) {
        this.left = left;
        this.right = right;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.value = value;
        this.modifier = modifier;
    }

    public static SegmentTreeNode of(SegmentTreeNode segmentTreeNode) {
        return new SegmentTreeNode(
                segmentTreeNode.leftBound,
                segmentTreeNode.rightBound,
                segmentTreeNode.value,
                segmentTreeNode.left,
                segmentTreeNode.right,
                segmentTreeNode.modifier
        );
    }

    public static SegmentTreeNode of(int[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Array's size can not be zero!");
        }

        return of(values, 0);
    }

    private static SegmentTreeNode of(int[] values, int offset) {
        if (values.length == 1) {
            return new SegmentTreeNode(offset, offset + 1, values[0]);
        }

        int mid = values.length / 2;
        int[] leftValues = Arrays.copyOfRange(values, 0, mid);
        int[] rightValues = Arrays.copyOfRange(values, mid, values.length);

        return new SegmentTreeNode(offset,
                values.length + offset,
                Arrays.stream(values).sum(),
                of(leftValues, offset),
                of(rightValues, mid + offset)
        );
    }

    public int get(int index) {
        if (index < leftBound || index >= rightBound) {
//            throw new IndexOutOfBoundsException("%d is out of [%d, %d) range".formatted(index, leftBound, rightBound));
            return 0;
        }

        SegmentTreeNode current = this;
        while (current.left != null && current.right != null) {
            current.propagate();

            int mid = (current.rightBound + current.leftBound) / 2;
            if (index < mid) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return current.value;
    }

    private void propagate() {
        if (modifier == 0) return;

        left.addToAllChildren(modifier);
        right.addToAllChildren(modifier);

        modifier = 0;
    }

    public SegmentTreeNode addToSegment(int value, int l, int r) {
        if (doesFullIntersect(l, r)) {
            SegmentTreeNode newNode = of(this);
            newNode.addToAllChildren(value);
            return newNode;
        } else if (doesIntersect(l, r)) {
            SegmentTreeNode newNode = of(this);
            newNode.addToForEachInIntersection(value, l, r);
            newNode.left = newNode.left.addToSegment(value, l, r);
            newNode.right = newNode.right.addToSegment(value, l, r);
            return newNode;
        }

        return this;
    }

    private boolean doesFullIntersect(int l, int r) {
        return leftBound >= l && rightBound <= r;
    }

    private boolean doesIntersect(int l, int r) {
        l = Math.max(l, leftBound);
        r = Math.min(r, rightBound);

        return l < rightBound && r > leftBound;
    }

    private void addToAllChildren(int value) {
        this.value += value * amountOfChildren();

        if (left != null || right != null) {
            modifier += value;
        }
    }

    private int amountOfChildren() {
        return rightBound - leftBound;
    }

    private void addToForEachInIntersection(int value, int l, int r) {
        int amount = intersectionSize(l, r);
        this.value += value * amount;
    }

    private int intersectionSize(int l, int r) {
        l = Math.max(l, leftBound);
        r = Math.min(r, rightBound);

        if (l >= rightBound || r <= leftBound) return 0;

        return r - l;
    }
}
