/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Optional
 *  java.util.UUID
 *  lombok.Generated
 *  org.bukkit.plugin.java.JavaPlugin
 */
package dev.gether.getclan.ranking;

import dev.gether.getclan.ranking.PlayerStat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import org.bukkit.plugin.java.JavaPlugin;

public class RankingService {
    private final Map<UUID, PlayerStat> playerStatsMap = new HashMap<>();
    private final List<PlayerStat> ranking = new ArrayList<>();
    private final JavaPlugin plugin;
    private final Object rankingLock = new Object();

    public RankingService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void add(UUID uuid, String name, int value) {
        Object object = this.rankingLock;
        synchronized (object) {
            PlayerStat playerStat = this.playerStatsMap.get(uuid);
            if (playerStat != null) {
                playerStat.setValue(value);
            } else {
                playerStat = new PlayerStat(uuid, name, value);
                this.playerStatsMap.put(uuid, playerStat);
                this.ranking.add(playerStat);
            }
        }
    }

    public void remove(UUID uuid) {
        this.ranking.removeIf(playerStat -> playerStat.getUuid().equals(uuid));
    }

    public Optional<PlayerStat> getByIndex(int index) {
        if (index < 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.ranking.get(index));
    }

    public int getIndexByUUID(UUID uuid) {
        for (int i = 0; i < this.ranking.size(); ++i) {
            if (!this.ranking.get(i).getUuid().equals(uuid)) continue;
            return i + 1;
        }
        return -1;
    }

    public int size() {
        return this.ranking.size();
    }

    @Generated
    public List<PlayerStat> getRanking() {
        return this.ranking;
    }

    @Generated
    public Object getRankingLock() {
        return this.rankingLock;
    }
}
