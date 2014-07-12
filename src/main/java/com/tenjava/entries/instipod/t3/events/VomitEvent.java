package com.tenjava.entries.instipod.t3.events;

import com.tenjava.entries.instipod.t3.CallablePlayerHungerEvent;
import com.tenjava.entries.instipod.t3.HashtagLifeCore;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class VomitEvent implements CallablePlayerHungerEvent {
    protected static ArrayList<String> players = new ArrayList<String>();
    
    @Override
    public void call(Player p, int hunger) throws Exception {
        if (hunger <= HashtagLifeCore.getInstance().getConfigInt("vomit.hunger_level") && !players.contains(p.getName())) {
            PotionEffect ourweakness = new PotionEffect(PotionEffectType.WEAKNESS, (HashtagLifeCore.getInstance().getConfigInt("vomit.event_length") * 20), HashtagLifeCore.getInstance().getConfigInt("vomit.event_strength"));
            PotionEffect sickness = new PotionEffect(PotionEffectType.HUNGER, (HashtagLifeCore.getInstance().getConfigInt("vomit.event_length") * 20), HashtagLifeCore.getInstance().getConfigInt("vomit.event_strength"));
            p.addPotionEffect(ourweakness);
            p.addPotionEffect(sickness);
            BukkitTask runnable = new VomitAttackTask(p).runTaskTimer(HashtagLifeCore.getInstance(), 0, 3);
            HashtagLifeCore.getInstance().debug("World " + p.getWorld().getName() + ": VomitEvent for player " + p.getName() + ", started task.");
            players.add(p.getName());
        }
    }

    @Override
    public String getEventName() {
        return "VomitEvent";
    }

}
class VomitAttackTask extends BukkitRunnable {
    private Player p;
    private long startTime = 0;
    
    public VomitAttackTask(Player p) {
        this.p = p;
    }
    
    @Override
    public void run() {
        if (startTime == 0) startTime = System.currentTimeMillis();
        long finishTime = startTime + (HashtagLifeCore.getInstance().getConfigInt("vomit.event_length") * 1000);
        if ((finishTime - System.currentTimeMillis()) > 0 && p.getFoodLevel() > HashtagLifeCore.getInstance().getConfigInt("vomit.hunger_level")) {
            Item dropped = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.DIRT, 1));
            dropped.setVelocity(p.getEyeLocation().getDirection().normalize());
        } else {
            HashtagLifeCore.getInstance().debug("World " + p.getWorld().getName() + ": VomitEvent for player " + p.getName() + ", finished task.");
            VomitEvent.players.remove(p.getName());
            this.cancel();
        }
    }
}