/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package dev.gether.getconfig.utils;

import dev.gether.getconfig.domain.config.potion.PotionEffectConfig;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionConverUtil {
    public static List<PotionEffect> getPotionEffectFromConfig(List<PotionEffectConfig> potionEffectConfigs) {
        ArrayList potionEffects = new ArrayList();
        potionEffectConfigs.forEach(arg_0 -> PotionConverUtil.lambda$getPotionEffectFromConfig$0((List)potionEffects, arg_0));
        return potionEffects;
    }

    public static List<PotionEffectType> getPotionEffectByName(List<String> potionsName) {
        ArrayList potionEffects = new ArrayList();
        potionsName.forEach(arg_0 -> PotionConverUtil.lambda$getPotionEffectByName$1((List)potionEffects, arg_0));
        return potionEffects;
    }

    private static /* synthetic */ void lambda$getPotionEffectByName$1(List potionEffects, String potion) {
        PotionEffectType potionEffectType = PotionEffectType.getByName((String)potion);
        if (potionEffectType == null) {
            MessageUtil.logMessage("\u001b[0;31m", "The potion effect name '" + potion + "' does not exist!");
        }
        potionEffects.add((Object)potionEffectType);
    }

    private static /* synthetic */ void lambda$getPotionEffectFromConfig$0(List potionEffects, PotionEffectConfig potion) {
        PotionEffectType potionEffectType = PotionEffectType.getByName((String)potion.getPotionName());
        if (potionEffectType == null) {
            MessageUtil.logMessage("\u001b[0;31m", "The potion effect name '" + potion.getPotionName() + "' does not exist!");
            return;
        }
        potionEffects.add((Object)new PotionEffect(potionEffectType, potion.getSeconds() * 20, potion.getLevel() - 1));
    }
}

