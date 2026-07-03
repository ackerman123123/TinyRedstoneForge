package com.kyo.tinyredstoneforge.client;

public final class ClientBootstrap {

    private ClientBootstrap() {
    }

    public static void initialize() {
        ClientRegistry.register();
        ClientEvents.register();
    }
}