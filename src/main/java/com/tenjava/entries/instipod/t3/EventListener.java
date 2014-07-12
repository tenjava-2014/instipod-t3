package com.tenjava.entries.instipod.t3;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitTask;

public class EventListener implements Listener {
    private EventsCore plugin;
    
    public EventListener(EventsCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            EventsCore.getInstance().debug("World " + event.getWorld().getName() + " has started storming, starting Bukkit Task.");
            BukkitTask runnable = new WeatherRunnable(event.getWorld()).runTaskTimer(plugin, EventsCore.getInstance().getConfigInt("global.start_delay"), EventsCore.getInstance().getConfigInt("global.frequency"));
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player)event.getDamager();
            EventsCore.getInstance().debug("World " + damager.getWorld().getName() + ": Selected " + damager.getName() + " for random entity events.");
            EventRegistrar.getInstance().doEntityEvents(damager, event.getEntity());
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player entity = (Player)event.getEntity();
            EventRegistrar.getInstance().doHungerEvents(entity, event.getFoodLevel());
        }
    }
}