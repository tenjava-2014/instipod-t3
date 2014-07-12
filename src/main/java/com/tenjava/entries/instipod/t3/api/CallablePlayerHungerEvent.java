package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Player;

public interface CallablePlayerHungerEvent extends CallableEvent {
    public void call(Player p, int hunger) throws Exception;
    @Override
    public String getEventName();
}