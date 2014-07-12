package com.tenjava.entries.instipod.t3.api;

public interface InstiEventListener {
    public void initEvents();
    public void eventOccured(CallableEvent event, String name);
}