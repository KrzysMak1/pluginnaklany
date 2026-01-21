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

public class ChangeOwnerClanEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Clan clan;
    private final UUID previousOwnerUUID;
    private final UUID newOwnerUUID;
    private boolean isCancelled;

    public ChangeOwnerClanEvent(Clan clan, UUID previousOwnerUUID, UUID newOwnerUUID) {
        this.clan = clan;
        this.previousOwnerUUID = previousOwnerUUID;
        this.newOwnerUUID = newOwnerUUID;
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

    public Clan getClan() {
        return this.clan;
    }

    public UUID getPreviousOwnerUUID() {
        return this.previousOwnerUUID;
    }

    public UUID getNewOwnerUUID() {
        return this.newOwnerUUID;
    }
}

