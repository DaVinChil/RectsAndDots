package ru.ns.segmenttree;

import java.util.Arrays;

public class SegmentTreeNode {
    private final long value;
    private long modifier;

    private final long leftBound;
    private final long rightBound;

    private SegmentTreeNode left;
    private SegmentTreeNode right;

    public SegmentTreeNode(long leftBound, long rightBound, long value) {
        this(leftBound, rightBound, value, null, null);
    }

    private SegmentTreeNode(long leftBound, long rightBound, long value, SegmentTreeNode left, SegmentTreeNode right) {
        this(leftBound, rightBound, value, left, right, 0);
    }

    private SegmentTreeNode(long leftBound, long rightBound, long value, SegmentTreeNode left, SegmentTreeNode right, long modifier) {
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

    public static SegmentTreeNode of(long[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Array's size can not be zero!");
        }

        return of(values, 0);
    }

    private static SegmentTreeNode of(long[] values, long offset) {
        if (values.length == 1) {
            return new SegmentTreeNode(offset, offset + 1, values[0]);
        }

        long mid = values.length / 2;
        long[] leftValues = Arrays.copyOfRange(values, 0, (int) mid);
        long[] rightValues = Arrays.copyOfRange(values, (int) mid, values.length);

        return new SegmentTreeNode(offset,
                values.length + offset,
                Arrays.stream(values).sum(),
                of(leftValues, offset),
                of(rightValues, mid + offset)
        );
    }

    private SegmentTreeNode copyWith(long newValue, long newModifier) {
        return new SegmentTreeNode(
                leftBound,
                rightBound,
                newValue,
                left,
                right,
                newModifier
        );
    }

    public long get(long index) {
        if (index < leftBound || index >= rightBound) {
            return 0;
        }

        SegmentTreeNode current = this;
        while (current.left != null && current.right != null) {
            current.propagate();

            long mid = (current.rightBound + current.leftBound) / 2;
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

        left = left.addToAllChildren(modifier);
        right = right.addToAllChildren(modifier);

        modifier = 0;
    }

    public SegmentTreeNode addToSegment(long value, long l, long r) {
        if (doesFullIntersect(l, r)) {
            return addToAllChildren(value);
        } else if (doesIntersect(l, r)) {
            SegmentTreeNode newNode = addToForEachInIntersection(value, l, r);
            newNode.left = newNode.left.addToSegment(value, l, r);
            newNode.right = newNode.right.addToSegment(value, l, r);
            return newNode;
        }

        return this;
    }

    private boolean doesFullIntersect(long l, long r) {
        return leftBound >= l && rightBound <= r;
    }

    private boolean doesIntersect(long l, long r) {
        l = Math.max(l, leftBound);
        r = Math.min(r, rightBound);

        return l < rightBound && r > leftBound;
    }

    private SegmentTreeNode addToAllChildren(long value) {
        return copyWith(
                this.value + value * amountOfChildren(),
                (left != null || right != null) ? modifier + value : modifier
        );
    }

    private long amountOfChildren() {
        return rightBound - leftBound;
    }

    private SegmentTreeNode addToForEachInIntersection(long value, long l, long r) {
        long amount = intersectionSize(l, r);

        return copyWith(this.value + value * amount, modifier);
    }

    private long intersectionSize(long l, long r) {
        l = Math.max(l, leftBound);
        r = Math.min(r, rightBound);

        if (l >= rightBound || r <= leftBound) return 0;

        return r - l;
    }
}
