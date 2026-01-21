/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.UUID
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package dev.gether.getclan.event;

import dev.gether.getclan.core.clan.Clan;
import java.util.UUID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerKickClanEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID kickedPlayerUUID;
    private final Clan clan;
    private boolean isCancelled;

    public PlayerKickClanEvent(Clan clan, UUID kickedPlayerUUID) {
        this.kickedPlayerUUID = kickedPlayerUUID;
        this.clan = clan;
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

    public UUID getKickedPlayer() {
        return this.kickedPlayerUUID;
    }

    public Clan getClan() {
        return this.clan;
    }
}

