package com.tenjava.entries.instipod.t3.events;

import com.tenjava.entries.instipod.t3.CallablePlayerEvent;
import com.tenjava.entries.instipod.t3.HashtagLifeCore;
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
        HashtagLifeCore plugin = HashtagLifeCore.getInstance();
        Location ofplayer = p.getLocation();
        if (ofplayer.getWorld().hasStorm()) {
            ArrayList<Block> blocks = getPossibleBlocks(ofplayer.getBlock(), 25);
            if (blocks.size() > 0) {
                HashtagLifeCore.getInstance().debug("World " + p.getWorld().getName() + ": LightningRedstoneEvent for player " + p.getName() + ", found " + blocks.size() + " possible targets.");
                Random random = new Random();
                int tostrike = random.nextInt(blocks.size());
                Block strike = blocks.get(tostrike);
                ofplayer.getWorld().strikeLightning(strike.getLocation());
                strike.setType(Material.REDSTONE_TORCH_ON);
                struck = strike;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        LightningRedstoneEvent.removeStruck();
                    }
                }.runTaskLater(HashtagLifeCore.getInstance(), 20);
                HashtagLifeCore.getInstance().debug("World " + p.getWorld().getName() + ": LightningRedstoneEvent for player " + p.getName() + ", executed.");
            } else {
                HashtagLifeCore.getInstance().debug("World " + p.getWorld().getName() + ": LightningRedstoneEvent for player " + p.getName() + ", found 0 possible targets.");
            }
        }
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
        struck.setType(Material.REDSTONE_WIRE);
        struck = null;
    }
}