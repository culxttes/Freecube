package com.sagbot.freecube.Generator;

import com.sagbot.freecube.Section;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.awt.*;
import java.util.Random;

public class CustomChunkGenerator extends ChunkGenerator {

    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < 16; ++y) {
                biome.setBiome(x, y, Biome.PLAINS);
            }
        }
        MaterialData stone_slab = new MaterialData(43, (byte) 8);
        Point coord = Section.getCoordChunkSection(chunkX, chunkZ);
        if (coord.x == Section.getMaxCoordChunkSection()) {
                chunk.setRegion(1, 0, 0, 15, 65, 16, Material.STONE);
            if (coord.y == Section.getMaxCoordChunkSection()) {
                chunk.setRegion(0, 0, 0, 1, 65, 1, stone_slab);
                chunk.setRegion(15, 0, 0, 16, 65, 1, stone_slab);
                chunk.setRegion(0, 0, 15, 1, 65, 16, stone_slab);
                chunk.setRegion(15, 0, 15, 16, 65, 16, stone_slab);
                chunk.setRegion(0, 0, 1, 1, 65, 15, Material.STONE);
                chunk.setRegion(15, 0, 1, 16, 65, 15, Material.STONE);
            } else {
                chunk.setRegion(0, 0, 0, 1, 65, 16, stone_slab);
                chunk.setRegion(15, 0, 0, 16, 65, 16, stone_slab);
            }
        } else if (coord.y == Section.getMaxCoordChunkSection()) {
            chunk.setRegion(0, 0, 1, 16, 65, 15, Material.STONE);
            chunk.setRegion(0, 0, 0, 16, 65, 1, stone_slab);
            chunk.setRegion(0, 0, 15, 16, 65, 16, stone_slab);
        } else {
            chunk.setRegion(0, 0, 0, 16, 64, 16, Material.DIRT);
            chunk.setRegion(0, 64, 0, 16, 65, 16, Material.GRASS);
        }
        return chunk;
    }
}
