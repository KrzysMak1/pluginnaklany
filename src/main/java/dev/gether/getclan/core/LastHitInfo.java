/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.UUID
 *  lombok.Generated
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getclan.core;

import java.util.UUID;
import lombok.Generated;
import org.bukkit.inventory.ItemStack;

public class LastHitInfo {
    private UUID attackerUUID;
    private long expirationTime;
    private ItemStack itemStack;

    public LastHitInfo(UUID attackerUUID, long expirationTime, ItemStack itemStack) {
        this.attackerUUID = attackerUUID;
        this.expirationTime = expirationTime;
        this.itemStack = itemStack;
    }

    @Generated
    public UUID getAttackerUUID() {
        return this.attackerUUID;
    }

    @Generated
    public long getExpirationTime() {
        return this.expirationTime;
    }

    @Generated
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Generated
    public void setAttackerUUID(UUID attackerUUID) {
        this.attackerUUID = attackerUUID;
    }

    @Generated
    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Generated
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}

