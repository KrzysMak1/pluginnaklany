/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.UUID
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package dev.gether.getclan.event;

import java.util.UUID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerNameEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private String playerName;
    private UUID uuid;
    private boolean isCancelled;

    public PlayerNameEvent(String playerName, UUID uuid) {
        this.playerName = playerName;
        this.uuid = uuid;
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

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}

