package ru.ns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersistentSegmentTree {
    private SegmentTreeNode head;
    private List<SegmentTreeNode> states = new ArrayList<>();

    PersistentSegmentTree(int[] values) {
        head = SegmentTreeNode.of(values);
        states.add(head);
    }

    public void toState(int index) {
        if (states.size() <= index) {
            throw new IndexOutOfBoundsException("No such state");
        }

        head = states.get(index);
        states.add(head);
    }

    public int currentStateIndex() {
        return states.size() - 1;
    }

    public int amountOfStates() {
        return states.size();
    }

    public List<SegmentTreeNode> getStates() {
        return Collections.unmodifiableList(states);
    }

    public int get(int index) {
        return head.get(index);
    }

    public void addToSegment(int value, int l, int r) {
        var newState = head.addToSegment(value, l, r);
        states.add(newState);
        head = newState;
    }
}
