package com.tenjava.entries.instipod.t3.events;

import com.tenjava.entries.instipod.t3.CallablePlayerEvent;
import com.tenjava.entries.instipod.t3.HashtagLifeCore;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LightningRedstoneEvent implements CallablePlayerEvent {

    @Override
    public void call(Player p) throws Exception {
        HashtagLifeCore plugin = HashtagLifeCore.getInstance();
        Location ofplayer = p.getLocation();
        if (ofplayer.getWorld().hasStorm()) {
            ArrayList<Block> blocks = getPossibleBlocks(ofplayer.getBlock(), 25);
            blocks.addAll(getPossibleBlocks(ofplayer.getBlock(), -25));
            if (blocks.size() > 0) {
                Random random = new Random();
                int tostrike = random.nextInt(blocks.size());
                p.sendMessage("debug//" + blocks.size() + " possible targets found");
                ofplayer.getWorld().strikeLightning(blocks.get(tostrike).getLocation());
            } else {
                p.sendMessage("debug// no targets found");
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
                    System.out.println("debug // Found block " + x + "," + y + "," + z + ": " + b.getType().name());
                    if (b.getType().equals(Material.REDSTONE_WIRE) || b.getType().equals(Material.REDSTONE) || b.getType().equals(Material.REDSTONE_BLOCK)) {
                        output.add(b);
                    }
                    z++;
                }
                y++;
            }
            x++;
        }
        return output;
    }
    
}