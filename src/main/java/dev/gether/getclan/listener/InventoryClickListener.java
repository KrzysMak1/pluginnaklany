/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Optional
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 */
package dev.gether.getclan.listener;

import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener
implements Listener {
    private final ClanManager clanManager;
    private final UserManager userManager;

    public InventoryClickListener(ClanManager clanManager, UserManager userManager) {
        this.clanManager = clanManager;
        this.userManager = userManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player)event.getWhoClicked();
        Optional<User> userByPlayer = this.userManager.findUserByPlayer(player);
        if (userByPlayer.isEmpty()) {
            return;
        }
        User user = (User)userByPlayer.get();
        if (!user.hasClan()) {
            return;
        }
        Clan clan = this.clanManager.getClan(user.getTag());
        if (inventory.equals((Object)clan.getInventory())) {
            event.setCancelled(true);
            this.clanManager.clickInv(player, clan, event.getRawSlot(), event.getClick());
        }
    }
}

