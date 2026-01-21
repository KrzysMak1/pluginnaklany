/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.cmd.context.domain;

import dev.gether.getclan.core.clan.Clan;
import org.bukkit.entity.Player;

public class ClanMember {
    private Player player;
    private Clan clan;

    public ClanMember(Player player, Clan clan) {
        this.player = player;
        this.clan = clan;
    }

    public Clan getClan() {
        return this.clan;
    }

    public Player getPlayer() {
        return this.player;
    }
}

