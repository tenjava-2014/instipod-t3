package com.tenjava.entries.instipod.t3;

import com.tenjava.entries.instipod.t3.events.LightningRedstoneEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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
        }
        
    }
}