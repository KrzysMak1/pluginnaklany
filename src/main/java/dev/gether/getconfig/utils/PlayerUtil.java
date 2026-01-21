/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  org.bukkit.Location
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {
    public static void giveItem(Player player, ItemStack itemStack) {
        if (PlayerUtil.isInventoryFull(player)) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(new ItemStack[]{itemStack});
        }
    }

    private static boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

    public static List<Player> findNearPlayers(Location location, int distance) {
        List<Player> players = new ArrayList<>();
        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, (double)distance, (double)distance, (double)distance);
        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof Player)) continue;
            Player player = (Player)entity;
            players.add(player);
        }
        return players;
    }
}
