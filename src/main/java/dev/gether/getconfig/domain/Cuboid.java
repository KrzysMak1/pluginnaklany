/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 */
package dev.gether.getconfig.domain;

import dev.gether.getconfig.domain.BlockData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Cuboid
implements Serializable {
    private String worldName;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;

    public Cuboid(Location loc1, Location loc2) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = world.getName();
        this.minX = Math.min((int)x1, (int)x2);
        this.minY = Math.min((int)y1, (int)y2);
        this.minZ = Math.min((int)z1, (int)z2);
        this.maxX = Math.max((int)x1, (int)x2);
        this.maxY = Math.max((int)y1, (int)y2);
        this.maxZ = Math.max((int)z1, (int)z2);
    }

    public List<BlockData> getBlocks() {
        ArrayList blocks = new ArrayList();
        World world = Bukkit.getWorld((String)this.worldName);
        for (int x = this.minX; x <= this.maxX; ++x) {
            for (int y = this.minY; y <= this.maxY; ++y) {
                for (int z = this.minZ; z <= this.maxZ; ++z) {
                    Block blockAt = world.getBlockAt(x, y, z);
                    BlockData blockData = new BlockData(blockAt);
                    blocks.add((Object)blockData);
                }
            }
        }
        return blocks;
    }

    public void clearCuboid() {
        World world = Bukkit.getWorld((String)this.worldName);
        for (int x = this.minX; x <= this.maxX; ++x) {
            for (int y = this.minY; y <= this.maxY; ++y) {
                for (int z = this.minZ; z <= this.maxZ; ++z) {
                    if (world.getBlockAt(x, y, z).getType() == Material.AIR) continue;
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }
    }

    public List<Block> getBlockCuboidWithoutAIR() {
        World world = Bukkit.getWorld((String)this.worldName);
        ArrayList blocks = new ArrayList();
        for (int x = this.minX; x <= this.maxX; ++x) {
            for (int y = this.minY; y <= this.maxY; ++y) {
                for (int z = this.minZ; z <= this.maxZ; ++z) {
                    Block blockAt = world.getBlockAt(x, y, z);
                    if (blockAt.getType() == Material.AIR) continue;
                    blocks.add((Object)blockAt);
                }
            }
        }
        return blocks;
    }

    public boolean contains(Cuboid cuboid) {
        World world = Bukkit.getWorld((String)this.worldName);
        World cuboidWorld = Bukkit.getWorld((String)cuboid.getWorldName());
        return cuboidWorld.equals((Object)world) && cuboid.getMinX() >= this.minX && cuboid.getMaxX() <= this.maxX && cuboid.getMinY() >= this.minY && cuboid.getMaxY() <= this.maxY && cuboid.getMinZ() >= this.minZ && cuboid.getMaxZ() <= this.maxZ;
    }

    public boolean contains(Location location) {
        World world = Bukkit.getWorld((String)this.worldName);
        if (location.getWorld().equals((Object)world)) {
            return this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
        return false;
    }

    public boolean contains(int x, int y, int z) {
        return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ;
    }

    public boolean overlaps(Cuboid cuboid) {
        World world = Bukkit.getWorld((String)this.worldName);
        World cuboidWorld = Bukkit.getWorld((String)cuboid.getWorldName());
        if (!cuboidWorld.equals((Object)world)) {
            return false;
        }
        boolean overlaps = cuboid.getMinX() <= this.maxX && cuboid.getMaxX() >= this.minX && cuboid.getMinY() <= this.maxY && cuboid.getMaxY() >= this.minY && cuboid.getMinZ() <= this.maxZ && cuboid.getMaxZ() >= this.minZ;
        return overlaps;
    }

    public Cuboid(Location center, int radius, int up, int down) {
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();
        int x1 = centerX - radius;
        int x2 = centerX + radius;
        int y1 = centerY - down;
        int y2 = centerY + up;
        int z1 = centerZ - radius;
        int z2 = centerZ + radius;
        this.worldName = center.getWorld().getName();
        this.minX = Math.min((int)x1, (int)x2);
        this.minY = Math.min((int)y1, (int)y2);
        this.minZ = Math.min((int)z1, (int)z2);
        this.maxX = Math.max((int)x1, (int)x2);
        this.maxY = Math.max((int)y1, (int)y2);
        this.maxZ = Math.max((int)z1, (int)z2);
    }

    public Location getCenter() {
        World world = Bukkit.getWorld((String)this.worldName);
        return new Location(world, (double)(this.minX / 2), (double)(this.maxY / 2), (double)(this.maxZ / 2));
    }

    public Location getHighestCenterBlock() {
        World world = Bukkit.getWorld((String)this.worldName);
        int centerX = (this.minX + this.maxX) / 2;
        int centerZ = (this.minZ + this.maxZ) / 2;
        Block highestBlockAt = world.getHighestBlockAt(centerX, centerZ);
        return highestBlockAt.getLocation().clone().add(0.0, 1.0, 0.0);
    }

    public void updateCuboid(int radius, int up, int down) {
        int centerX = (this.minX + this.maxX) / 2;
        int centerZ = (this.minZ + this.maxZ) / 2;
        int x1 = centerX - radius;
        int x2 = centerX + radius;
        int z1 = centerZ - radius;
        int z2 = centerZ + radius;
        this.minX = Math.min((int)x1, (int)x2);
        this.minZ = Math.min((int)z1, (int)z2);
        this.maxX = Math.max((int)x1, (int)x2);
        this.maxZ = Math.max((int)z1, (int)z2);
    }

    public boolean equals(Object obj) {
        World otherWorld;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Cuboid)) {
            return false;
        }
        Cuboid other = (Cuboid)obj;
        World world = Bukkit.getWorld((String)this.worldName);
        return world.equals((Object)(otherWorld = Bukkit.getWorld((String)other.getWorldName()))) && this.minX == other.minX && this.minY == other.minY && this.minZ == other.minZ && this.maxX == other.maxX && this.maxY == other.maxY && this.maxZ == other.maxZ;
    }

    public String toString() {
        return "Cuboid[world:" + this.worldName + ", minX:" + this.minX + ", minY:" + this.minY + ", minZ:" + this.minZ + ", maxX:" + this.maxX + ", maxY:" + this.maxY + ", maxZ:" + this.maxZ + "]";
    }

    public String getWorldName() {
        return this.worldName;
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public int getMinZ() {
        return this.minZ;
    }

    public int getMaxZ() {
        return this.maxZ;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void setMinZ(int minZ) {
        this.minZ = minZ;
    }

    public void setMaxZ(int maxZ) {
        this.maxZ = maxZ;
    }
}

