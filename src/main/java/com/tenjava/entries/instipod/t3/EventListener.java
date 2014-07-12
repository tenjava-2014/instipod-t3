package com.tenjava.entries.instipod.t3;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
}