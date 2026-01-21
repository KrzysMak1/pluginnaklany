/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.HashMap
 *  java.util.Map
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.core;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class AntySystemRank {
    private final String killerIp;
    private final Map<String, Long> cooldowns;

    public AntySystemRank(String killerIp) {
        this.killerIp = killerIp;
        this.cooldowns = new HashMap<>();
    }

    public boolean isPlayerKillable(String victimIp, Player killer) {
        if (victimIp.equals(this.killerIp) && killer.hasPermission("getclan.abuse.bypass")) {
            return true;
        }
        Long cooldownEnd = this.cooldowns.get(victimIp);
        long currentTime = System.currentTimeMillis();
        boolean killable = cooldownEnd == null || currentTime > cooldownEnd;
        return killable;
    }

    public void addCooldown(String victimIp, int cooldownTime) {
        long newCooldownEnd = System.currentTimeMillis() + (long)cooldownTime * 1000L;
        this.cooldowns.put(victimIp, newCooldownEnd);
    }

    public long getRemainingCooldown(String victimIp) {
        Long cooldownEnd = this.cooldowns.get(victimIp);
        if (cooldownEnd == null) {
            return 0L;
        }
        return Math.max((long)0L, (long)(cooldownEnd - System.currentTimeMillis()));
    }
}
