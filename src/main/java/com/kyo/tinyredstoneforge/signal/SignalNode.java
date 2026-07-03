package com.kyo.tinyredstoneforge.signal;

public class SignalNode {
    private final String id;
    private int signal;

    public SignalNode(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public int signal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = Math.max(0, Math.min(15, signal));
    }
}