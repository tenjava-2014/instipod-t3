package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Player;

public interface CallablePlayerEvent extends CallableEvent {
    public void call(Player p) throws Exception;
    @Override
    public String getEventName();
}