/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.Particle
 */
package dev.gether.getconfig.domain.config.particles;

import dev.gether.getconfig.domain.config.particles.DustOptions;
import org.bukkit.Particle;

public class ParticleConfig {
    private boolean enable;
    private Particle particle;
    private int count = 15;
    private double offSetX = 0.1;
    private double offSetY = 0.1;
    private double offSetZ = 0.1;
    private double extra = 0.01;
    private DustOptions dustOptions;

    public ParticleConfig() {
    }

    public ParticleConfig(boolean enable, Particle particle, int count, double offSetX, double offSetY, double offSetZ, double extra, DustOptions dustOptions) {
        this.enable = enable;
        this.particle = particle;
        this.count = count;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.offSetZ = offSetZ;
        this.extra = extra;
        this.dustOptions = dustOptions;
    }

    public static ParticleConfigBuilder builder() {
        return new ParticleConfigBuilder();
    }

    public boolean isEnable() {
        return this.enable;
    }

    public Particle getParticle() {
        return this.particle;
    }

    public int getCount() {
        return this.count;
    }

    public double getOffSetX() {
        return this.offSetX;
    }

    public double getOffSetY() {
        return this.offSetY;
    }

    public double getOffSetZ() {
        return this.offSetZ;
    }

    public double getExtra() {
        return this.extra;
    }

    public DustOptions getDustOptions() {
        return this.dustOptions;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOffSetX(double offSetX) {
        this.offSetX = offSetX;
    }

    public void setOffSetY(double offSetY) {
        this.offSetY = offSetY;
    }

    public void setOffSetZ(double offSetZ) {
        this.offSetZ = offSetZ;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }

    public void setDustOptions(DustOptions dustOptions) {
        this.dustOptions = dustOptions;
    }

    public static class ParticleConfigBuilder {
        private boolean enable;
        private Particle particle;
        private int count;
        private double offSetX;
        private double offSetY;
        private double offSetZ;
        private double extra;
        private DustOptions dustOptions;

        ParticleConfigBuilder() {
        }

        public ParticleConfigBuilder enable(boolean enable) {
            this.enable = enable;
            return this;
        }

        public ParticleConfigBuilder particle(Particle particle) {
            this.particle = particle;
            return this;
        }

        public ParticleConfigBuilder count(int count) {
            this.count = count;
            return this;
        }

        public ParticleConfigBuilder offSetX(double offSetX) {
            this.offSetX = offSetX;
            return this;
        }

        public ParticleConfigBuilder offSetY(double offSetY) {
            this.offSetY = offSetY;
            return this;
        }

        public ParticleConfigBuilder offSetZ(double offSetZ) {
            this.offSetZ = offSetZ;
            return this;
        }

        public ParticleConfigBuilder extra(double extra) {
            this.extra = extra;
            return this;
        }

        public ParticleConfigBuilder dustOptions(DustOptions dustOptions) {
            this.dustOptions = dustOptions;
            return this;
        }

        public ParticleConfig build() {
            return new ParticleConfig(this.enable, this.particle, this.count, this.offSetX, this.offSetY, this.offSetZ, this.extra, this.dustOptions);
        }

        public String toString() {
            return "ParticleConfig.ParticleConfigBuilder(enable=" + this.enable + ", particle=" + this.particle + ", count=" + this.count + ", offSetX=" + this.offSetX + ", offSetY=" + this.offSetY + ", offSetZ=" + this.offSetZ + ", extra=" + this.extra + ", dustOptions=" + this.dustOptions + ")";
        }
    }
}

