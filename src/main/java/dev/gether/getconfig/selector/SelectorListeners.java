/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.selector;

import dev.gether.getconfig.selector.SelectorAddon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class SelectorListeners
implements Listener {
    private final SelectorAddon selectorAddon;
    private final String PERMISSION_USAGE = "getselector.admin";

    public SelectorListeners(SelectorAddon selectorAddon) {
        this.selectorAddon = selectorAddon;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("getselector.admin")) {
            return;
        }
        this.selectorAddon.getSelectorManager().cleanCache(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        if (!player.hasPermission("getselector.admin")) {
            return;
        }
        if (item != null && item.isSimilar(this.selectorAddon.getSelectorItem())) {
            event.setCancelled(true);
            if (event.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }
            Block clickedBlock = event.getClickedBlock();
            switch (action) {
                case LEFT_CLICK_BLOCK: {
                    this.selectorAddon.getSelectorManager().setFirstLocation(player, clickedBlock.getLocation());
                    break;
                }
                case RIGHT_CLICK_BLOCK: {
                    this.selectorAddon.getSelectorManager().setSecondLocation(player, clickedBlock.getLocation());
                }
            }
        }
    }
}

