package com.kyo.tinyredstoneforge.signal;

import java.util.ArrayList;
import java.util.List;

public class SignalGraph {
    private final List<SignalNode> nodes = new ArrayList<>();
    private final List<SignalEdge> edges = new ArrayList<>();

    public SignalNode addNode(String id) {
        SignalNode node = new SignalNode(id);
        nodes.add(node);
        return node;
    }

    public void connect(SignalNode from, SignalNode to) {
        edges.add(new SignalEdge(from, to));
    }

    public List<SignalNode> nodes() {
        return nodes;
    }

    public List<SignalEdge> edges() {
        return edges;
    }

    public void clear() {
        nodes.clear();
        edges.clear();
    }
}