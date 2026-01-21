/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Optional
 *  me.clip.placeholderapi.expansion.PlaceholderExpansion
 *  me.clip.placeholderapi.expansion.Relational
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.placeholder;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.ranking.PlayerStat;
import dev.gether.getclan.ranking.RankType;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.Optional;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClanPlaceholder
extends PlaceholderExpansion
implements Relational {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final ClanManager clanManager;

    @NotNull
    public String getIdentifier() {
        return "getclan";
    }

    @NotNull
    public String getAuthor() {
        return "gethertv";
    }

    @NotNull
    public String getVersion() {
        return "1.0";
    }

    public boolean persist() {
        return true;
    }

    public ClanPlaceholder(GetClan plugin, FileManager fileManager, ClanManager clanManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.clanManager = clanManager;
    }

    /*
     * Exception decompiling
     */
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * ob.f0$e
         *     at ob.f0.e(SourceFile:35)
         *     at ob.f0.a(SourceFile:1)
         *     at ob.f0$d.a(SourceFile:68)
         *     at qb.n.i(SourceFile:13)
         *     at qb.e.i(SourceFile:9)
         *     at qb.l.i(SourceFile:14)
         *     at qb.n.i(SourceFile:3)
         *     at ob.f0.g(SourceFile:649)
         *     at ob.f0.d(SourceFile:37)
         *     at ib.f.d(SourceFile:235)
         *     at ib.f.e(SourceFile:7)
         *     at ib.f.c(SourceFile:95)
         *     at rc.f.n(SourceFile:11)
         *     at pc.i.m(SourceFile:5)
         *     at pc.d.K(SourceFile:92)
         *     at pc.d.g0(SourceFile:1)
         *     at fb.b.d(SourceFile:191)
         *     at fb.b.c(SourceFile:145)
         *     at fb.a.a(SourceFile:108)
         *     at com.thesourceofcode.jadec.decompilers.JavaExtractionWorker.decompileWithCFR(SourceFile:76)
         *     at com.thesourceofcode.jadec.decompilers.JavaExtractionWorker.doWork(SourceFile:110)
         *     at com.thesourceofcode.jadec.decompilers.BaseDecompiler.withAttempt(SourceFile:3)
         *     at com.thesourceofcode.jadec.workers.DecompilerWorker.d(SourceFile:53)
         *     at com.thesourceofcode.jadec.workers.DecompilerWorker.b(SourceFile:1)
         *     at e7.a.run(SourceFile:1)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1154)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:652)
         *     at java.lang.Thread.run(Thread.java:1563)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public String onPlaceholderRequest(Player first, Player second, String identifier) {
        if (first == null || second == null) {
            return null;
        }
        if (identifier.equalsIgnoreCase("tag")) {
            return this.relTag(first, second, false);
        }
        if (identifier.equalsIgnoreCase("tag_upper")) {
            return this.relTag(first, second, true);
        }
        return null;
    }

    private String relTag(Player first, Player second, boolean upper) {
        User user1 = (User)this.plugin.getUserManager().getUserData().get((Object)first.getUniqueId());
        User user2 = (User)this.plugin.getUserManager().getUserData().get((Object)second.getUniqueId());
        if (user1 == null || user2 == null) {
            return null;
        }
        Clan clan1 = this.clanManager.getClan(user2.getTag());
        if (clan1 == null) {
            return this.fileManager.getConfig().getNoneClan();
        }
        String tag = clan1.getTag();
        if (upper) {
            tag = tag.toUpperCase();
        }
        if (clan1.isMember(first.getUniqueId())) {
            return MessageUtil.toLegacy(this.fileManager.getConfig().getFormatMember(), java.util.Map.of("tag", tag));
        }
        Clan clan2 = this.clanManager.getClan(user1.getTag());
        if (clan2 != null && clan1.isAlliance(clan2.getTag())) {
            return MessageUtil.toLegacy(this.fileManager.getConfig().getFormatAlliance(), java.util.Map.of("tag", tag));
        }
        return MessageUtil.toLegacy(this.fileManager.getConfig().getFormatNormal(), java.util.Map.of("tag", tag));
    }

    private boolean isNumber(String arg) {
        try {
            int a = Integer.parseInt((String)arg);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private String handleTopType(RankType rankType, String identifier, int top) {
        Optional<PlayerStat> rank = this.plugin.getRankingManager().findTopPlayerByIndex(rankType, --top);
        if (rank.isEmpty()) {
            return "";
        }
        PlayerStat playerStat = (PlayerStat)rank.get();
        if (identifier.endsWith("_value")) {
            return String.valueOf((int)playerStat.getValue());
        }
        if (identifier.endsWith("_name")) {
            return String.valueOf((Object)playerStat.getName());
        }
        return "";
    }
}
