/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.block.Block
 */
package dev.gether.getconfig.domain;

import java.io.Serializable;
import org.bukkit.block.Block;

public class BlockData
implements Serializable {
    public String blockData;
    private String worldName;
    private int x;
    private int y;
    private int z;

    public BlockData(Block block) {
        this.blockData = block.getBlockData().getAsString();
        this.worldName = block.getWorld().getName();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
    }

    public void setBlockData(String blockData) {
        this.blockData = blockData;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getBlockData() {
        return this.blockData;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
}

