/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.cmd.context;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.cmd.context.domain.DeputyOwner;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.shaded.litecommands.context.ContextProvider;
import dev.gether.shaded.litecommands.context.ContextResult;
import dev.gether.shaded.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeputyOwnerContextual
implements ContextProvider<CommandSender, DeputyOwner> {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final ClanManager clanManager;

    public DeputyOwnerContextual(GetClan plugin, FileManager fileManager, ClanManager clanManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.clanManager = clanManager;
    }

    @Override
    public ContextResult<DeputyOwner> provide(Invocation<CommandSender> invocation) {
        CommandSender commandSender = invocation.sender();
        if (!(commandSender instanceof Player)) {
            return ContextResult.error(this.fileManager.getLangConfig().getMessage("player-not-found"));
        }
        Player player = (Player)commandSender;
        UserManager userManager = this.plugin.getUserManager();
        User user = (User)userManager.getUserData().get((Object)player.getUniqueId());
        if (!user.hasClan()) {
            return ContextResult.error(this.fileManager.getLangConfig().getMessage("player-has-no-clan"));
        }
        Clan clan = this.clanManager.getClan(user.getTag());
        if (!clan.isOwner(player.getUniqueId()) && !clan.isDeputy(player.getUniqueId())) {
            return ContextResult.error(this.fileManager.getLangConfig().getMessage("not-clan-owner"));
        }
        return ContextResult.ok(() -> new DeputyOwner(player, clan));
    }
}

