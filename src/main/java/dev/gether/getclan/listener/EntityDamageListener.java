/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package dev.gether.getclan.listener;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener
implements Listener {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final ClanManager clanManager;

    public EntityDamageListener(GetClan plugin, FileManager fileManager, ClanManager clanManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.clanManager = clanManager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }
        Player victim = (Player)event.getEntity();
        Player attacker = (Player)event.getDamager();
        User victimUserData = (User)this.plugin.getUserManager().getUserData().get((Object)victim.getUniqueId());
        if (victimUserData == null || !victimUserData.hasClan()) {
            return;
        }
        Clan victimClan = this.clanManager.getClan(victimUserData.getTag());
        if (victimClan.isMember(attacker.getUniqueId())) {
            if (!victimClan.isPvpEnable()) {
                event.setCancelled(true);
            }
            return;
        }
        User attackerUserData = (User)this.plugin.getUserManager().getUserData().get((Object)attacker.getUniqueId());
        if (attackerUserData == null || !attackerUserData.hasClan()) {
            return;
        }
        Clan attackerClan = this.clanManager.getClan(attackerUserData.getTag());
        if (victimClan.isAlliance(attackerClan.getTag()) && !this.fileManager.getConfig().isPvpAlliance()) {
            event.setCancelled(true);
        }
    }
}

