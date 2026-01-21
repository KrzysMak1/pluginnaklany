/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 */
package dev.gether.getconfig.utils;

import dev.gether.getconfig.utils.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
    private ItemBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemStack create(Material material, String name, List<String> lore, boolean glow) {
        ItemStack itemStack = ItemBuilder.create(material, name, glow);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore((List<String>)new ArrayList(lore.stream().map(line -> MessageUtil.toLegacy((String)line, java.util.Map.of())).toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(String material, String name, List<String> lore, boolean glow) {
        return ItemBuilder.create(Material.valueOf((String)material), name, lore, glow);
    }

    public static ItemStack create(Material material, String name, List<String> lore, boolean glow, boolean unbreakable) {
        ItemStack itemStack = ItemBuilder.create(material, name, lore, glow);
        if (!unbreakable) {
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material material, String name, List<String> lore, boolean glow, boolean unbreakable, int modelData) {
        ItemStack itemStack = ItemBuilder.create(material, name, lore, glow, unbreakable);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(Integer.valueOf((int)modelData));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createSkull(String name, List<String> lore) {
        ItemStack itemStack = ItemBuilder.create(Material.PLAYER_HEAD, name, lore, false);
        SkullMeta itemMeta = (SkullMeta)itemStack.getItemMeta();
        itemMeta.setOwner(name);
        itemMeta.setDisplayName(MessageUtil.toLegacy(name, java.util.Map.of()));
        itemStack.setItemMeta((ItemMeta)itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material material, String name, boolean glow) {
        ItemStack itemStack = new ItemStack(material);
        if (glow) {
            itemStack.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtil.toLegacy(name, java.util.Map.of()));
        if (glow) {
            itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material material, String displayname, List<String> lore, boolean glow, boolean unbreakable, Integer modelData) {
        ItemStack itemStack = ItemBuilder.create(material, displayname, lore, glow, unbreakable);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemStack.hasItemMeta() || modelData == null) {
            return itemStack;
        }
        itemMeta.setCustomModelData(modelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
