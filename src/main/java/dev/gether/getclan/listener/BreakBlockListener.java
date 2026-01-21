/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Optional
 *  java.util.Random
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 */
package dev.gether.getclan.listener;

import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.upgrade.LevelData;
import dev.gether.getclan.core.upgrade.Upgrade;
import dev.gether.getclan.core.upgrade.UpgradeCost;
import dev.gether.getclan.core.upgrade.UpgradeManager;
import dev.gether.getclan.core.upgrade.UpgradeType;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getconfig.utils.PlayerUtil;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BreakBlockListener
implements Listener {
    private final UserManager userManager;
    private final ClanManager clanManager;
    private final UpgradeManager upgradeManager;
    private final FileManager fileManager;
    private final Random random = new Random();

    public BreakBlockListener(UserManager userManager, ClanManager clanManager, UpgradeManager upgradeManager, FileManager fileManager) {
        this.userManager = userManager;
        this.clanManager = clanManager;
        this.upgradeManager = upgradeManager;
        this.fileManager = fileManager;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!this.fileManager.getUpgradesConfig().isUpgradeEnable()) {
            return;
        }
        Optional<Upgrade> upgradeByType = this.fileManager.getUpgradesConfig().findUpgradeByType(UpgradeType.DROP_BOOST);
        if (upgradeByType.isEmpty()) {
            return;
        }
        Upgrade upgrade = (Upgrade)upgradeByType.get();
        if (!upgrade.isEnabled()) {
            return;
        }
        if (this.fileManager.getUpgradesConfig().getWhitelistMaterial().isEmpty()) {
            return;
        }
        if (!this.fileManager.getUpgradesConfig().getWhitelistMaterial().contains((Object)event.getBlock().getType())) {
            return;
        }
        Player player = event.getPlayer();
        Optional<User> userByPlayer = this.userManager.findUserByPlayer(player);
        if (userByPlayer.isEmpty()) {
            return;
        }
        User user = (User)userByPlayer.get();
        if (!user.hasClan()) {
            return;
        }
        Clan clan = this.clanManager.getClan(user.getTag());
        if (clan == null) {
            return;
        }
        LevelData levelData = (LevelData)clan.getUpgrades().get((Object)UpgradeType.DROP_BOOST);
        if (levelData == null) {
            return;
        }
        Optional<UpgradeCost> upgradeCostTemp = this.upgradeManager.findUpgradeCost(UpgradeType.DROP_BOOST, levelData.getLevel());
        if (upgradeCostTemp.isEmpty()) {
            return;
        }
        UpgradeCost upgradeCost = (UpgradeCost)upgradeCostTemp.get();
        double boostValue = upgradeCost.getBoostValue();
        double multiply = 1.0 + boostValue;
        double restChance = multiply % 1.0;
        ArrayList<ItemStack> drops = new ArrayList<>(event.getBlock().getDrops(player.getInventory().getItemInMainHand()));
        if (drops.isEmpty()) {
            return;
        }
        event.setDropItems(false);
        drops.forEach(itemStack -> {
            int basicAmount = itemStack.getAmount();
            int recieveAmount = basicAmount * (int)multiply;
            double winTicket = this.random.nextDouble();
            if (winTicket <= restChance) {
                recieveAmount += basicAmount;
            }
            itemStack.setAmount(recieveAmount);
            PlayerUtil.giveItem(player, itemStack);
        });
    }
}
