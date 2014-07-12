package com.tenjava.entries.instipod.t3.events;

import com.tenjava.entries.instipod.t3.CallablePlayerEntityInteractEvent;
import com.tenjava.entries.instipod.t3.HashtagLifeCore;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AngryChickenEvent implements CallablePlayerEntityInteractEvent {

    @Override
    public void call(Player p, Entity e) throws Exception {
        if (e instanceof Chicken) {
            HashtagLifeCore.getInstance().debug("AngryChickenAttackTask has started with target " + p.getName() + ".");
            BukkitTask runnable = new AngryChickenAttackTask(p, e).runTaskTimer(HashtagLifeCore.getInstance(), 0, 20);
        }
    }

    @Override
    public String getEventName() {
        return "AngryChickenEvent";
    }

}
class AngryChickenAttackTask extends BukkitRunnable {
    private Player p;
    private Entity e;
    private long startTime = 0;
    
    public AngryChickenAttackTask(Player p, Entity e) {
        this.p = p;
        this.e = e;
    }
    
    @Override
    public void run() {
        if (startTime == 0) startTime = System.currentTimeMillis();
        long finishTime = startTime + (HashtagLifeCore.getInstance().getConfigInt("angry_chicken.event_length") * 1000);
        if ((finishTime - System.currentTimeMillis()) > 0) {
            e.teleport(p.getLocation().add(0, HashtagLifeCore.getInstance().getConfigInt("angry_chicken.bomb_height"), 0));
            e.getLocation().add(0, -2, 0).getBlock().setType(Material.ANVIL);
        } else {
            HashtagLifeCore.getInstance().debug("AngryChickenAttackTask has finished.");
            this.cancel();
        }
    }
    
}