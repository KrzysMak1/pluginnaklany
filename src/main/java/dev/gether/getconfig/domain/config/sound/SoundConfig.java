/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.Sound
 */
package dev.gether.getconfig.domain.config.sound;

import org.bukkit.Sound;

public class SoundConfig {
    private boolean enable;
    private Sound sound;

    public SoundConfig() {
    }

    public SoundConfig(boolean enable, Sound sound) {
        this.enable = enable;
        this.sound = sound;
    }

    public static SoundConfigBuilder builder() {
        return new SoundConfigBuilder();
    }

    public boolean isEnable() {
        return this.enable;
    }

    public Sound getSound() {
        return this.sound;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public static class SoundConfigBuilder {
        private boolean enable;
        private Sound sound;

        SoundConfigBuilder() {
        }

        public SoundConfigBuilder enable(boolean enable) {
            this.enable = enable;
            return this;
        }

        public SoundConfigBuilder sound(Sound sound) {
            this.sound = sound;
            return this;
        }

        public SoundConfig build() {
            return new SoundConfig(this.enable, this.sound);
        }

        public String toString() {
            return "SoundConfig.SoundConfigBuilder(enable=" + this.enable + ", sound=" + this.sound + ")";
        }
    }
}

