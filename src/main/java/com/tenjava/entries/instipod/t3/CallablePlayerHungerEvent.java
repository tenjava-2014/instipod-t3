package com.tenjava.entries.instipod.t3;

import org.bukkit.entity.Player;

public interface CallablePlayerHungerEvent {
    public void call(Player p, int hunger) throws Exception;
    public String getEventName();
}