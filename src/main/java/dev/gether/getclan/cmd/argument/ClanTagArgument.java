/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  org.bukkit.command.CommandSender
 */
package dev.gether.getclan.cmd.argument;

import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getconfig.utils.MessageUtil;
import dev.gether.shaded.litecommands.argument.Argument;
import dev.gether.shaded.litecommands.argument.parser.ParseResult;
import dev.gether.shaded.litecommands.argument.resolver.ArgumentResolver;
import dev.gether.shaded.litecommands.invocation.Invocation;
import dev.gether.shaded.litecommands.suggestion.SuggestionContext;
import dev.gether.shaded.litecommands.suggestion.SuggestionResult;
import java.util.List;
import org.bukkit.command.CommandSender;

public class ClanTagArgument
extends ArgumentResolver<CommandSender, Clan> {
    private final ClanManager clansManager;
    private final FileManager fileManager;

    public ClanTagArgument(ClanManager clansManager, FileManager fileManager) {
        this.clansManager = clansManager;
        this.fileManager = fileManager;
    }

    @Override
    protected ParseResult<Clan> parse(Invocation<CommandSender> invocation, Argument<Clan> context, String argument) {
        Clan clan = this.clansManager.getClan(argument);
        if (clan == null) {
            return ParseResult.failure(MessageUtil.toLegacy(this.fileManager.getLangConfig().getMessage("clan-does-not-exist"), java.util.Map.of()));
        }
        return ParseResult.success(clan);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Clan> argument, SuggestionContext context) {
        List sortedTags = this.clansManager.getClansData().keySet().stream().sorted().limit(5L).toList();
        return SuggestionResult.of((Iterable<String>)sortedTags);
    }
}
