/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Method
 *  java.util.Random
 *  java.util.UUID
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 */
package dev.gether.getconfig.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class CustomHead {
    public static ItemStack setTextures(ItemStack itemStack, Property property) {
        if (itemStack.getType() != Material.PLAYER_HEAD) {
            return itemStack;
        }
        SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
        Random random = new Random();
        String xyz = "";
        for (int i = 0; i < 6; ++i) {
            xyz = xyz + (char)(random.nextInt(26) + 97);
        }
        GameProfile profile = new GameProfile(UUID.randomUUID(), xyz);
        profile.getProperties().put("textures", property);
        try {
            Method method = skullMeta.getClass().getDeclaredMethod("setProfile", new Class[]{GameProfile.class});
            method.setAccessible(true);
            method.invoke((Object)skullMeta, new Object[]{profile});
            itemStack.setItemMeta((ItemMeta)skullMeta);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return itemStack;
    }
}
