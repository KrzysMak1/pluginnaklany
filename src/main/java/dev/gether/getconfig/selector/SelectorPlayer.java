/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package dev.gether.getconfig.selector;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SelectorPlayer {
    private Player player;
    private Location firstLocation;
    private Location secondLocation;

    SelectorPlayer(Player player, Location firstLocation, Location secondLocation) {
        this.player = player;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
    }

    public static SelectorPlayerBuilder builder() {
        return new SelectorPlayerBuilder();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location getFirstLocation() {
        return this.firstLocation;
    }

    public Location getSecondLocation() {
        return this.secondLocation;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    public void setSecondLocation(Location secondLocation) {
        this.secondLocation = secondLocation;
    }

    public static class SelectorPlayerBuilder {
        private Player player;
        private Location firstLocation;
        private Location secondLocation;

        SelectorPlayerBuilder() {
        }

        public SelectorPlayerBuilder player(Player player) {
            this.player = player;
            return this;
        }

        public SelectorPlayerBuilder firstLocation(Location firstLocation) {
            this.firstLocation = firstLocation;
            return this;
        }

        public SelectorPlayerBuilder secondLocation(Location secondLocation) {
            this.secondLocation = secondLocation;
            return this;
        }

        public SelectorPlayer build() {
            return new SelectorPlayer(this.player, this.firstLocation, this.secondLocation);
        }

        public String toString() {
            return "SelectorPlayer.SelectorPlayerBuilder(player=" + this.player + ", firstLocation=" + this.firstLocation + ", secondLocation=" + this.secondLocation + ")";
        }
    }
}

