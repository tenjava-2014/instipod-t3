package com.tenjava.entries.instipod.t3;

import com.tenjava.entries.instipod.t3.events.WeatherRunnable;
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
        registrar = new EventRegistrar();
        registrar.initEvents();
        
        configuration = getConfig();
        
        for (World w : getServer().getWorlds()) {
            debug("World " + w.getName() + " is already storming, starting Bukkit Task.");
            BukkitTask runnable = new WeatherRunnable(w).runTaskTimer(this, getConfigInt("lightningredstone.start-delay"), getConfigInt("lightningredstone.try-frequency"));
        }
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public void log(Level level, String message) {
        getServer().getLogger().log(level, "[" + getDescription().getName() + "] " + message);
    }
    
    public void debug(String message) {
        if (isDebug()) {
            log(Level.INFO, message);
        }
    }
    
    public static HashtagLifeCore getInstance() {
        return instance;
    }
    
    public int getConfigInt(String path) {
        return configuration.getInt(path);
    }
    
    public boolean isDebug() {
        return configuration.getBoolean("debug");
    }
}
