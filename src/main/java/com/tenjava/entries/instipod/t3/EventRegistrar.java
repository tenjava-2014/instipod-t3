package com.tenjava.entries.instipod.t3;

import com.tenjava.entries.instipod.t3.api.CallablePlayerEntityInteractEvent;
import com.tenjava.entries.instipod.t3.api.CallablePlayerEvent;
import com.tenjava.entries.instipod.t3.api.CallablePlayerHungerEvent;
import com.tenjava.entries.instipod.t3.api.InstiEventListener;
import com.tenjava.entries.instipod.t3.api.Utils;
import com.tenjava.entries.instipod.t3.events.AngryChickenEvent;
import com.tenjava.entries.instipod.t3.events.CatsandDogsEvent;
import com.tenjava.entries.instipod.t3.events.LightningRedstoneEvent;
import com.tenjava.entries.instipod.t3.events.VomitEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EventRegistrar {
    private static EventRegistrar instance;
    private ArrayList<InstiEventListener> listeners = new ArrayList<InstiEventListener>();
    private HashMap<CallablePlayerEvent, Integer> stormEvents = new HashMap<CallablePlayerEvent, Integer>();
    private HashMap<CallablePlayerEntityInteractEvent, Integer> entityEvents = new HashMap<CallablePlayerEntityInteractEvent, Integer>();
    private HashMap<CallablePlayerHungerEvent, Integer> hungerEvents = new HashMap<CallablePlayerHungerEvent, Integer>();
    
    public EventRegistrar() {
        instance = this;
    }
    
    /**
     * Returns the current server instance of the EventRegistrar
     * @return current instance of EventRegistrar
     */
    public static EventRegistrar getInstance() {
        return instance;
    }
    
    /**
     * Internal plugin call to register built-in events
     */
    protected void initEvents() {
        if (EventsCore.getInstance().getConfigBoolean("lightning_redstone.enabled")) 
            registerStormEvent(new LightningRedstoneEvent(), (100 - EventsCore.getInstance().getConfigInt("lightning_redstone.chance")));
        if (EventsCore.getInstance().getConfigBoolean("cats_and_dogs.enabled")) 
            registerStormEvent(new CatsandDogsEvent(), (100 - EventsCore.getInstance().getConfigInt("cats_and_dogs.chance")));
        if (EventsCore.getInstance().getConfigBoolean("angry_chicken.enabled")) 
            registerEntityEvent(new AngryChickenEvent(), (100 - EventsCore.getInstance().getConfigInt("angry_chicken.chance")));
        if (EventsCore.getInstance().getConfigBoolean("vomit.enabled")) 
            registerHungerEvent(new VomitEvent(), (100 - EventsCore.getInstance().getConfigInt("vomit.chance")));
        
        for (InstiEventListener l : listeners) {
            l.initEvents();
        }
    }
    
    /**
     * Registers a storm event with the Registrar
     * @param event the Storm event to register
     * @param chance chance of the event happening
     */
    public void registerStormEvent(CallablePlayerEvent event, int chance) {
        stormEvents.put(event, chance);
        EventsCore.getInstance().logEvent("Event " + event.getEventName() + " has been registered.");
    }
    
    /**
     * Registers an entity event with the Registrar
     * @param event the Entity event to register
     * @param chance chance of the event happening
     */
    public void registerEntityEvent(CallablePlayerEntityInteractEvent event, int chance) {
        entityEvents.put(event, chance);
        EventsCore.getInstance().logEvent("Event " + event.getEventName() + " has been registered.");
    }
    
    /**
     * Registers a hunger event with the Registrar
     * @param event the Hunger event to register
     * @param chance chance of the event happening
     */
    public void registerHungerEvent(CallablePlayerHungerEvent event, int chance) {
        hungerEvents.put(event, chance);
        EventsCore.getInstance().logEvent("Event " + event.getEventName() + " has been registered.");
    }
    
    /**
     * Unregisters or disables a storm event with the Registrar
     * @param event the Storm event to unregister
     */
    public void unregisterStormEvent(CallablePlayerEvent event) {
        stormEvents.remove(event);
        EventsCore.getInstance().logEvent("Event " + event.getEventName() + " has been unregistered.");
    }
    
    /**
     * Unregisters or disables an entity event with the Registrar
     * @param event the Entity event to unregister
     */
    public void unregisterEntityEvent(CallablePlayerEntityInteractEvent event) {
        entityEvents.remove(event);
        EventsCore.getInstance().logEvent("Event " + event.getEventName() + " has been unregistered.");
    }
    
    /**
     * Unregisters or disables a hunger event with the Registrar
     * @param event the Hunger event to unregister
     */
    public void unregisterStormEvent(CallablePlayerHungerEvent event) {
        hungerEvents.remove(event);
        EventsCore.getInstance().logEvent("Event " + event.getEventName() + " has been unregistered.");
    }
    
    /**
     * Calls the provided storm event to start
     * @param event the Storm event to start
     * @param p the player to provide
     */
    public void callStormEvent(CallablePlayerEvent event, Player p) {
        try {
            event.call(p);
            for (InstiEventListener l : listeners) {
                l.eventOccured(event, event.getEventName());
            }
        } catch (Exception ex) {
            EventsCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
    
    /**
     * Calls the provided hunger event to start
     * @param event the Hunger event to start
     * @param p the player to provide
     * @param hunger the hunger level to provide
     */
    public void callHungerEvent(CallablePlayerHungerEvent event, Player p, int hunger) {
        try {
            event.call(p, hunger);
            for (InstiEventListener l : listeners) {
                l.eventOccured(event, event.getEventName());
            }
        } catch (Exception ex) {
            EventsCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
    
    /**
     * Calls the provided entity event to start
     * @param event the Entity event to start
     * @param p the player to provide
     * @param e the entity to provide
     */
    public void callEntityEvent(CallablePlayerEntityInteractEvent event, Player p, Entity e) {
        try {
            event.call(p, e);
            for (InstiEventListener l : listeners) {
                l.eventOccured(event, event.getEventName());
            }
        } catch (Exception ex) {
            EventsCore.getInstance().log(Level.WARNING, "Failed to execute event callable: " + event.toString() + "!");
        }
    }
    
    /**
     * Calculates chances and calls storm events that should occur
     * @param p the player to provide to events
     */
    public void doStormEvents(Player p) {
        if (p.getWorld().hasStorm()) {
            for (CallablePlayerEvent event : stormEvents.keySet()) {
                int chance = Utils.getRandom(1, 100);
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
    
    /**
     * Calculates chances and calls hunger events that should occur
     * @param p the player to provide to events
     * @param hunger the hunger level to provide to events
     */
    public void doHungerEvents(Player p, int hunger) {
        for (CallablePlayerHungerEvent event : hungerEvents.keySet()) {
            int chance = Utils.getRandom(1, 100);
            if (chance >= hungerEvents.get(event)) {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Executing event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + hungerEvents.get(event) + ".");
                EventsCore.getInstance().logEvent("Player " + p.getName() + " in world " + p.getWorld().getName() + ", executing event " + event.getEventName() + ".");
                callHungerEvent(event, p, hunger);
            } else {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Skipping event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + hungerEvents.get(event) + ".");
            }
        }
    }
    
    /**
     * Calculates chances and calls entity events that should occur
     * @param p the player to provide to events
     * @param e the entity to provide to events
     */
    public void doEntityEvents(Player p, Entity e) {
        for (CallablePlayerEntityInteractEvent event : entityEvents.keySet()) {
            int chance = Utils.getRandom(1, 100);
            if (chance >= entityEvents.get(event)) {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Executing event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + entityEvents.get(event) + ".");
                EventsCore.getInstance().logEvent("Player " + p.getName() + " in world " + p.getWorld().getName() + ", executing event " + event.getEventName() + ".");
                callEntityEvent(event, p, e);
            } else {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": Skipping event " + event.getEventName() + " on player " + p.getName() + ", result " + chance + " needed " + entityEvents.get(event) + ".");
            }
        }
    }
    
    /**
     * Registers an InstiEventListener to receive events
     * @param l the listener to register
     */
    public void registerListener(InstiEventListener l) {
        listeners.add(l);
        l.initEvents();
    }
    
    /**
     * Unregisters an InstiEventListener to stop receiving events
     * @param l the listener to unregister
     */
    public void unregisterListener(InstiEventListener l) {
        listeners.remove(l);
    }
}