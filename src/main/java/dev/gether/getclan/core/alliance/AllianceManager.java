/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package dev.gether.getclan.core.alliance;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.cmd.context.domain.DeputyOwner;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.alliance.AllianceService;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.event.CreateAllianceEvent;
import dev.gether.getclan.event.DisbandAllianceEvent;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class AllianceManager {
    private final GetClan plugin;
    private final AllianceService allianceService;
    private final FileManager fileManager;

    public AllianceManager(GetClan plugin, AllianceService allianceService, FileManager fileManager) {
        this.plugin = plugin;
        this.allianceService = allianceService;
        this.fileManager = fileManager;
    }

    public void loadAlliances() {
        Map<String, String> alliances = this.allianceService.loadAlliances();
        alliances.forEach((tag1, tag2) -> {
            Clan clan1 = this.plugin.getClanManager().getClan((String)tag1);
            Clan clan2 = this.plugin.getClanManager().getClan((String)tag2);
            if (clan1 == null || clan2 == null) {
                MessageUtil.logMessage("\u001b[0;31m", "Something is wrong! Alliance clan doesn't exists! " + tag1 + " or " + tag2);
                return;
            }
            clan1.addAlliance((String)tag2);
            clan2.addAlliance((String)tag1);
        });
    }

    public void alliance(DeputyOwner deputyOwner, Clan allianceClan) {
        Clan clan = deputyOwner.getClan();
        Player player = deputyOwner.getPlayer();
        if (this.plugin.getClanManager().isYourClan(allianceClan, player.getUniqueId())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("cannot-alliance-own-clan"));
            return;
        }
        if (clan.isAlliance(allianceClan.getTag())) {
            DisbandAllianceEvent event = new DisbandAllianceEvent(clan, allianceClan);
            Bukkit.getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                clan.removeAlliance(allianceClan.getTag());
                allianceClan.removeAlliance(clan.getTag());
                this.allianceService.deleteAlliance(clan.getTag());
                MessageUtil.broadcast(this.fileManager.getLangConfig().getMessage("alliance-disbanded"), Map.of("first-clan", clan.getTag(), "second-clan", allianceClan.getTag()));
            }
            return;
        }
        if (this.plugin.getClanManager().isLimitAlliance(clan)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("alliance-limit-reached"));
            return;
        }
        if (allianceClan.isSuggestAlliance(clan.getTag())) {
            CreateAllianceEvent event = new CreateAllianceEvent(clan, allianceClan);
            Bukkit.getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                allianceClan.removeSuggestAlliance(clan.getTag());
                clan.addAlliance(allianceClan.getTag());
                allianceClan.addAlliance(clan.getTag());
                this.allianceService.createAlliance(clan.getTag(), allianceClan.getTag());
                MessageUtil.broadcast(this.fileManager.getLangConfig().getMessage("alliance-formed"), Map.of("first-clan", clan.getTag(), "second-clan", allianceClan.getTag()));
            }
            return;
        }
        if (!clan.isSuggestAlliance(allianceClan.getTag())) {
            clan.inviteAlliance(allianceClan.getTag());
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("suggest-alliance"));
            allianceClan.broadcast(this.fileManager.getLangConfig().getMessage("get-suggest-alliance"), Map.of("tag", clan.getTag()));
        } else {
            clan.removeInviteAlliance(allianceClan.getTag());
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("cancel-suggest-alliance"), Map.of("tag", allianceClan.getTag()));
        }
    }
}
