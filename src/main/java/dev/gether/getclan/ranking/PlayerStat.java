/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Comparable
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.UUID
 *  lombok.Generated
 */
package dev.gether.getclan.ranking;

import java.util.UUID;
import lombok.Generated;

public class PlayerStat
implements Comparable<PlayerStat> {
    private UUID uuid;
    private String name;
    private int value;

    public PlayerStat(UUID uuid, String name, int value) {
        this.uuid = uuid;
        this.name = name;
        this.value = value;
    }

    public int compareTo(PlayerStat other) {
        int compare = Integer.compare((int)this.value, (int)other.value);
        if (compare != 0) {
            return compare;
        }
        return this.uuid.compareTo(other.uuid);
    }

    @Generated
    public UUID getUuid() {
        return this.uuid;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public int getValue() {
        return this.value;
    }

    @Generated
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @Generated
    public void setValue(int value) {
        this.value = value;
    }
}

