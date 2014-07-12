package com.tenjava.entries.instipod.t3;

import com.tenjava.entries.instipod.t3.events.CatsandDogsEvent;
import com.tenjava.entries.instipod.t3.events.LightningRedstoneEvent;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.entity.Player;

public class EventRegistrar {
    private static EventRegistrar instance;
    private HashMap<CallablePlayerEvent, Integer> stormEvents = new HashMap<CallablePlayerEvent, Integer>();
    
    public EventRegistrar() {
        instance = this;
    }
    
    public static EventRegistrar getInstance() {
        return instance;
    }
    
    public void initEvents() {
        if (HashtagLifeCore.getInstance().getConfigBoolean("lightning_redstone.enabled")) 
            stormEvents.put(new LightningRedstoneEvent(), (100 - HashtagLifeCore.getInstance().getConfigInt("lightning_redstone.chance")));
        if (HashtagLifeCore.getInstance().getConfigBoolean("cats_and_dogs.enabled")) 
            stormEvents.put(new CatsandDogsEvent(), (100 - HashtagLifeCore.getInstance().getConfigInt("cats_and_dogs.chance")));
    }
    
    public void callEvent(CallablePlayerEvent event, Player p) {
        try {
            event.call(p);
        } catch (Exception ex) {
            HashtagLifeCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
    
    public void doStormEvents(Player p) {
        if (p.getWorld().hasStorm()) {
            Random random = new Random();
            for (CallablePlayerEvent event : stormEvents.keySet()) {
                int chance = random.nextInt(100) + 1;
                if (chance >= stormEvents.get(event)) {
                    HashtagLifeCore.getInstance().debug("World " + p.getWorld().getName() + ": Executing event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + stormEvents.get(event) + ".");
                    HashtagLifeCore.getInstance().logEvent("Player " + p.getName() + " in world " + p.getWorld().getName() + ", executing event " + event.getEventName() + ".");
                    callEvent(event, p);
                } else {
                    HashtagLifeCore.getInstance().debug("World " + p.getWorld().getName() + ": Skipping event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + stormEvents.get(event) + ".");
                }
            }
        }
    }
}