/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  org.bukkit.Color
 *  org.bukkit.Particle
 *  org.bukkit.Particle$DustOptions
 *  org.bukkit.entity.Entity
 */
package dev.gether.getconfig.utils;

import dev.gether.getconfig.domain.config.particles.DustOptions;
import dev.gether.getconfig.domain.config.particles.ParticleConfig;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public class ParticlesUtil {
    public static void spawnParticles(Entity entity, ParticleConfig particleConfig) {
        Particle.DustOptions dustOptions = null;
        if (particleConfig.getParticle() == Particle.DUST && particleConfig.getDustOptions() != null) {
            DustOptions customDustOptions = particleConfig.getDustOptions();
            dustOptions = new Particle.DustOptions(Color.fromRGB((int)customDustOptions.getRed(), (int)customDustOptions.getGreen(), (int)customDustOptions.getBlue()), (float)customDustOptions.getSize());
        }
        entity.getWorld().spawnParticle(particleConfig.getParticle(), entity.getLocation(), particleConfig.getCount(), particleConfig.getOffSetX(), particleConfig.getOffSetY(), particleConfig.getOffSetZ(), particleConfig.getExtra(), dustOptions);
    }
}
