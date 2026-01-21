/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package dev.gether.getconfig.domain.config.potion;

public class PotionEffectConfig {
    private String potionName;
    private int seconds;
    private int level;

    public PotionEffectConfig() {
    }

    public PotionEffectConfig(String potionName, int seconds, int level) {
        this.potionName = potionName;
        this.seconds = seconds;
        this.level = level;
    }

    public String getPotionName() {
        return this.potionName;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public int getLevel() {
        return this.level;
    }

    public void setPotionName(String potionName) {
        this.potionName = potionName;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

