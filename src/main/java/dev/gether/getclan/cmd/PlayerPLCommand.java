/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.cmd;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.core.user.User;
import dev.gether.shaded.litecommands.annotations.argument.Arg;
import dev.gether.shaded.litecommands.annotations.command.Command;
import dev.gether.shaded.litecommands.annotations.context.Context;
import dev.gether.shaded.litecommands.annotations.execute.Execute;
import dev.gether.shaded.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name="getplayer", aliases={"gracz"})
@Permission(value={"getclan.use"})
public class PlayerPLCommand {
    private final GetClan plugin;

    public PlayerPLCommand(GetClan plugin) {
        this.plugin = plugin;
    }

    @Execute
    public void infoClan(@Context Player player, @Arg(value="gracz") User user) {
        this.plugin.getUserManager().infoPlayer(player, user);
    }
}

