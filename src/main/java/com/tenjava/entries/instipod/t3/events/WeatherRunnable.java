package com.tenjava.entries.instipod.t3.events;

import com.tenjava.entries.instipod.t3.EventRegistrar;
import java.util.Random;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WeatherRunnable extends BukkitRunnable {
    private World world;
    
    public WeatherRunnable(World world) {
        this.world = world;
    }
    
    @Override
    public void run() {
        System.out.println("debug//runnable executing");
        if (world.hasStorm()) {
            Random random = new Random();
            int luckyplayer = random.nextInt(world.getPlayers().size());
            Player tostrike = world.getPlayers().get(luckyplayer);
            EventRegistrar.getInstance().doStormEvents(tostrike);
        } else {
            this.cancel();
        }
    }
}