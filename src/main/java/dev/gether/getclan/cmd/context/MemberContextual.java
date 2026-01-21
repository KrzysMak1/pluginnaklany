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
import dev.gether.getclan.cmd.context.domain.Member;
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

public class MemberContextual
implements ContextProvider<CommandSender, Member> {
    private final GetClan getClan;
    private final FileManager fileManager;
    private final ClanManager clanManager;

    public MemberContextual(GetClan getClan, FileManager fileManager, ClanManager clanManager) {
        this.getClan = getClan;
        this.fileManager = fileManager;
        this.clanManager = clanManager;
    }

    @Override
    public ContextResult<Member> provide(Invocation<CommandSender> invocation) {
        CommandSender commandSender = invocation.sender();
        if (!(commandSender instanceof Player)) {
            return ContextResult.error(this.fileManager.getLangConfig().getMessage("player-not-found"));
        }
        Player player = (Player)commandSender;
        UserManager userManager = this.getClan.getUserManager();
        User user = (User)userManager.getUserData().get((Object)player.getUniqueId());
        Clan clan = this.clanManager.getClan(user.getTag());
        if (!user.hasClan()) {
            return ContextResult.error(this.fileManager.getLangConfig().getMessage("player-has-no-clan"));
        }
        return ContextResult.ok(() -> new Member(player, clan));
    }
}

