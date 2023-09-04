package com.sagbot.freecube;

import org.bukkit.Chunk;

import java.awt.*;

public abstract class Section {
    private static final int ZONE_SIZE = Freecube.ZONE_SIZE;
    private static final int ROAD_SIZE = Freecube.ROAD_SIZE;

    public static int getSectionSize() {
        return ZONE_SIZE + ROAD_SIZE;
    }

    public static int getMaxCoordChunkSection() {
        return ZONE_SIZE + ROAD_SIZE - 1;
    }

    public static int getMinCoordRoad() {
        return ZONE_SIZE;
    }

    public static Point getSectionCoord(int chunkX, int chunkY) {
        int x = (int) Math.floor((float) chunkX / (float) getSectionSize());
        int y = (int) Math.floor((float) chunkY / (float) getSectionSize());
        return new Point(x, y);
    }

    public static Point getCoordChunkSection(int chunkX, int chunkZ) {
        Point section = getSectionCoord(chunkX, chunkZ);
        int x = section.x * getSectionSize();
        int y = section.y * getSectionSize();
        return new Point(chunkX - x, chunkZ - y);
    }

    public static int getSectionId(int sectionX, int sectionY) {
        if (Math.abs(sectionX) > Math.abs(sectionY)) {
            int alpha = sectionX > 0 ? -3 : 1;
            int x = Math.abs(sectionX);
            int offsetX = 4 * x * x + alpha * x + 1;
            int beta = sectionX > 0 ? 1 : -1;
            return offsetX + beta * sectionY;
        }
        int alpha = sectionY > 0 ? -1 : 3;
        int x = Math.abs(sectionY);
        int offsetY = 4 * x * x + alpha * x + 1;
        int beta = sectionY > 0 ? -1 : 1;
        return offsetY + beta * sectionX;
    }

    private static double getCloser(double[] x) {
        double[] diff = new double[x.length];
        for (int i = 0; i < x.length; ++i) {
            diff[i] = Math.abs(Math.round(x[i]) - x[i]);
        }
        int indMin = 0;
        double min = diff[indMin];
        for (int i = 1; i < diff.length; ++i) {
            if (min > diff[i]) {
                indMin = i;
                min = diff[i];
            }
        }
        return x[indMin];
    }

    public static Point getSectionCoord(int id) {
        if (id == 0) {
            throw new AssertionError();
        }
        int c = 1 - id;
        double[] x = {
                1. / 8. + Math.sqrt(1. / 64. - c / 4.), // +y
                -3. / 8. + Math.sqrt(9 / 64. - c / 4.), // -y
                3. / 8. + Math.sqrt(9 / 64. - c / 4.), //+x
                -1. / 8. + Math.sqrt(1 / 64. - c / 4.), // -x
        };
        double closer = getCloser(x);
        if (closer == x[0]) {
            int rawY = (int) Math.round(closer);
            int fakeX = 4 * rawY * rawY - rawY + 1;
            return new Point(fakeX - id, rawY);
        }
        if (closer == x[1]) {
            int rawY = (int) Math.round(closer);
            int fakeX = 4 * rawY * rawY + 3 * rawY + 1;
            return new Point(id - fakeX, -rawY);
        }
        if (closer == x[2]) {
            int rawX = (int) Math.round(closer);
            int fakeY = 4 * rawX * rawX - 3 * rawX + 1;
            return new Point(rawX,  id - fakeY);
        }
        int rawX = (int) Math.round(closer);
        int fakeY = 4 * rawX * rawX + rawX + 1;
        return new Point(-rawX, fakeY - id);
    }

    public static Point getSpawnPoint(int id) {
        if (id == 0) {
            throw new AssertionError();
        }
        Point p = getSectionCoord(id);
        int x = p.x * getSectionSize() * 16 + ZONE_SIZE * 8;
        int y = p.y * getSectionSize() * 16 + ZONE_SIZE * 8;
        return new Point(x, y);
    }

    public static int getSectionIdByChunk(Chunk c) {
        Point p = getSectionCoord(c.getX(), c.getZ());
        return getSectionId(p.x, p.y);
    }

    public static boolean isRoad(Chunk c) {
        Point p = getCoordChunkSection(c.getX(), c.getZ());
        return p.x >= getMinCoordRoad() || p.y >= getMinCoordRoad();
    }

    public static boolean isInSameSection(Chunk c1, Chunk c2) {
        Point p = getSectionCoord(c1.getX(), c1.getZ());
        return c2.getX() >= p.x * getSectionSize() && c2.getX() <= p.x * getSectionSize() + getSectionSize() - 1 &&
                c2.getZ() >= p.y * getSectionSize() && c2.getZ() <= p.y * getSectionSize() + getSectionSize() - 1;
    }
}
