/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  org.bukkit.FluidCollisionMode
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.util.RayTraceResult
 *  org.bukkit.util.Vector
 */
package dev.gether.getconfig.domain;

import dev.gether.getconfig.domain.ItemModelData;
import dev.gether.getconfig.domain.WeaponType;
import dev.gether.getconfig.utils.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class Weapon {
    private String name;
    private WeaponType weaponType;
    private Sound sound = Sound.ENTITY_WITHER_SHOOT;
    private double soundPitch = 2.0;
    private double soundVolume = 1.0;
    private boolean scope = false;
    private double attackCooldown;
    private double damage = 1.0;
    private int amountBullet = 1;
    private int bulletMagazine = 30;
    private int reloadingCooldown = 5;
    private ItemModelData ammoItem = ItemModelData.builder().itemStack(ItemBuilder.create(Material.ARROW, "Ammo 1", (List<String>)new ArrayList(), true, false, 1)).build();
    private ItemModelData weaponItem = ItemModelData.builder().itemStack(ItemBuilder.create(Material.CROSSBOW, "Bron", (List<String>)new ArrayList(), true, false, 1)).build();

    public Weapon(String name, WeaponType weaponType) {
        this.name = name;
        this.weaponType = weaponType;
    }

    public void shot(Player player, Vector vectorEye, int distance) {
        RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(), vectorEye, (double)distance, FluidCollisionMode.NEVER, true, 0.0, e -> e instanceof LivingEntity && !player.getUniqueId().equals((Object)e.getUniqueId()));
        if (result == null) {
            return;
        }
        Entity hitEntity = result.getHitEntity();
        if (hitEntity == null) {
            return;
        }
        LivingEntity mob = (LivingEntity)hitEntity;
        mob.damage(this.damage, (Entity)player);
    }

    public String toString() {
        return "Weapon{name='" + this.name + "', weaponType=" + this.weaponType + ", sound=" + this.sound + ", soundPitch=" + this.soundPitch + ", soundVolume=" + this.soundVolume + ", scope=" + this.scope + ", attackCooldown=" + this.attackCooldown + ", damage=" + this.damage + ", amountBullet=" + this.amountBullet + ", bulletMagazine=" + this.bulletMagazine + ", reloadingCooldown=" + this.reloadingCooldown + ", ammoItem=" + this.ammoItem + ", weaponItem=" + this.weaponItem + "}";
    }

    public Weapon() {
    }

    public Weapon(String name, WeaponType weaponType, Sound sound, double soundPitch, double soundVolume, boolean scope, double attackCooldown, double damage, int amountBullet, int bulletMagazine, int reloadingCooldown, ItemModelData ammoItem, ItemModelData weaponItem) {
        this.name = name;
        this.weaponType = weaponType;
        this.sound = sound;
        this.soundPitch = soundPitch;
        this.soundVolume = soundVolume;
        this.scope = scope;
        this.attackCooldown = attackCooldown;
        this.damage = damage;
        this.amountBullet = amountBullet;
        this.bulletMagazine = bulletMagazine;
        this.reloadingCooldown = reloadingCooldown;
        this.ammoItem = ammoItem;
        this.weaponItem = weaponItem;
    }

    public String getName() {
        return this.name;
    }

    public WeaponType getWeaponType() {
        return this.weaponType;
    }

    public Sound getSound() {
        return this.sound;
    }

    public double getSoundPitch() {
        return this.soundPitch;
    }

    public double getSoundVolume() {
        return this.soundVolume;
    }

    public boolean isScope() {
        return this.scope;
    }

    public double getAttackCooldown() {
        return this.attackCooldown;
    }

    public double getDamage() {
        return this.damage;
    }

    public int getAmountBullet() {
        return this.amountBullet;
    }

    public int getBulletMagazine() {
        return this.bulletMagazine;
    }

    public int getReloadingCooldown() {
        return this.reloadingCooldown;
    }

    public ItemModelData getAmmoItem() {
        return this.ammoItem;
    }

    public ItemModelData getWeaponItem() {
        return this.weaponItem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setSoundPitch(double soundPitch) {
        this.soundPitch = soundPitch;
    }

    public void setSoundVolume(double soundVolume) {
        this.soundVolume = soundVolume;
    }

    public void setScope(boolean scope) {
        this.scope = scope;
    }

    public void setAttackCooldown(double attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setAmountBullet(int amountBullet) {
        this.amountBullet = amountBullet;
    }

    public void setBulletMagazine(int bulletMagazine) {
        this.bulletMagazine = bulletMagazine;
    }

    public void setReloadingCooldown(int reloadingCooldown) {
        this.reloadingCooldown = reloadingCooldown;
    }

    public void setAmmoItem(ItemModelData ammoItem) {
        this.ammoItem = ammoItem;
    }

    public void setWeaponItem(ItemModelData weaponItem) {
        this.weaponItem = weaponItem;
    }
}

