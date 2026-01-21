/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  org.bukkit.command.CommandSender
 */
package dev.gether.getclan.handler;

import dev.gether.getclan.config.FileManager;
import dev.gether.getconfig.utils.MessageUtil;
import dev.gether.shaded.litecommands.handler.result.ResultHandlerChain;
import dev.gether.shaded.litecommands.invocation.Invocation;
import dev.gether.shaded.litecommands.permission.MissingPermissions;
import dev.gether.shaded.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;

public class PermissionMessage
implements MissingPermissionsHandler<CommandSender> {
    private final FileManager fileManager;

    public PermissionMessage(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> resultHandlerChain) {
        MessageUtil.sendMessage(invocation.sender(), this.fileManager.getLangConfig().getMessage("no-permission"), java.util.Map.of("permission", String.join((CharSequence)", ", missingPermissions.getPermissions())));
    }
}
