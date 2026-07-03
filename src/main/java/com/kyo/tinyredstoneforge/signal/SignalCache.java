package com.kyo.tinyredstoneforge.signal;

import java.util.HashMap;
import java.util.Map;

public class SignalCache {
    private final Map<String, Integer> values = new HashMap<>();

    public int get(String nodeId) {
        return values.getOrDefault(nodeId, 0);
    }

    public void set(String nodeId, int value) {
        values.put(nodeId, Math.max(0, Math.min(15, value)));
    }

    public void clear() {
        values.clear();
    }
}