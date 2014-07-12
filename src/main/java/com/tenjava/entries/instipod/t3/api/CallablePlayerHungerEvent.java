package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Player;

public interface CallablePlayerHungerEvent extends CallableEvent {
    /**
     * Executed to call the event into action
     * @param p the player to execute on
     * @param hunger hunger level of the player at event start
     * @throws java.lang.Exception
     */
    public void call(Player p, int hunger) throws Exception;
    /**
     * Returns the event's plaintext name
     * @return the event name
     */
    @Override
    public String getEventName();
}