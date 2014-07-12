package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Player;

public interface CallablePlayerEvent {
    public void call(Player p) throws Exception;
    public String getEventName();
}