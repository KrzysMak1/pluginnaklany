/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.System
 *  java.util.HashMap
 *  java.util.UUID
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.core;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class CooldownManager {
    private HashMap<UUID, Long> cooldown = new HashMap<>();

    public boolean hasCooldown(Player player) {
        Long timeMS = this.cooldown.get(player.getUniqueId());
        return timeMS != null && timeMS >= System.currentTimeMillis();
    }

    public void addCooldown(Player player) {
        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 1000L);
    }

    public void delPlayerFromCooldown(Player player) {
        this.cooldown.remove(player.getUniqueId());
    }
}
