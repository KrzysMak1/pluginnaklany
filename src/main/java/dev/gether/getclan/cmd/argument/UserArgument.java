/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.stream.Collectors
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.cmd.argument;

import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getconfig.utils.MessageUtil;
import dev.gether.shaded.litecommands.argument.Argument;
import dev.gether.shaded.litecommands.argument.parser.ParseResult;
import dev.gether.shaded.litecommands.argument.resolver.ArgumentResolver;
import dev.gether.shaded.litecommands.invocation.Invocation;
import dev.gether.shaded.litecommands.suggestion.SuggestionContext;
import dev.gether.shaded.litecommands.suggestion.SuggestionResult;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class UserArgument
extends ArgumentResolver<CommandSender, User> {
    private final UserManager userManager;
    private final FileManager fileManager;

    public UserArgument(UserManager userManager, FileManager fileManager) {
        this.userManager = userManager;
        this.fileManager = fileManager;
    }

    @Override
    protected ParseResult<User> parse(Invocation<CommandSender> invocation, Argument<User> context, String argument) {
        Player player = Bukkit.getPlayer((String)argument);
        if (player == null) {
            return ParseResult.failure(MessageUtil.toLegacy(this.fileManager.getLangConfig().getMessage("player-not-found"), java.util.Map.of()));
        }
        User user = (User)this.userManager.getUserData().get((Object)player.getUniqueId());
        return ParseResult.success(user);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<User> argument, SuggestionContext context) {
        return SuggestionResult.of((Iterable<String>)((Iterable)Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList())));
    }
}
