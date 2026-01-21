/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Optional
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package dev.gether.getclan.listener;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.core.CooldownManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.user.User;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerConnectionListener
implements Listener {
    private final GetClan plugin;
    private final CooldownManager cooldownManager;
    private final ClanManager clanManager;

    public PlayerConnectionListener(GetClan plugin, CooldownManager cooldownManager, ClanManager clanManager) {
        this.plugin = plugin;
        this.cooldownManager = cooldownManager;
        this.clanManager = clanManager;
    }

    @EventHandler
    public void onJoinPlayer(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        new BukkitRunnable(){

            public void run() {
                PlayerConnectionListener.this.plugin.getUserManager().loadUser(player);
            }
        }.runTaskAsynchronously((Plugin)this.plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.cooldownManager.delPlayerFromCooldown(player);
        Optional<User> userByPlayer = this.plugin.getUserManager().findUserByPlayer(player);
        if (userByPlayer.isEmpty()) {
            return;
        }
        User user = (User)userByPlayer.get();
        if (!user.hasClan()) {
            return;
        }
        Clan clan = this.clanManager.getClan(user.getTag());
        boolean owner = clan.isOwner(player.getUniqueId());
        if (owner) {
            clan.resetInvite();
        }
    }
}

