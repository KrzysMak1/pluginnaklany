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

@Command(name="getclan", aliases={"klan"})
@Permission(value={"getclan.use"})
public class ClanPLCommand {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final ClanManager clanManager;
    private final UpgradeManager upgradeManager;
    private final Pattern pattern = Pattern.compile((String)"^[a-zA-Z\u0105\u0107\u0119\u0142\u0144\u00f3\u015b\u017a\u017c\u0104\u0106\u0118\u0141\u0143\u00d3\u015a\u0179\u017b0-9]+$");

    public ClanPLCommand(GetClan plugin, FileManager fileManager, ClanManager clanManager, UpgradeManager upgradeManager) {
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

    @Execute(name="usun")
    public void deleteClan(@Context Owner owner) {
        this.plugin.getClanManager().deleteClan(owner);
    }

    @Execute(name="opusc")
    public void leaveClan(@Context Member member) {
        this.plugin.getClanManager().leaveClan(member);
    }

    @Execute(name="stworz")
    public void createClan(@Context CommandSender sender, @Arg(value="tag") String tag) {
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

    @Execute(name="ustawlidera")
    public void setOwner(@Context Owner owner, @Arg(value="gracz") Player target) {
        this.plugin.getClanManager().setOwner(owner, target);
    }

    @Execute(name="zastepca")
    public void setDeputy(@Context Owner owner, @Arg(value="gracz") Player target) {
        this.plugin.getClanManager().setDeputy(owner, target);
    }

    @Execute(name="usunzastepce")
    public void removeDeputy(@Context CommandSender sender, @Arg Owner owner) {
        this.plugin.getClanManager().removeDeputy(owner);
    }

    @Execute(name="zapros")
    public void inviteUser(@Context DeputyOwner deputyOwner, @Arg(value="gracz") Player target) {
        this.plugin.getClanManager().inviteUser(deputyOwner, target);
    }

    @Execute(name="wyrzuc")
    public void kickUser(@Context DeputyOwner deputyOwner, @Arg(value="nickname") String username) {
        this.plugin.getClanManager().kickUser(deputyOwner, username);
    }

    @Execute(name="sojusz")
    public void alliance(@Context DeputyOwner deputyOwner, @Arg(value="tag") Clan clan) {
        this.plugin.getAllianceManager().alliance(deputyOwner, clan);
    }

    @Execute(name="dolacz")
    public void joinClan(@Context Player player, @Arg(value="tag") Clan clan) {
        this.plugin.getClanManager().joinClan(player, clan);
    }

    @Execute(name="reload")
    @Permission(value={"getclan.admin"})
    public void reloadConfig(@Context CommandSender sender) {
        this.plugin.reloadPlugin(sender);
    }

    @Execute(name="admin dolacz")
    @Permission(value={"getclan.admin"})
    public void adminForceJoinUser(@Context CommandSender sender, @Arg(value="gracz") User user, @Arg(value="tag") Clan clan) {
        this.plugin.getClanManager().forceJoin(sender, user, clan);
    }

    @Execute(name="admin wyrzuc")
    @Permission(value={"getclan.admin"})
    public void adminForceKickUser(@Context CommandSender sender, @Arg(value="gracz") User user) {
        this.plugin.getClanManager().forceKickUser(sender, user);
    }

    @Execute(name="pvp")
    public void changePvpStatusClan(@Context DeputyOwner deputyOwner) {
        this.plugin.getClanManager().changePvpStatus(deputyOwner);
    }

    @Execute(name="ulepszenia")
    public void upgradePanel(@Context Player player) {
        Optional<User> userByPlayer = this.plugin.getUserManager().findUserByPlayer(player);
        if (userByPlayer.isEmpty()) {
            return;
        }
        this.clanManager.openMenu(player, (User)userByPlayer.get());
    }

    @Execute(name="admin ulepszenia off")
    @Permission(value={"getclan.admin"})
    public void disableUpgrade(@Context CommandSender sender) {
        this.fileManager.getUpgradesConfig().setUpgradeEnable(false);
        MessageUtil.sendMessage(sender, "&cWylaczono ulepszanie klanow!");
    }

    @Execute(name="admin ulepszenia on")
    @Permission(value={"getclan.admin"})
    public void enableUpgrade(@Context CommandSender sender) {
        this.fileManager.getUpgradesConfig().setUpgradeEnable(true);
        MessageUtil.sendMessage(sender, "&aWlaczono ulepszanie klanow!");
    }

    @Execute(name="admin setitem")
    @Permission(value={"getclan.admin"})
    public void adminUpgradeItem(@Context Player player, @Arg(value="typ-ulepszenia") UpgradeType upgradeType, @Arg(value="poziom") int level) {
        Optional<UpgradeCost> upgradeCostOpt = this.upgradeManager.findUpgradeCost(upgradeType, level);
        if (upgradeCostOpt.isEmpty()) {
            MessageUtil.sendMessage(player, "&cNie mo\u017cna znale\u017a\u0107 typu ulepszenia na tym poziomie!");
            return;
        }
        ItemStack itemStack = this.getItemStack(player);
        if (itemStack == null) {
            return;
        }
        UpgradeCost upgradeCost = (UpgradeCost)upgradeCostOpt.get();
        upgradeCost.setItemStack(itemStack);
        this.upgradeManager.save();
        MessageUtil.sendMessage(player, "&aPomy\u015blnie ustawiono nowy przedmiot!");
    }

    @Execute(name="admin ustawlidera")
    @Permission(value={"getclan.admin"})
    public void adminSetOwner(@Context CommandSender sender, @Arg(value="nickname") String username) {
        this.plugin.getClanManager().forceSetOwner(sender, username);
    }

    @Execute(name="admin resetuj *")
    @Permission(value={"getclan.admin"})
    public void adminReset(@Context CommandSender sender, @Arg(value="gracz") User user) {
        this.plugin.getUserManager().resetUser(user);
        MessageUtil.sendMessage(sender, "&aPomy\u015blnie zresetowano u\u017cytkownika!");
    }

    @Execute(name="admin resetuj punkty")
    @Permission(value={"getclan.admin"})
    public void adminResetPoints(@Context CommandSender sender, @Arg(value="gracz") User user) {
        this.plugin.getUserManager().resetPoints(user);
        MessageUtil.sendMessage(sender, "&aPunkty pomy\u015blnie zresetowane!");
    }

    @Execute(name="admin resetuj zabojstwa")
    @Permission(value={"getclan.admin"})
    public void adminResetKill(@Context CommandSender sender, @Arg(value="gracz") User user) {
        this.plugin.getUserManager().resetKill(user);
        MessageUtil.sendMessage(sender, "&aZab\u00f3jstwa pomy\u015blnie zresetowane!");
    }

    @Execute(name="admin resetuj smierci")
    @Permission(value={"getclan.admin"})
    public void adminResetDeath(@Context CommandSender sender, @Arg(value="gracz") User user) {
        this.plugin.getUserManager().resetDeath(user);
        MessageUtil.sendMessage(sender, "&a\u015amierci pomy\u015blnie zresetowane!");
    }

    @Execute(name="admin klan resetuj ulepszenia")
    @Permission(value={"getclan.admin"})
    public void adminClanReset(@Context CommandSender sender, @Arg(value="tag") Clan clan) {
        clan.getUpgrades().values().forEach(LevelData::reset);
        this.clanManager.updateItem(clan);
        MessageUtil.sendMessage(sender, "&aPomy\u015blnie zresetowano ulepszenia klanu");
    }

    @Execute(name="admin debug")
    @Permission(value={"getclan.admin"})
    public void adminDebug(@Context CommandSender sender, @Arg(value="gracz") Player target) {
        User user = (User)this.plugin.getUserManager().getUserData().get((Object)target.getUniqueId());
        if (user == null || !user.hasClan()) {
            MessageUtil.sendMessage(sender, "&cGracz nie nale\u017cy do \u017cadnego klanu!");
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
        MessageUtil.sendMessage(player, "&aPomy\u015blnie ustawiono przedmiot!");
    }

    @Execute(name="admin give")
    @Permission(value={"getclan.admin"})
    public void giveLevelItem(@Context CommandSender sender, @Arg(value="typ-ulepszenia") UpgradeType upgradeType, @Arg(value="gracz") Player target, @Arg(value="poziom") int level, @Arg(value="ilosc") int amount) {
        Optional<UpgradeCost> upgradeCostOpt = this.upgradeManager.findUpgradeCost(upgradeType, level);
        if (upgradeCostOpt.isEmpty()) {
            MessageUtil.sendMessage(sender, "&cNie mo\u017cna znale\u017a\u0107 typu ulepszenia na tym poziomie!");
            return;
        }
        UpgradeCost upgradeCost = (UpgradeCost)upgradeCostOpt.get();
        ItemStack item = upgradeCost.getItemStack().clone();
        item.setAmount(amount);
        PlayerUtil.giveItem(target, item);
        MessageUtil.sendMessage(sender, "&aPomy\u015blnie wr\u0119czono przedmiot!");
    }

    @Execute(name="admin give odlamek")
    @Permission(value={"getclan.admin"})
    public void giveDefaultItem(@Context CommandSender sender, @Arg(value="gracz") Player target, @Arg(value="ilosc") int amount) {
        ItemStack item = this.fileManager.getConfig().getItemCost().clone();
        item.setAmount(amount);
        PlayerUtil.giveItem(target, item);
        MessageUtil.sendMessage(sender, "&aPomy\u015blnie wr\u0119czono przedmiot!");
    }

    private ItemStack getItemStack(Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            MessageUtil.sendMessage(player, "&cMusisz trzyma\u0107 przedmiot w r\u0119ce!");
            return null;
        }
        ItemStack itemClone = itemInMainHand.clone();
        itemClone.setAmount(1);
        return itemClone;
    }

    @Execute(name="admin usun klan")
    @Permission(value={"getclan.admin"})
    public void adminRemove(@Context CommandSender sender, @Arg(value="gracz") Owner owner) {
        this.plugin.getClanManager().deleteClan(owner);
        MessageUtil.sendMessage(sender, "&aKlan pomy\u015blnie usuni\u0119ty!");
    }

    @Execute(name="admin usun klan")
    @Permission(value={"getclan.admin"})
    public void adminRemoveByTag(@Context CommandSender sender, @Arg(value="tag") Clan clan) {
        this.plugin.getClanManager().deleteClanByAdmin(clan);
        MessageUtil.sendMessage(sender, "&aKlan pomy\u015blnie usuni\u0119ty!");
    }

    @Execute(name="admin ustaw punkty")
    @Permission(value={"getclan.admin"})
    public void adminSetPoint(@Context CommandSender sender, @Arg(value="gracz") User user, @Arg(value="points") int points) {
        user.setPoints(points);
        MessageUtil.sendMessage(sender, "&aPunkty pomy\u015blnie ustawione!");
    }

    @Execute(name="admin ustaw ulepszenie")
    @Permission(value={"getclan.admin"})
    public void adminSetUpgrade(@Context CommandSender sender, @Arg(value="tag") Clan clan, @Arg(value="typ") UpgradeType upgradeType, @Arg(value="poziom") int level) {
        Optional<Upgrade> upgradeByType = this.fileManager.getUpgradesConfig().findUpgradeByType(upgradeType);
        if (upgradeByType.isEmpty()) {
            MessageUtil.sendMessage(sender, "&cTakie ulepszenie nie istnieje!");
            return;
        }
        Upgrade upgrade = (Upgrade)upgradeByType.get();
        UpgradeCost upgradeCost = (UpgradeCost)upgrade.getUpgradesCost().get((Object)level);
        if (upgradeCost == null) {
            MessageUtil.sendMessage(sender, "&cTakie poziom tego ulepszenia nie istnieje!");
            return;
        }
        LevelData levelData = (LevelData)clan.getUpgrades().get((Object)upgradeType);
        levelData.reset();
        levelData.setLevel(level);
        clan.setUpdate(true);
        MessageUtil.sendMessage(sender, "&aPomy\u015blnie ustawiono poziom ulepszenia dla tego klanu!");
    }
}

