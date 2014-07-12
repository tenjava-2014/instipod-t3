package com.tenjava.entries.instipod.t3;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitTask;

public class EventListener implements Listener {
    private HashtagLifeCore plugin;
    
    public EventListener(HashtagLifeCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            HashtagLifeCore.getInstance().debug("World " + event.getWorld().getName() + " has started storming, starting Bukkit Task.");
            BukkitTask runnable = new WeatherRunnable(event.getWorld()).runTaskTimer(plugin, HashtagLifeCore.getInstance().getConfigInt("global.start_delay"), HashtagLifeCore.getInstance().getConfigInt("global.frequency"));
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player)event.getDamager();
            HashtagLifeCore.getInstance().debug("World " + damager.getWorld().getName() + ": Selected " + damager.getName() + " for random entity events.");
            EventRegistrar.getInstance().doEntityEvents(damager, event.getEntity());
        }
    }
}