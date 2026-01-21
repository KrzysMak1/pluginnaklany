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

public class DeleteDeputyEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Clan clan;
    private final UUID removedDeputyUUID;
    private boolean isCancelled;

    public DeleteDeputyEvent(Clan clan, UUID removedDeputyUUID) {
        this.clan = clan;
        this.removedDeputyUUID = removedDeputyUUID;
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

    public UUID getRemovedDeputyUUID() {
        return this.removedDeputyUUID;
    }

    public Clan getClan() {
        return this.clan;
    }
}

