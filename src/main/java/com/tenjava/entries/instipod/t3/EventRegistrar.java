package com.tenjava.entries.instipod.t3;

import com.tenjava.entries.instipod.t3.api.CallablePlayerHungerEvent;
import com.tenjava.entries.instipod.t3.api.CallablePlayerEvent;
import com.tenjava.entries.instipod.t3.api.CallablePlayerEntityInteractEvent;
import com.tenjava.entries.instipod.t3.events.AngryChickenEvent;
import com.tenjava.entries.instipod.t3.events.CatsandDogsEvent;
import com.tenjava.entries.instipod.t3.events.LightningRedstoneEvent;
import com.tenjava.entries.instipod.t3.events.VomitEvent;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EventRegistrar {
    private static EventRegistrar instance;
    private HashMap<CallablePlayerEvent, Integer> stormEvents = new HashMap<CallablePlayerEvent, Integer>();
    private HashMap<CallablePlayerEntityInteractEvent, Integer> entityEvents = new HashMap<CallablePlayerEntityInteractEvent, Integer>();
    private HashMap<CallablePlayerHungerEvent, Integer> hungerEvents = new HashMap<CallablePlayerHungerEvent, Integer>();
    
    public EventRegistrar() {
        instance = this;
    }
    
    public static EventRegistrar getInstance() {
        return instance;
    }
    
    public void initEvents() {
        if (EventsCore.getInstance().getConfigBoolean("lightning_redstone.enabled")) 
            stormEvents.put(new LightningRedstoneEvent(), (100 - EventsCore.getInstance().getConfigInt("lightning_redstone.chance")));
        if (EventsCore.getInstance().getConfigBoolean("cats_and_dogs.enabled")) 
            stormEvents.put(new CatsandDogsEvent(), (100 - EventsCore.getInstance().getConfigInt("cats_and_dogs.chance")));
        if (EventsCore.getInstance().getConfigBoolean("angry_chicken.enabled")) 
            entityEvents.put(new AngryChickenEvent(), (100 - EventsCore.getInstance().getConfigInt("angry_chicken.chance")));
        if (EventsCore.getInstance().getConfigBoolean("vomit.enabled")) 
            hungerEvents.put(new VomitEvent(), (100 - EventsCore.getInstance().getConfigInt("vomit.chance")));
    }
    
    public void callStormEvent(CallablePlayerEvent event, Player p) {
        try {
            event.call(p);
        } catch (Exception ex) {
            EventsCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
    
    public void callHungerEvent(CallablePlayerHungerEvent event, Player p, int hunger) {
        try {
            event.call(p, hunger);
        } catch (Exception ex) {
            EventsCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
    
    public void callEntityEvent(CallablePlayerEntityInteractEvent event, Player p, Entity e) {
        try {
            event.call(p, e);
        } catch (Exception ex) {
            EventsCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
    
    public void doStormEvents(Player p) {
        if (p.getWorld().hasStorm()) {
            Random random = new Random();
            for (CallablePlayerEvent event : stormEvents.keySet()) {
                int chance = random.nextInt(100) + 1;
                if (chance >= stormEvents.get(event)) {
                    EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Executing event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + stormEvents.get(event) + ".");
                    EventsCore.getInstance().logEvent("Player " + p.getName() + " in world " + p.getWorld().getName() + ", executing event " + event.getEventName() + ".");
                    callStormEvent(event, p);
                } else {
                    EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Skipping event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + stormEvents.get(event) + ".");
                }
            }
        }
    }
    
    public void doHungerEvents(Player p, int hunger) {
        Random random = new Random();
        for (CallablePlayerHungerEvent event : hungerEvents.keySet()) {
            int chance = random.nextInt(100) + 1;
            if (chance >= hungerEvents.get(event)) {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Executing event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + hungerEvents.get(event) + ".");
                EventsCore.getInstance().logEvent("Player " + p.getName() + " in world " + p.getWorld().getName() + ", executing event " + event.getEventName() + ".");
                callHungerEvent(event, p, hunger);
            } else {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Skipping event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + hungerEvents.get(event) + ".");
            }
        }
    }
    
    public void doEntityEvents(Player p, Entity e) {
        Random random = new Random();
        for (CallablePlayerEntityInteractEvent event : entityEvents.keySet()) {
            int chance = random.nextInt(100) + 1;
            if (chance >= entityEvents.get(event)) {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Executing event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + entityEvents.get(event) + ".");
                EventsCore.getInstance().logEvent("Player " + p.getName() + " in world " + p.getWorld().getName() + ", executing event " + event.getEventName() + ".");
                callEntityEvent(event, p, e);
            } else {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Skipping event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + entityEvents.get(event) + ".");
            }
        }
    }
}