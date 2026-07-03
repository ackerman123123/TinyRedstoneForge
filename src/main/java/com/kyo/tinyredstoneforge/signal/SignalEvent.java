package com.kyo.tinyredstoneforge.signal;

public record SignalEvent(String nodeId, int oldSignal, int newSignal) {
}