/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package dev.gether.getclan.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PointsChangeUserEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player killer;
    private final Player victim;
    private int pointKiller;
    private int pointVictim;
    private boolean isCancelled;

    public PointsChangeUserEvent(Player killer, Player victim, int pointKiller, int pointVictim) {
        this.killer = killer;
        this.victim = victim;
        this.pointKiller = pointKiller;
        this.pointVictim = pointVictim;
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

    public Player getKiller() {
        return this.killer;
    }

    public Player getVictim() {
        return this.victim;
    }

    public int getPointKiller() {
        return this.pointKiller;
    }

    public int getPointVictim() {
        return this.pointVictim;
    }

    public void setPointKiller(int pointKiller) {
        this.pointKiller = pointKiller;
    }

    public void setPointVictim(int pointVictim) {
        this.pointVictim = pointVictim;
    }
}

