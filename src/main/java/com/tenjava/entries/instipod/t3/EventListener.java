package com.tenjava.entries.instipod.t3;

import com.tenjava.entries.instipod.t3.events.LightningRedstoneEvent;
import com.tenjava.entries.instipod.t3.events.WeatherRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitTask;

public class EventListener implements Listener {
    private HashtagLifeCore plugin;
    
    public EventListener(HashtagLifeCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        //lazy way of doing commands here, maybe fix later?
        if (event.getMessage().contains("strikeredstone")) {
            //even lazier
            try {
                (new LightningRedstoneEvent()).call(event.getPlayer());
            } catch (Exception ex) {
                //problem
                ex.printStackTrace();
            }
            event.setCancelled(true);
        }
        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            HashtagLifeCore.getInstance().debug("World " + event.getWorld().getName() + " has started storming, starting Bukkit Task.");
            BukkitTask runnable = new WeatherRunnable(event.getWorld()).runTaskTimer(plugin, HashtagLifeCore.getInstance().getConfigInt("global.start-delay"), HashtagLifeCore.getInstance().getConfigInt("global.try-frequency"));
        }
    }
}