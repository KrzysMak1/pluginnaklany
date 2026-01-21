/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.cmd.context.domain;

import dev.gether.getclan.cmd.context.domain.ClanMember;
import dev.gether.getclan.core.clan.Clan;
import org.bukkit.entity.Player;

public class Member
extends ClanMember {
    public Member(Player player, Clan clan) {
        super(player, clan);
    }
}

