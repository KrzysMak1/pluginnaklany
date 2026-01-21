/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Optional
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.inventory.EquipmentSlot
 */
package dev.gether.getclan.listener;

import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.CooldownManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.Optional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractionEntityListener
implements Listener {
    private final FileManager fileManager;
    private final UserManager userManager;
    private final CooldownManager cooldownManager;

    public PlayerInteractionEntityListener(FileManager fileManager, UserManager userManager, CooldownManager cooldownManager) {
        this.fileManager = fileManager;
        this.userManager = userManager;
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity instanceof Player) {
            Player clickPlayer = (Player)entity;
            if (!player.isSneaking()) {
                return;
            }
            if (event.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }
            if (this.cooldownManager.hasCooldown(player)) {
                MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("slow-down"));
                return;
            }
            this.cooldownManager.addCooldown(player);
            Optional<User> userByPlayer = this.userManager.findUserByPlayer(clickPlayer);
            if (userByPlayer.isPresent()) {
                User user = (User)userByPlayer.get();
                this.userManager.infoPlayer(player, user);
            }
        }
    }
}

