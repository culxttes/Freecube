package com.sagbot.freecube.Listeners;

import com.sagbot.freecube.Section;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.material.Dispenser;

import java.util.List;

public class Blocks implements Listener {

    @EventHandler
    public void onBlockFlow(BlockFromToEvent e) {
        Block b = e.getToBlock();
        if (Section.isRoad(b.getChunk())) {
            e.setCancelled(true);
            return;
        }
        Block bOrigin = e.getBlock();
        if (!Section.isInSameSection(bOrigin.getChunk(), b.getChunk())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent e) {
        List<Block> lb = e.getBlocks();
        if (lb.isEmpty()) {
            if (Section.isRoad(e.getBlock().getRelative(e.getDirection()).getChunk())) {
                e.setCancelled(true);
            }
        }
        Block b = lb.get(lb.size() - 1).getRelative(e.getDirection());
        for (Block block : lb) {
            if (Section.isRoad(block.getChunk())) {
                e.setCancelled(true);
                return;
            }
        }
        if (Section.isRoad(b.getChunk())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent e) {
        List<Block> lb = e.getBlocks();
        if (lb.isEmpty()) {
            return;
        }
        for (int i = 0; i < lb.size(); ++i) {
            if (Section.isRoad(lb.get(i).getRelative(e.getDirection(), -1).getChunk())
                    || Section.isRoad(lb.get(i).getRelative(e.getDirection()).getChunk())) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent e) {
        Bukkit.broadcastMessage("BlockDispenseEvent");
        if (e.getBlock().getState().getData() instanceof Dispenser) {
            Dispenser d = (Dispenser) e.getBlock().getState().getData();
            Block b = e.getBlock().getRelative(d.getFacing());
            if (Section.isRoad(b.getChunk())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockEvent(BlockEvent event) {
        Bukkit.broadcastMessage(event.getClass().getName());
    }
}
