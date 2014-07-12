package com.tenjava.entries.instipod.t3.events;

import com.tenjava.entries.instipod.t3.api.CallablePlayerEvent;
import com.tenjava.entries.instipod.t3.EventsCore;
import com.tenjava.entries.instipod.t3.api.Utils;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LightningRedstoneEvent implements CallablePlayerEvent {
    private static Block struck = null;

    @Override
    public void call(Player p) throws Exception {
        EventsCore plugin = EventsCore.getInstance();
        Location ofplayer = p.getLocation();
        if (ofplayer.getWorld().hasStorm()) {
            ArrayList<Block> blocks = getPossibleBlocks(ofplayer.getBlock(), 25);
            if (blocks.size() > 0) {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": LightningRedstoneEvent for player " + p.getName() + ", found " + blocks.size() + " possible targets.");
                int tostrike = Utils.getRandom(blocks.size());
                Block strike = blocks.get(tostrike);
                ofplayer.getWorld().strikeLightning(strike.getLocation());
                strike.setType(Material.REDSTONE_TORCH_ON);
                struck = strike;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        LightningRedstoneEvent.removeStruck();
                    }
                }.runTaskLater(EventsCore.getInstance(), 20);
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": LightningRedstoneEvent for player " + p.getName() + ", executed.");
            } else {
                EventsCore.getInstance().debug("World " + p.getWorld().getName() + ": LightningRedstoneEvent for player " + p.getName() + ", found 0 possible targets.");
            }
        }
    }
    
    @Override
    public String getEventName() {
        return "LightningRedstoneEvent";
    }
    
    private ArrayList<Block> getPossibleBlocks(Block starting, int radius) {
        ArrayList<Block> output = new ArrayList<Block>();
        int x = 0;
        int y = 0;
        int z = 0;
        while (x <= radius) {
            while (y <= radius) {
                while (z <= radius) {
                    Block b = starting.getRelative(x, y, z);
                    if (b.getType().equals(Material.REDSTONE_WIRE)) {
                        output.add(b);
                    }
                    z++;
                }
                z = 0;
                y++;
            }
            y = 0;
            x++;
        }
        x = 0;
        y = 0;
        z = 0;
        while (x >= (-1 * radius)) {
            while (y >= (-1 * radius)) {
                while (z >= (-1 * radius)) {
                    Block b = starting.getRelative(x, y, z);
                    if (b.getType().equals(Material.REDSTONE_WIRE)) {
                        output.add(b);
                    }
                    z--;
                }
                z = 0;
                y--;
            }
            y = 0;
            x--;
        }
        return output;
    }
    
    public static boolean isBlockStruck(Block b) {
        return b.equals(struck);
    }
    
    public static void removeStruck() {
        int result = Utils.getRandom(1, 100);
        struck.setType(Material.REDSTONE_WIRE);
        if (result >= (100 - EventsCore.getInstance().getConfigInt("lightning_redstone.overload_chance"))) {
            float power = EventsCore.getInstance().getConfigInt("lightning_redstone.overload_power") * 1F;
            struck.getWorld().createExplosion(struck.getLocation().getBlockX(), struck.getLocation().getBlockY(), struck.getLocation().getBlockZ(), power, true, true);
        }
        struck = null;
    }
}