/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.List
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package dev.gether.getclan.event;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClanMembersEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private List<Player> playersOnline = new ArrayList();
    private boolean isCancelled;

    public ClanMembersEvent(List<Player> playersOnline) {
        this.playersOnline = playersOnline;
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

    public List<Player> getPlayersOnline() {
        return this.playersOnline;
    }

    public void removePlayer(Player player) {
        this.playersOnline.remove((Object)player);
    }
}

