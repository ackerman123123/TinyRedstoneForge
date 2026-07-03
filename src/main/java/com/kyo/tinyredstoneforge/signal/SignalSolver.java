package com.kyo.tinyredstoneforge.signal;

public class SignalSolver {
    public void solve(SignalGraph graph) {
        for (SignalEdge edge : graph.edges()) {
            int nextSignal = Math.max(edge.to().signal(), edge.from().signal() - 1);
            edge.to().setSignal(nextSignal);
        }
    }
}