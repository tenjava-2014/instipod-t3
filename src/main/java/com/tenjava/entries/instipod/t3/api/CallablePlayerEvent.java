package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Player;

public interface CallablePlayerEvent extends CallableEvent {
    /**
     * Executed to call the event into action
     * @param p the player to execute on
     * @throws java.lang.Exception
     */
    public void call(Player p) throws Exception;
    /**
     * Returns the event's plaintext name
     * @return the event name
     */
    @Override
    public String getEventName();
}