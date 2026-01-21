/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 */
package dev.gether.getclan.listener;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import java.util.Map;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener
implements Listener {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final ClanManager clanManager;

    public AsyncPlayerChatListener(GetClan plugin, FileManager fileManager, ClanManager clanManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.clanManager = clanManager;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onSendMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        User user = (User)this.plugin.getUserManager().getUserData().get((Object)player.getUniqueId());
        if (!user.hasClan()) {
            return;
        }
        Clan clan = this.clanManager.getClan(user.getTag());
        if (message.startsWith("!!")) {
            event.setCancelled(true);
            message = message.substring(2);
            if (message.length() == 0) {
                return;
            }
            clan.broadcast(this.fileManager.getConfig().getFormatAllianceMessage(), Map.of("tag", clan.getTag(), "message", message, "player", player.getName()));
            return;
        }
        if (message.startsWith("!")) {
            event.setCancelled(true);
            message = message.substring(1);
            if (message.length() == 0) {
                return;
            }
            clan.broadcast(this.fileManager.getConfig().getFormatClanMessage(), Map.of("tag", clan.getTag(), "message", message, "player", player.getName()));
            return;
        }
    }
}
