package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface CallablePlayerEntityInteractEvent extends CallableEvent {
    public void call(Player p, Entity e) throws Exception;
    @Override
    public String getEventName();
}