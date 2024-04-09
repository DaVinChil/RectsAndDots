package ru.ns.segmenttree;

import java.util.ArrayList;
import java.util.List;

public class PersistentSegmentTree {
    public static final PersistentSegmentTree ZERO_TREE = new PersistentSegmentTree(new long[]{0});

    private SegmentTreeNode head;
    private final List<SegmentTreeNode> states = new ArrayList<>();
    private long x = -1;

    public PersistentSegmentTree(long[] values) {
        head = SegmentTreeNode.of(values);
    }

    public void toState(int index) {
        if (states.size() <= index || index < 0) {
            head = ZERO_TREE.head;
        } else if (index < states.size() - 1) {
            head = states.get(index);
            states.add(head);
        }
    }

    public long get(int index) {
        return head.get(index);
    }

    public void addToSegment(long value, int l, int r, long x) {
        var newState = head.addToSegment(value, l, r);
        head = newState;

        if (this.x != x) {
            states.add(newState);
            this.x = x;
        }
    }
}
