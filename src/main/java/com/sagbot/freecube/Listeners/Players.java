package com.sagbot.freecube.Listeners;

import com.sagbot.freecube.Freecube;
import com.sagbot.freecube.Section;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.*;

public class Players implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (!e.getFrom().getChunk().equals(e.getTo().getChunk())) {
            updateScoreBord(player, e.getTo().getChunk());
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        updateScoreBord(e.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        updateScoreBord(e.getPlayer(), e.getTo().getChunk());
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) { //|| p.hasPermission(null))
            return;
        }
        Block b = e.getBlock();
        if (Section.isRoad(b.getChunk()) || true) { // check if the player is in the zone otherwise
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) { //|| p.hasPermission(null))
            return;
        }
        Block b = e.getBlock();
        if (Section.isRoad(b.getChunk()) || true) { // check if the player is in the zone otherwise
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) { //|| p.hasPermission(null))
            return;
        }
        Block b = e.getBlockClicked().getRelative(e.getBlockFace());
        if (Section.isRoad(b.getChunk()) || false) { // check if the player is in the zone otherwise
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBucketFillEvent(PlayerBucketFillEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) { //|| p.hasPermission(null))
            return;
        }
        Block b = e.getBlockClicked();
        if (Section.isRoad(b.getChunk()) || false) { // check if the player is in the zone otherwise
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) { //|| p.hasPermission(null))
            return;
        }
        Chunk c = p.getLocation().getChunk();
        if (Section.isRoad(c) || false) { // check if the player is in the zone otherwise
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            if (p.isOp()) { //|| p.hasPermission(null))
                return;
            }
            Chunk c = e.getEntity().getLocation().getChunk();
            if (Section.isRoad(c) || false) { // check if the player is in the zone otherwise
                e.setCancelled(true);
            }
        }
    }

    private static void updateScoreBord(Player e) {
        updateScoreBord(e, e.getLocation().getChunk());
    }

    private static void updateScoreBord(Player e, Chunk c) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("Freecube", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Sag" + ChatColor.WHITE + "" + ChatColor.BOLD + "Craft" + ChatColor.RESET + "" + ChatColor.ITALIC + ".net");
        objective.getScore(" ").setScore(7);
        objective.getScore( ChatColor.GRAY + "Monde: " + ChatColor.AQUA + "" + ChatColor.BOLD + Freecube.WORLD_NAME).setScore(6);
        objective.getScore( "  ").setScore(5);
        objective.getScore("    ").setScore(1);
        if (Section.isRoad(c)) {
            objective.getScore(ChatColor.GRAY + "Zone: " + ChatColor.WHITE + "Route").setScore(4);
        } else {
            objective.getScore(ChatColor.GRAY + "Zone: " + ChatColor.WHITE + Freecube.WORLD_NAME + Section.getSectionIdByChunk(c)).setScore(4);
            objective.getScore("   ").setScore(3);
            objective.getScore(ChatColor.GRAY + "Rang: Invit\u00e9").setScore(2);
        }
        e.setScoreboard(board);
    }
}
