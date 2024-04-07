package ru.ns.segmenttree;

import java.util.ArrayList;
import java.util.List;

public class PersistentSegmentTree {
    public static final PersistentSegmentTree ZERO_TREE = new PersistentSegmentTree(new long[]{0});

    private SegmentTreeNode head;
    private final List<SegmentTreeNode> states = new ArrayList<>();
    private long currentStateIndex;

    public PersistentSegmentTree(long[] values) {
        head = SegmentTreeNode.of(values);
        states.add(head);
        currentStateIndex = 0;
    }

    public void toState(long index) {
        if (states.size() <= index) {
            throw new IndexOutOfBoundsException("No such state");
        }

        currentStateIndex = index;
        head = states.get((int) index);
    }

    public long currentStateIndex() {
        return states.size() - 1;
    }

    public long get(long index) {
        return head.get(index);
    }

    public void addToSegment(long value, long l, long r) {
        var newState = head.addToSegment(value, l, r);

        if (currentStateIndex + 1 < states.size()) {
            states.subList((int) (currentStateIndex + 1), states.size()).clear();
        }

        states.add(newState);
        head = newState;
        currentStateIndex++;
    }
}
