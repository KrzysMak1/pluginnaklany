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

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CreateClanEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private String playerName;
    private final Player player;
    private final String tag;
    private boolean isCancelled;

    public CreateClanEvent(Player player, String tag) {
        this.player = player;
        this.playerName = player.getName();
        this.tag = tag;
        this.isCancelled = false;
    }

    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getTag() {
        return this.tag;
    }
}

