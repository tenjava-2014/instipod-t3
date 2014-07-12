package com.tenjava.entries.instipod.t3.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface CallablePlayerEntityInteractEvent extends CallableEvent {
    /**
     * Executed to call the event into action
     * @param p the player to execute on
     * @param e the entity who the player damaged
     * @throws java.lang.Exception
     */
    public void call(Player p, Entity e) throws Exception;
    /**
     * Returns the event's plaintext name
     * @return the event name
     */
    @Override
    public String getEventName();
}