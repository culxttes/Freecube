package com.sagbot.freecube;

import com.sagbot.freecube.Commandes.CommandFreecube;
import com.sagbot.freecube.Generator.CustomChunkGenerator;
import com.sagbot.freecube.Listeners.Blocks;
import com.sagbot.freecube.Listeners.Entities;
import com.sagbot.freecube.Listeners.Worlds;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import com.sagbot.freecube.Listeners.Players;

public final class Freecube extends JavaPlugin {

    public static final int ZONE_SIZE = 7;
    public static final int ROAD_SIZE = 1;
    public static final String WORLD_NAME = "A";

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Players(), this);
        getServer().getPluginManager().registerEvents(new Blocks(), this);
        getServer().getPluginManager().registerEvents(new Entities(), this);
        getServer().getPluginManager().registerEvents(new Worlds(), this);
        this.getCommand("fc").setExecutor(new CommandFreecube());
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new CustomChunkGenerator();
    }


}
