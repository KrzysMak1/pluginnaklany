/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.Override
 *  org.bukkit.command.CommandSender
 */
package dev.gether.getclan.handler;

import dev.gether.getclan.config.FileManager;
import dev.gether.getconfig.utils.MessageUtil;
import dev.gether.shaded.litecommands.handler.result.ResultHandlerChain;
import dev.gether.shaded.litecommands.invalidusage.InvalidUsage;
import dev.gether.shaded.litecommands.invalidusage.InvalidUsageHandler;
import dev.gether.shaded.litecommands.invocation.Invocation;
import dev.gether.shaded.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

public class CustomInvalidUsage
implements InvalidUsageHandler<CommandSender> {
    private final FileManager fileManager;

    public CustomInvalidUsage(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> resultHandlerChain) {
        Schematic schematic = result.getSchematic();
        if (schematic.isOnlyFirst()) {
            MessageUtil.sendMessage(invocation.sender(), this.fileManager.getLangConfig().getMessage("usage-cmd"), java.util.Map.of("usage", schematic.first()));
            return;
        }
        MessageUtil.sendMessage(invocation.sender(), this.fileManager.getLangConfig().getMessage("usage-list"));
    }
}
