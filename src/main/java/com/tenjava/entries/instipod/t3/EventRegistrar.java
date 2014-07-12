package com.tenjava.entries.instipod.t3;

import java.util.concurrent.Callable;
import java.util.logging.Level;

public class EventRegistrar {
    public void callEvent(Callable event) {
        try {
            event.call();
        } catch (Exception ex) {
            HashtagLifeCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
}