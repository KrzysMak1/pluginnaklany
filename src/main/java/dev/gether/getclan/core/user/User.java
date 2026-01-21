/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.UUID
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.core.user;

import java.util.UUID;
import org.bukkit.entity.Player;

public class User {
    private UUID uuid;
    private String name;
    private int kills;
    private int death;
    private int points;
    private String tag;
    private boolean update = false;

    public User(UUID uuid, String name, int kills, int death, int points, String tag) {
        this.kills = kills;
        this.death = death;
        this.points = points;
        this.uuid = uuid;
        this.name = name;
        this.tag = tag;
    }

    public User(Player player, int points) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.kills = 0;
        this.death = 0;
        this.points = points;
    }

    public void increaseDeath() {
        ++this.death;
        this.update = true;
    }

    public void increaseKill() {
        ++this.kills;
        this.update = true;
    }

    public void takePoint(int points) {
        this.points -= points;
        this.update = true;
    }

    public void addPoint(int points) {
        this.points += points;
        this.update = true;
    }

    public void setTag(String tag) {
        this.tag = tag;
        this.update = true;
    }

    public void resetKill() {
        this.kills = 0;
        this.update = true;
    }

    public void resetDeath() {
        this.death = 0;
        this.update = true;
    }

    public void setPoints(int points) {
        this.points = points;
        this.update = true;
    }

    public String getTag() {
        return this.tag;
    }

    public boolean hasClan() {
        return this.tag != null;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeath() {
        return this.death;
    }

    public int getPoints() {
        return this.points;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setUpdate(boolean status) {
        this.update = status;
    }
}

