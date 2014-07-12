package com.tenjava.entries.instipod.t3;

import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class HashtagLifeCore extends JavaPlugin {
    private static HashtagLifeCore instance;
    private EventRegistrar registrar;
    private FileConfiguration configuration;
    
    @Override
    public void onEnable() {
        instance = this;
        EventListener listener = new EventListener(this);
        
        configuration = getConfig();
        configuration.options().copyDefaults(true);
        saveConfig();
        
        registrar = new EventRegistrar();
        registrar.initEvents();
        
        for (World w : getServer().getWorlds()) {
            debug("World " + w.getName() + " is already storming, starting Bukkit Task.");
            BukkitTask runnable = new WeatherRunnable(w).runTaskTimer(this, getConfigInt("global.start_delay"), getConfigInt("global.frequency"));
        }
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public void log(Level level, String message) {
        getServer().getLogger().log(level, "[" + getDescription().getName() + "] " + message);
    }
    
    public void logEvent(String message) {
        if (getConfigBoolean("log_events")) {
            log(Level.INFO, "[E] " + message);
        }
    }
    
    public void debug(String message) {
        if (isDebug()) {
            log(Level.INFO, "[D] " + message);
        }
    }
    
    public static HashtagLifeCore getInstance() {
        return instance;
    }
    
    public int getConfigInt(String path) {
        return configuration.getInt(path);
    }
    
    public boolean getConfigBoolean(String path) {
        return configuration.getBoolean(path);
    }
    
    public boolean isDebug() {
        return configuration.getBoolean("debug");
    }
}
