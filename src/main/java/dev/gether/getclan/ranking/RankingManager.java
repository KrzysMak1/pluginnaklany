/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.Optional
 *  java.util.UUID
 */
package dev.gether.getclan.ranking;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.ranking.PlayerStat;
import dev.gether.getclan.ranking.RankType;
import dev.gether.getclan.ranking.RankingService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class RankingManager {
    private HashMap<RankType, RankingService> ranking = new HashMap<>();
    private final ClanManager clanManager;

    public RankingManager(ClanManager clanManager, GetClan getClan) {
        this.clanManager = clanManager;
        for (RankType rankType : RankType.values()) {
            this.ranking.put(rankType, new RankingService(getClan));
        }
    }

    public void updateUser(User user) {
        if (user == null) {
            return;
        }
        this.update(RankType.KILLS, user.getUuid(), user.getName(), user.getKills());
        this.update(RankType.DEATHS, user.getUuid(), user.getName(), user.getDeath());
        this.update(RankType.USER_POINTS, user.getUuid(), user.getName(), user.getPoints());
        if (!user.hasClan()) {
            return;
        }
        Clan clan = this.clanManager.getClan(user.getTag());
        this.addClan(clan);
    }

    public void sort() {
        this.ranking.values().forEach(rankingService -> {
            Object object = rankingService.getRankingLock();
            synchronized (object) {
                Collections.sort(rankingService.getRanking());
                Collections.reverse(rankingService.getRanking());
            }
        });
    }

    public Optional<PlayerStat> findTopPlayerByIndex(RankType rankType, int index) {
        RankingService rankingService = this.ranking.get(rankType);
        if (rankingService.size() <= index) {
            return Optional.empty();
        }
        return rankingService.getByIndex(index);
    }

    private void update(RankType rankType, UUID uuid, String name, int value) {
        RankingService rankingService = this.ranking.get(rankType);
        rankingService.add(uuid, name, value);
    }

    public void updateAll(Collection<User> users) {
        new ArrayList<>(users).forEach(this::updateUser);
    }

    public int findTopPlayerByName(User user) {
        RankingService rankingService = this.ranking.get(RankType.USER_POINTS);
        return rankingService.getIndexByUUID(user.getUuid());
    }

    public int findTopClan(Clan clan) {
        RankingService rankingService = this.ranking.get(RankType.CLAN_POINTS);
        return rankingService.getIndexByUUID(clan.getUuid());
    }

    public void removeClan(Clan clan) {
        RankingService rankingService = this.ranking.get(RankType.CLAN_POINTS);
        rankingService.remove(clan.getUuid());
    }

    public void addClan(Clan clan) {
        String averagePoint = this.clanManager.getAveragePoint(clan);
        this.update(RankType.CLAN_POINTS, clan.getUuid(), clan.getTag(), Integer.parseInt(averagePoint));
    }
}
