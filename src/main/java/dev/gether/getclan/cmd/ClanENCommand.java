/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Optional
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getclan.cmd;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.cmd.context.domain.DeputyOwner;
import dev.gether.getclan.cmd.context.domain.Member;
import dev.gether.getclan.cmd.context.domain.Owner;
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
import dev.gether.getconfig.utils.MessageUtil;
import dev.gether.getconfig.utils.PlayerUtil;
import dev.gether.shaded.litecommands.annotations.argument.Arg;
import dev.gether.shaded.litecommands.annotations.command.Command;
import dev.gether.shaded.litecommands.annotations.context.Context;
import dev.gether.shaded.litecommands.annotations.execute.Execute;
import dev.gether.shaded.litecommands.annotations.permission.Permission;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name="getclan", aliases={"clan"})
@Permission(value={"getclan.use"})
public class ClanENCommand {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final ClanManager clanManager;
    private final UpgradeManager upgradeManager;
    private final Pattern pattern = Pattern.compile((String)"^[a-zA-Z\u0105\u0107\u0119\u0142\u0144\u00f3\u015b\u017a\u017c\u0104\u0106\u0118\u0141\u0143\u00d3\u015a\u0179\u017b0-9]+$");

    public ClanENCommand(GetClan plugin, FileManager fileManager, ClanManager clanManager, UpgradeManager upgradeManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.clanManager = clanManager;
        this.upgradeManager = upgradeManager;
    }

    private boolean isPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, this.fileManager.getLangConfig().getMessage("not-possible-via-console"));
            return false;
        }
        return true;
    }

    @Execute(name="delete")
    public void deleteClan(@Context Owner owner) {
        this.plugin.getClanManager().deleteClan(owner);
    }

    @Execute(name="leave")
    public void leaveClan(@Context Member member) {
        this.plugin.getClanManager().leaveClan(member);
    }

    @Execute(name="create")
    public void createClan(@Context CommandSender sender, @Arg String tag) {
        if (!this.isPlayer(sender)) {
            return;
        }
        Player player = (Player)sender;
        Matcher matcher = this.pattern.matcher((CharSequence)tag);
        if (!matcher.matches()) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("invalid-tag-characters"));
            return;
        }
        this.plugin.getClanManager().createClan(player, tag);
    }

    @Execute(name="info")
    public void infoClan(@Context CommandSender sender, @Arg(value="tag") Clan clan) {
        if (!this.isPlayer(sender)) {
            return;
        }
        Player player = (Player)sender;
        this.plugin.getClanManager().infoClan(player, clan);
    }

    @Execute(name="setowner")
    public void setOwner(@Context Owner owner, @Arg(value="player") Player target) {
        this.plugin.getClanManager().setOwner(owner, target);
    }

    @Execute(name="deputy")
    public void setDeputy(@Context Owner owner, @Arg(value="player") Player target) {
        this.plugin.getClanManager().setDeputy(owner, target);
    }

    @Execute(name="removedeputy")
    public void removeDeputy(@Context CommandSender sender, @Arg Owner owner) {
        this.plugin.getClanManager().removeDeputy(owner);
    }

    @Execute(name="invite")
    public void inviteUser(@Context DeputyOwner deputyOwner, @Arg(value="player") Player target) {
        this.plugin.getClanManager().inviteUser(deputyOwner, target);
    }

    @Execute(name="kick")
    public void kickUser(@Context DeputyOwner deputyOwner, @Arg(value="nickname") String username) {
        this.plugin.getClanManager().kickUser(deputyOwner, username);
    }

    @Execute(name="alliance")
    public void alliace(@Context DeputyOwner deputyOwner, @Arg(value="tag") Clan clan) {
        this.plugin.getAllianceManager().alliance(deputyOwner, clan);
    }

    @Execute(name="join")
    public void joinClan(@Context Player player, @Arg(value="tag") Clan clan) {
        this.plugin.getClanManager().joinClan(player, clan);
    }

    @Execute(name="reload")
    @Permission(value={"getclan.admin"})
    public void reloadConfig(@Context CommandSender sender) {
        this.plugin.reloadPlugin(sender);
    }

    @Execute(name="admin join")
    @Permission(value={"getclan.admin"})
    public void adminForceJoinUser(@Context CommandSender sender, @Arg(value="player") User user, @Arg(value="tag") Clan clan) {
        this.plugin.getClanManager().forceJoin(sender, user, clan);
    }

    @Execute(name="admin kick")
    @Permission(value={"getclan.admin"})
    public void adminForceKickUser(@Context CommandSender sender, @Arg(value="gracz") User user) {
        this.plugin.getClanManager().forceKickUser(sender, user);
    }

    @Execute(name="pvp")
    public void changePvpStatusClan(@Context DeputyOwner deputyOwner) {
        this.plugin.getClanManager().changePvpStatus(deputyOwner);
    }

    @Execute(name="upgrade")
    public void upgradePanel(@Context Player player) {
        Optional<User> userByPlayer = this.plugin.getUserManager().findUserByPlayer(player);
        if (userByPlayer.isEmpty()) {
            return;
        }
        this.clanManager.openMenu(player, (User)userByPlayer.get());
    }

    @Execute(name="admin setitem")
    @Permission(value={"getclan.admin"})
    public void adminUpgradeItem(@Context Player player, @Arg(value="upgrade-type") UpgradeType upgradeType, @Arg(value="level") int level) {
        Optional<UpgradeCost> upgradeCostOpt = this.upgradeManager.findUpgradeCost(upgradeType, level);
        if (upgradeCostOpt.isEmpty()) {
            MessageUtil.sendMessage(player, "&cCannot find the upgrade type with this level!");
            return;
        }
        ItemStack itemStack = this.getItemStack(player);
        if (itemStack == null) {
            return;
        }
        UpgradeCost upgradeCost = (UpgradeCost)upgradeCostOpt.get();
        upgradeCost.setItemStack(itemStack);
        this.upgradeManager.save();
        MessageUtil.sendMessage(player, "&aSuccessful set the new item!");
    }

    @Execute(name="admin setleader")
    @Permission(value={"getclan.admin"})
    public void adminSetOwner(@Context CommandSender sender, @Arg(value="nickname") String username) {
        this.plugin.getClanManager().forceSetOwner(sender, username);
    }

    @Execute(name="admin reset all")
    @Permission(value={"getclan.admin"})
    public void adminReset(@Context CommandSender sender, @Arg(value="player") User user) {
        this.plugin.getUserManager().resetUser(user);
        MessageUtil.sendMessage(sender, "&aSuccessfully reset!");
    }

    @Execute(name="admin reset points")
    @Permission(value={"getclan.admin"})
    public void adminResetPoints(@Context CommandSender sender, @Arg(value="player") User user) {
        this.plugin.getUserManager().resetPoints(user);
        MessageUtil.sendMessage(sender, "&aPoints reset successfully!");
    }

    @Execute(name="admin reset kill")
    @Permission(value={"getclan.admin"})
    public void adminResetKill(@Context CommandSender sender, @Arg(value="player") User user) {
        this.plugin.getUserManager().resetKill(user);
        MessageUtil.sendMessage(sender, "&aKills reset successfully!");
    }

    @Execute(name="admin reset death")
    @Permission(value={"getclan.admin"})
    public void adminResetDeath(@Context CommandSender sender, @Arg(value="player") User user) {
        this.plugin.getUserManager().resetDeath(user);
        MessageUtil.sendMessage(sender, "&aDeaths reset successfully!");
    }

    @Execute(name="admin clan reset")
    @Permission(value={"getclan.admin"})
    public void adminClanReset(@Context CommandSender sender, @Arg(value="tag") Clan clan) {
        clan.getUpgrades().values().forEach(upgrade -> upgrade.reset());
        this.clanManager.updateItem(clan);
        MessageUtil.sendMessage(sender, "&aSuccessful reset upgrades of clan");
    }

    @Execute(name="admin debug")
    @Permission(value={"getclan.admin"})
    public void adminDebug(@Context CommandSender sender, @Arg(value="player") Player target) {
        User user = (User)this.plugin.getUserManager().getUserData().get((Object)target.getUniqueId());
        if (user == null || !user.hasClan()) {
            MessageUtil.sendMessage(sender, "&cPlayer doesn't have a clan!");
            return;
        }
        UserManager userManager = this.plugin.getUserManager();
        Clan clan = this.clanManager.getClan(user.getTag());
        clan.getMembers().forEach(memberUUID -> {
            User userTemp = (User)userManager.getUserData().get(memberUUID);
            MessageUtil.sendMessage(sender, "&7" + memberUUID + " -> " + userTemp.getPoints());
        });
    }

    @Execute(name="admin setitem")
    @Permission(value={"getclan.admin"})
    public void setItemCost(@Context Player player) {
        ItemStack itemStack = this.getItemStack(player);
        if (itemStack == null) {
            return;
        }
        this.fileManager.getConfig().setItemCost(itemStack);
        this.fileManager.getConfig().save();
        MessageUtil.sendMessage(player, "&aItem set successfully!");
    }

    @Execute(name="admin give")
    @Permission(value={"getclan.admin"})
    public void giveLevelItem(@Context CommandSender sender, @Arg(value="upgrade-type") UpgradeType upgradeType, @Arg(value="player") Player target, @Arg(value="level") int level, @Arg(value="amount") int amount) {
        Optional<UpgradeCost> upgradeCostOpt = this.upgradeManager.findUpgradeCost(upgradeType, level);
        if (upgradeCostOpt.isEmpty()) {
            MessageUtil.sendMessage(sender, "&cCannot find the upgrade type with this level!");
            return;
        }
        UpgradeCost upgradeCost = (UpgradeCost)upgradeCostOpt.get();
        ItemStack item = upgradeCost.getItemStack().clone();
        item.setAmount(amount);
        PlayerUtil.giveItem(target, item);
        MessageUtil.sendMessage(sender, "&aSuccessful give item!");
    }

    @Execute(name="admin give default")
    @Permission(value={"getclan.admin"})
    public void giveDefaultItem(@Context CommandSender sender, @Arg(value="player") Player target, @Arg(value="amount") int amount) {
        ItemStack item = this.fileManager.getConfig().getItemCost().clone();
        item.setAmount(amount);
        PlayerUtil.giveItem(target, item);
        MessageUtil.sendMessage(sender, "&aSuccessful give item!");
    }

    private ItemStack getItemStack(Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            MessageUtil.sendMessage(player, "&cYou need to hold the item in your hand!");
            return null;
        }
        ItemStack itemClone = itemInMainHand.clone();
        itemClone.setAmount(1);
        return itemClone;
    }

    @Execute(name="admin delete clan")
    @Permission(value={"getclan.admin"})
    public void adminRemove(@Context CommandSender sender, @Arg(value="player") Owner owner) {
        this.plugin.getClanManager().deleteClan(owner);
        MessageUtil.sendMessage(sender, "&aClan successfully removed!");
    }

    @Execute(name="admin delete clan")
    @Permission(value={"getclan.admin"})
    public void adminRemoveByTag(@Context CommandSender sender, @Arg(value="tag") Clan clan) {
        this.plugin.getClanManager().deleteClanByAdmin(clan);
        MessageUtil.sendMessage(sender, "&aClan successfully removed!");
    }

    @Execute(name="admin set points")
    @Permission(value={"getclan.admin"})
    public void adminSetPoint(@Context CommandSender sender, @Arg(value="player") User user, @Arg(value="points") int points) {
        user.setPoints(points);
        MessageUtil.sendMessage(sender, "&aNew points set successfully!");
    }

    @Execute(name="admin upgrade disable")
    @Permission(value={"getclan.admin"})
    public void disableUpgrade(@Context CommandSender sender) {
        this.fileManager.getUpgradesConfig().setUpgradeEnable(false);
        MessageUtil.sendMessage(sender, "&cDisabled the clan upgrade");
    }

    @Execute(name="admin upgrade enable")
    @Permission(value={"getclan.admin"})
    public void enableUpgrade(@Context CommandSender sender) {
        this.fileManager.getUpgradesConfig().setUpgradeEnable(true);
        MessageUtil.sendMessage(sender, "&aEnabled the clan upgrade");
    }

    @Execute(name="admin set upgrade")
    @Permission(value={"getclan.admin"})
    public void adminSetUpgrade(@Context CommandSender sender, @Arg(value="tag") Clan clan, @Arg(value="type") UpgradeType upgradeType, @Arg(value="level") int level) {
        Optional<Upgrade> upgradeByType = this.fileManager.getUpgradesConfig().findUpgradeByType(upgradeType);
        if (upgradeByType.isEmpty()) {
            MessageUtil.sendMessage(sender, "&cThis upgrade is not exists!");
            return;
        }
        Upgrade upgrade = (Upgrade)upgradeByType.get();
        UpgradeCost upgradeCost = (UpgradeCost)upgrade.getUpgradesCost().get((Object)level);
        if (upgradeCost == null) {
            MessageUtil.sendMessage(sender, "&cThat level of this upgrade not exists!");
            return;
        }
        LevelData levelData = (LevelData)clan.getUpgrades().get((Object)upgradeType);
        levelData.reset();
        levelData.setLevel(level);
        clan.setUpdate(true);
        MessageUtil.sendMessage(sender, "&aSuccessfully set the upgrade level for this clan!");
    }
}

