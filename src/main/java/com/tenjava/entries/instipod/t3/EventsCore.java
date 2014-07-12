package com.tenjava.entries.instipod.t3;

import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class EventsCore extends JavaPlugin {
    private static EventsCore instance;
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
    
    /**
     * Logs a message to the console with plugin name prefix
     * @param level the severity level of the message
     * @param message the message text
     */
    public void log(Level level, String message) {
        getServer().getLogger().log(level, "[" + getDescription().getName() + "] " + message);
    }
    
    /**
     * Logs a message to the console when log_events is enabled
     * @param message the message text
     */
    public void logEvent(String message) {
        if (getConfigBoolean("log_events")) {
            log(Level.INFO, "[E] " + message);
        }
    }
    
    /**
     * Logs a message to the console when debug is enabled
     * @param message the message text
     */
    public void debug(String message) {
        if (isDebug()) {
            log(Level.INFO, "[D] " + message);
        }
    }
    
    /**
     * Returns the current plugin instance
     * @return plugin instance
     */
    public static EventsCore getInstance() {
        return instance;
    }
    
    /**
     * Returns an integer from the configuration file
     * @param path the path to the entry in the configuration file
     * @return integer from configuration file
     */
    public int getConfigInt(String path) {
        return configuration.getInt(path);
    }
    
    /**
     * Returns a boolean from the configuration file
     * @param path the path to the entry in the configuration file
     * @return boolean from configuration file
     */
    public boolean getConfigBoolean(String path) {
        return configuration.getBoolean(path);
    }
    
    /**
     * Returns if debug mode is enabled
     * @return boolean if debug mode is enabled
     */
    public boolean isDebug() {
        return this.getConfigBoolean("debug");
    }
    
    /**
     * Returns if the player has permission
     * @param p the player to check
     * @param node the permission to check
     * @return boolean if player has permission
     */
    public boolean hasPerm(Player p, String node) {
        if (getConfigBoolean("op_has_all_perms")) {
            if (p.isOp()) {
                return true;
            }
        }
        if (!getConfigBoolean("use_permissions")) {
            return true;
        }
        return p.hasPermission(node);
    }
}
