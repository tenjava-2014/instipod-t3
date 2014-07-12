package com.tenjava.entries.instipod.t3.events;

import com.tenjava.entries.instipod.t3.api.CallablePlayerEvent;
import com.tenjava.entries.instipod.t3.EventsCore;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CatsandDogsEvent implements CallablePlayerEvent {

    @Override
    public void call(Player p) throws Exception {
        Location ofp = p.getLocation();
        if (ofp.getWorld().hasStorm()) {
            BukkitTask runnable = new CatsandDogsTask(p).runTaskTimer(EventsCore.getInstance(), 0, 20);
            EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": CatsandDogsEvent for player " + p.getName() + ", started task.");
        }
    }

    @Override
    public String getEventName() {
        return "CatsandDogsEvent";
    }

}
class CatsandDogsTask extends BukkitRunnable {
    private long startTime = 0;
    private Player player;
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    
    public CatsandDogsTask(Player p) {
        this.player = p;
    }
    
    @Override
    public void run() {
        if (startTime == 0) startTime = System.currentTimeMillis();
        long finishTime = startTime + (EventsCore.getInstance().getConfigInt("cats_and_dogs.event_length") * 1000);
        if ((finishTime - System.currentTimeMillis()) > 0 && player.getWorld().hasStorm()) {
            Location ofp = player.getLocation();
            ofp.add(0, EventsCore.getInstance().getConfigInt("cats_and_dogs.spawn_height"), 2);
            entities.add(ofp.getWorld().spawnEntity(ofp, EntityType.WOLF));
            ofp.add(0, 0, -4);
            entities.add(ofp.getWorld().spawnEntity(ofp, EntityType.OCELOT));
            ofp.add(2, 0, 2);
            entities.add(ofp.getWorld().spawnEntity(ofp, EntityType.WOLF));
            ofp.add(-4, 0, 0);
            entities.add(ofp.getWorld().spawnEntity(ofp, EntityType.OCELOT));
        } else {
            this.cancel();
            EventsCore.getInstance().debug("World " + player.getWorld().getName() + ": CatsandDogsEvent for player " + player.getName() + ", finshed task.");
            for (Entity e : entities) {
                try {
                    e.remove();
                } catch (Exception ex) { }
            }
            entities.clear();
        }
    }
    
}