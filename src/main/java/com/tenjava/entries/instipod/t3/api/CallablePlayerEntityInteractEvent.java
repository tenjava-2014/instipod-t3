package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface CallablePlayerEntityInteractEvent {
    public void call(Player p, Entity e) throws Exception;
    public String getEventName();
}