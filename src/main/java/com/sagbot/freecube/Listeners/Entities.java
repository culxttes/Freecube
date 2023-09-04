package com.sagbot.freecube.Listeners;

import com.sagbot.freecube.Section;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.projectiles.BlockProjectileSource;
import  org.bukkit.entity.Projectile;

import java.util.List;
import java.util.stream.Collectors;

public class Entities implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Projectile) {
            Projectile p = (Projectile) e.getDamager();
            Chunk arrival = e.getEntity().getLocation().getChunk();
            Chunk departure;
            if (p.getShooter() instanceof Entity) {
                departure = ((Entity) p.getShooter()).getLocation().getChunk();
            } else {
                departure = ((BlockProjectileSource) p.getShooter()).getBlock().getChunk();
            }
            if (Section.isRoad(arrival) || !Section.isInSameSection(departure, arrival)) {
                e.setCancelled(true);
            }
            return;
        }
        Entity p = e.getDamager();
        if (p.isOp()) {
            return;
        }
        Chunk arrival = e.getEntity().getLocation().getChunk();
        if (Section.isRoad(arrival) || !Section.isInSameSection(p.getLocation().getChunk(), arrival) || false) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplodeEvent (EntityExplodeEvent e) {
        e.setCancelled(true);
    }
}
