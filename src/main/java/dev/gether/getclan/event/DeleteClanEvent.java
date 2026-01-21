/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package dev.gether.getclan.event;

import dev.gether.getclan.core.clan.Clan;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DeleteClanEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private String playerName;
    private final Player player;
    private final Clan clan;
    private boolean isCancelled;

    public DeleteClanEvent(Player player, Clan clan) {
        this.player = player;
        this.playerName = player.getName();
        this.clan = clan;
    }

    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Clan getClan() {
        return this.clan;
    }
}

