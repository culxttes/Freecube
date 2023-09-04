package com.sagbot.freecube.Listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class Worlds implements Listener {

    @EventHandler
    public void onWorldInitEvent(WorldInitEvent e) {
        World world = e.getWorld();
        world.setStorm(false);
        world.setWeatherDuration(0);
        world.setThunderDuration(0);
        world.setSpawnFlags(false, false);
        world.setTime(6000);
        world.setGameRuleValue("doDaylightCycle", Boolean.toString(false));
    }
}
