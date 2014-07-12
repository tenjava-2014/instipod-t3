package com.tenjava.entries.instipod.t3;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class HashtagLifeCore extends JavaPlugin {
    private static HashtagLifeCore instance;
    
    @Override
    public void onEnable() {
        instance = this;
        EventListener listener = new EventListener(this);
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public void log(Level level, String message) {
        getServer().getLogger().log(level, message);
    }
    
    public static HashtagLifeCore getInstance() {
        return instance;
    }
}
