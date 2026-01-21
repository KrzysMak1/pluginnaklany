/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Objects
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package dev.gether.getconfig.utils;

import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
    public static String getItemName(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        return meta.hasDisplayName() ? meta.getDisplayName() : itemStack.getType().name();
    }

    public static ItemStack hideAttribute(ItemStack itemStack) {
        ItemStack item = itemStack.clone();
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        item.setItemMeta(meta);
        return item;
    }

    public static boolean hasItemStack(Player player, ItemStack needItemStack) {
        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null || itemStack.getType() == Material.AIR || !itemStack.isSimilar(needItemStack)) continue;
            return true;
        }
        return false;
    }

    public static boolean sameItem(ItemStack item1, ItemStack item2) {
        if (item1.getType() != item2.getType()) {
            return false;
        }
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta1 != null && meta2 != null && !Objects.equals((Object)meta1.getDisplayName(), (Object)meta2.getDisplayName())) {
            return false;
        }
        if (!Objects.equals((Object)meta1.getLore(), (Object)meta2.getLore())) {
            return false;
        }
        if (!Objects.equals((Object)item1.getEnchantments(), (Object)item2.getEnchantments())) {
            return false;
        }
        if (!Objects.equals((Object)meta1.getItemFlags(), (Object)meta2.getItemFlags())) {
            return false;
        }
        return meta1.isUnbreakable() == meta2.isUnbreakable();
    }

    public static boolean sameItemName(ItemStack item1, ItemStack item2) {
        if (item1.getType() != item2.getType()) {
            return false;
        }
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta1 != null && meta2 != null && !Objects.equals((Object)meta1.getDisplayName(), (Object)meta2.getDisplayName())) {
            return false;
        }
        if (!Objects.equals((Object)item1.getEnchantments(), (Object)item2.getEnchantments())) {
            return false;
        }
        if (!Objects.equals((Object)meta1.getItemFlags(), (Object)meta2.getItemFlags())) {
            return false;
        }
        return meta1.isUnbreakable() == meta2.isUnbreakable();
    }

    public static void removeItem(Player player, ItemStack itemStack, int amount) {
        int remove = amount;
        for (int i = 0; i < player.getInventory().getSize(); ++i) {
            ItemStack current = player.getInventory().getItem(i);
            if (current == null || !current.isSimilar(itemStack)) continue;
            int currentAmount = current.getAmount();
            if (currentAmount >= remove) {
                current.setAmount(currentAmount - remove);
                break;
            }
            player.getInventory().setItem(i, null);
            remove -= currentAmount;
        }
    }

    public static int calcItem(Player player, ItemStack calcStack) {
        int amount = 0;
        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null || itemStack.getType() == Material.AIR || !itemStack.isSimilar(calcStack)) continue;
            amount += itemStack.getAmount();
        }
        return amount;
    }

    public static boolean hasCurrentAmount(Player player, ItemStack itemStack, int needAmount) {
        int amount = ItemUtil.calcItem(player, itemStack);
        return amount >= needAmount;
    }
}

