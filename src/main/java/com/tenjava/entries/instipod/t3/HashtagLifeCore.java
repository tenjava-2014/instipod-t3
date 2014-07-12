package com.tenjava.entries.instipod.t3;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class HashtagLifeCore extends JavaPlugin {
    private static HashtagLifeCore instance;
    private EventRegistrar registrar;
    
    @Override
    public void onEnable() {
        instance = this;
        EventListener listener = new EventListener(this);
        registrar = new EventRegistrar();
        registrar.initEvents();
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
