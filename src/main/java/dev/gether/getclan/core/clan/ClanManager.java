/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Optional
 *  java.util.Set
 *  java.util.UUID
 *  java.util.stream.Collectors
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package dev.gether.getclan.core.clan;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.cmd.context.domain.DeputyOwner;
import dev.gether.getclan.cmd.context.domain.Member;
import dev.gether.getclan.cmd.context.domain.Owner;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.CostType;
import dev.gether.getclan.core.alliance.AllianceService;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanService;
import dev.gether.getclan.core.upgrade.LevelData;
import dev.gether.getclan.core.upgrade.Upgrade;
import dev.gether.getclan.core.upgrade.UpgradeCost;
import dev.gether.getclan.core.upgrade.UpgradeType;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getclan.event.CancelInviteClanEvent;
import dev.gether.getclan.event.ChangeOwnerClanEvent;
import dev.gether.getclan.event.ClanMembersEvent;
import dev.gether.getclan.event.CreateClanEvent;
import dev.gether.getclan.event.DeleteClanEvent;
import dev.gether.getclan.event.DeleteDeputyEvent;
import dev.gether.getclan.event.DeputyChangeClanEvent;
import dev.gether.getclan.event.InviteClanEvent;
import dev.gether.getclan.event.JoinClanEvent;
import dev.gether.getclan.event.LeaveClanEvent;
import dev.gether.getclan.event.PlayerKickClanEvent;
import dev.gether.getconfig.utils.ItemUtil;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ClanManager {
    private final GetClan plugin;
    private final ClanService clanService;
    private final AllianceService allianceService;
    private final HashMap<String, Clan> clansData = new HashMap<>();
    private final FileManager fileManager;

    public ClanManager(GetClan plugin, ClanService clanService, AllianceService allianceService, FileManager fileManager) {
        this.plugin = plugin;
        this.clanService = clanService;
        this.allianceService = allianceService;
        this.fileManager = fileManager;
    }

    public void setOwner(Owner owner, Player target) {
        Clan clan = owner.getClan();
        Player player = owner.getPlayer();
        if (this.isSame(player, target.getUniqueId())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("cannot-change-to-yourself"));
            return;
        }
        if (!this.isYourClan(clan, target.getUniqueId())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-not-in-your-clan"));
            return;
        }
        boolean success = this.handleSetOwner(clan, target.getUniqueId());
        if (success) {
            MessageUtil.sendMessage(owner.getPlayer(), this.fileManager.getLangConfig().getMessage("change-leader"), Map.of("new-owner", target.getName()));
        }
    }

    private boolean handleSetOwner(Clan clan, UUID newOwnerUUID) {
        ChangeOwnerClanEvent event = new ChangeOwnerClanEvent(clan, clan.getOwnerUUID(), newOwnerUUID);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            clan.setOwner(newOwnerUUID);
            return true;
        }
        return false;
    }

    public void changePvpStatus(DeputyOwner deputyOwner) {
        Clan clan = deputyOwner.getClan();
        clan.togglePvp();
        clan.broadcast(this.fileManager.getLangConfig().getMessage(clan.isPvpEnable() ? "pvp-enabled" : "pvp-disabled"));
    }

    public void forceSetOwner(CommandSender sender, String username) {
        Optional<UUID> uuidOptional = this.getPlayerUUIDByNickname(username);
        if (uuidOptional.isEmpty()) {
            MessageUtil.sendMessage(sender, this.fileManager.getLangConfig().getMessage("player-not-found"));
            return;
        }
        UUID newOwnerUUID = (UUID)uuidOptional.get();
        User user = this.plugin.getUserManager().getUserData().get(newOwnerUUID);
        if (!user.hasClan()) {
            MessageUtil.sendMessage(sender, this.fileManager.getLangConfig().getMessage("admin-player-no-clan"));
            return;
        }
        String tag = user.getTag();
        Clan clan = this.getClan(tag);
        boolean success = this.handleSetOwner(clan, newOwnerUUID);
        if (success) {
            MessageUtil.sendMessage(sender, this.fileManager.getLangConfig().getMessage("admin-set-leader"));
        }
    }

    public void inviteUser(DeputyOwner deputyOwner, Player target) {
        Clan clan = deputyOwner.getClan();
        Player player = deputyOwner.getPlayer();
        if (this.isLimitMember(clan)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("clan-member-limit-reached"));
            return;
        }
        if (this.hasClan(target)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-already-in-clan"));
            return;
        }
        if (clan.hasInvite(target.getUniqueId())) {
            CancelInviteClanEvent event = new CancelInviteClanEvent(clan, target);
            Bukkit.getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                clan.cancelInvite(target.getUniqueId());
                MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("invite-cancelled"), Map.of("player", target.getName()));
            }
            return;
        }
        InviteClanEvent event = new InviteClanEvent(clan, target);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            clan.invite(target.getUniqueId());
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-invited"), Map.of("player", target.getName()));
            MessageUtil.sendMessage(target, this.fileManager.getLangConfig().getMessage("clan-invitation-received"), Map.of("tag", clan.getTag()));
        }
    }

    public void openMenu(Player player, User user) {
        if (!this.fileManager.getUpgradesConfig().isUpgradeEnable()) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("upgrade-clan-disabled"));
            return;
        }
        if (!user.hasClan()) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-has-no-clan"));
            return;
        }
        Clan clan = this.getClan(user.getTag());
        player.openInventory(clan.getInventory());
    }

    private boolean hasClan(Player target) {
        User user = this.plugin.getUserManager().getUserData().get(target.getUniqueId());
        return user.hasClan();
    }

    private boolean isLimitMember(Clan clan) {
        if (!this.fileManager.getUpgradesConfig().isUpgradeEnable()) {
            return clan.getMembers().size() >= this.getMaxMember(clan);
        }
        LevelData levelData = clan.getUpgrades().get(UpgradeType.MEMBERS);
        if (levelData == null) {
            return true;
        }
        Optional<Upgrade> upgradeByType = this.fileManager.getUpgradesConfig().findUpgradeByType(UpgradeType.MEMBERS);
        if (upgradeByType.isEmpty()) {
            return true;
        }
        Upgrade upgrade = (Upgrade)upgradeByType.get();
        if (!upgrade.isEnabled()) {
            return clan.getMembers().size() >= this.getMaxMember(clan);
        }
        UpgradeCost upgradeCost = upgrade.getUpgradesCost().get(levelData.getLevel());
        if (upgradeCost == null) {
            return true;
        }
        return (int)upgradeCost.getBoostValue() < clan.getMembers().size();
    }

    public int getMaxMember(Clan clan) {
        int ownerMax = this.getUserMaxMember(clan.getOwnerUUID());
        int deputyOwnerMax = this.getUserMaxMember(clan.getDeputyOwnerUUID());
        return Math.max((int)ownerMax, (int)deputyOwnerMax);
    }

    private int getUserMaxMember(UUID uuid) {
        if (uuid == null) {
            return 0;
        }
        Player player = Bukkit.getPlayer((UUID)uuid);
        if (player != null) {
            Map<String, Integer> permissionLimitMember = this.fileManager.getConfig().permissionLimitMember;
            for (Map.Entry permissionData : permissionLimitMember.entrySet()) {
                String permission = (String)permissionData.getKey();
                int max = (Integer)permissionData.getValue();
                if (!player.hasPermission(permission)) continue;
                return max;
            }
        }
        return 0;
    }

    public void infoClan(Player player, Clan clan) {
        int index = this.plugin.getRankingManager().findTopClan(clan);
        String infoMessage = String.join((CharSequence)"\n", (CharSequence[])new CharSequence[]{this.fileManager.getLangConfig().getMessage("info-clan")});
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("tag", clan.getTag());
        placeholders.put("owner", this.getPlayerName(clan.getOwnerUUID()));
        placeholders.put("deputy-owner", this.getPlayerName(clan.getDeputyOwnerUUID()));
        placeholders.put("points", this.getAveragePoint(player));
        placeholders.put("members-online", String.valueOf((int)this.countOnlineMember(clan)));
        placeholders.put("members-size", String.valueOf((int)clan.getMembers().size()));
        placeholders.put("rank", String.valueOf((int)index));
        HashMap<String, Component> componentPlaceholders = new HashMap<>();
        componentPlaceholders.put("members", this.plugin.getMessageService().parse(this.getClanMembers(clan), Map.of()));
        MessageUtil.sendMessage(player, infoMessage, placeholders, componentPlaceholders);
    }

    private List<Player> getClanPlayerOnline(Clan clan) {
        ArrayList onlinePlayers = new ArrayList();
        clan.getMembers().forEach(arg_0 -> ClanManager.lambda$getClanPlayerOnline$0((List)onlinePlayers, arg_0));
        return onlinePlayers;
    }

    private String getClanMembers(Clan clan) {
        List<UUID> members = clan.getMembers();
        List<Player> onlinePlayers = this.getClanPlayerOnline(clan);
        ClanMembersEvent event = new ClanMembersEvent(onlinePlayers);
        Bukkit.getPluginManager().callEvent((Event)event);
        return members.stream().map(uuid -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            String color = this.fileManager.getConfig().getColorOfflinePlayer();
            if (player.isOnline() && event.getPlayersOnline().contains(player.getPlayer())) {
                color = this.fileManager.getConfig().getColorOnlinePlayer();
            }
            return color + player.getName();
        }).collect(Collectors.joining(", "));
    }

    public int countOnlineMember(Clan clan) {
        int online = 0;
        List<Player> playerOnline = this.getClanPlayerOnline(clan);
        for (UUID uuid : clan.getMembers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !playerOnline.contains(player)) continue;
            ++online;
        }
        return online;
    }

    public String getAveragePoint(Player player) {
        UserManager userManager = this.plugin.getUserManager();
        User user = userManager.getUserData().get(player.getUniqueId());
        if (user == null || !user.hasClan()) {
            return this.fileManager.getConfig().getNonePointsClan();
        }
        Clan clan = this.getClan(user.getTag());
        if (!this.doesClanFulfillThreshold(clan)) {
            return MessageUtil.toLegacy(this.fileManager.getConfig().getPlaceholderNeedMembers(), java.util.Map.of());
        }
        return this.getAveragePoint(clan);
    }

    public String getAveragePoint(Clan clan) {
        List<UUID> members = clan.getMembers();
        int sum = 0;
        int count = 0;
        UserManager userManager = this.plugin.getUserManager();
        for (UUID uuid : members) {
            User tempUser = userManager.getUserData().get(uuid);
            if (tempUser == null) {
                OfflinePlayer player = Bukkit.getOfflinePlayer((UUID)uuid);
                this.plugin.getLogger().info("\u001b[0;31mBl\u0105d - Gracz o nazwie " + player.getName() + "  nalezy do klanu " + clan.getTag() + " ale nie znajduje go jako obiekt User");
                continue;
            }
            sum += tempUser.getPoints();
            ++count;
        }
        if (count == 0) {
            throw new RuntimeException("Cannot division through 0");
        }
        double average = (double)sum / (double)count;
        return String.valueOf((int)((int)average));
    }

    private String getPlayerName(UUID uuid) {
        if (uuid == null) {
            return this.fileManager.getConfig().getNoneDeputy();
        }
        return Bukkit.getOfflinePlayer((UUID)uuid).getName();
    }

    public void joinClan(Player player, Clan clan) {
        User user = this.plugin.getUserManager().getUserData().get(player.getUniqueId());
        if (user.hasClan()) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-already-has-clan"));
            return;
        }
        if (!clan.hasInvite(player.getUniqueId())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("no-invitation"));
            return;
        }
        if (this.isLimitMember(clan)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("clan-member-limit-reached"));
            return;
        }
        this.joinClanCheckEvent(player, user, clan);
    }

    public void forceJoin(CommandSender sender, User user, Clan clan) {
        Player player = Bukkit.getPlayer((UUID)user.getUuid());
        if (user.hasClan()) {
            MessageUtil.sendMessage(sender, this.fileManager.getLangConfig().getMessage("admin-player-has-clan"));
            return;
        }
        this.joinClanCheckEvent(player, user, clan);
    }

    private void joinClanCheckEvent(Player player, User user, Clan clan) {
        JoinClanEvent event = new JoinClanEvent(clan, player);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            user.setTag(clan.getTag());
            clan.joinUser(player.getUniqueId());
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-joined-clan"));
            return;
        }
    }

    public void deleteClan(Owner owner) {
        Clan clan = owner.getClan();
        Player player = owner.getPlayer();
        this.handleDeleteClan(clan, player);
    }

    public void deleteClanByAdmin(Clan clan) {
        this.handleDeleteClan(clan, null);
    }

    private boolean handleDeleteClan(Clan clan, Player player) {
        if (player == null) {
            return this.deleteClan(clan, null, " ");
        }
        DeleteClanEvent event = new DeleteClanEvent(player, clan);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            return this.deleteClan(clan, player, event.getPlayerName());
        }
        return false;
    }

    private boolean deleteClan(Clan clan, Player player, String playerName) {
        String tag = clan.getTag();
        ClanManager clansManager = this.plugin.getClanManager();
        for (UUID uuid : clan.getMembers()) {
            User member = this.plugin.getUserManager().getUserData().get(uuid);
            member.setTag(null);
        }
        for (String allianceTag : clan.getAlliances()) {
            Clan allianceClan = clansManager.getClansData().get(allianceTag);
            allianceClan.getAlliances().remove(tag);
            this.allianceService.deleteAlliance(tag);
        }
        this.clanService.deleteClan(tag);
        this.deleteClan(tag);
        this.plugin.getRankingManager().removeClan(clan);
        if (player != null) {
            MessageUtil.broadcast(this.fileManager.getLangConfig().getMessage("clan-deleted"), Map.of("tag", tag, "player", playerName));
        }
        return true;
    }

    public void createClan(Player player, String tag) {
        boolean status;
        if (this.hasClan(player)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-already-has-clan"));
            return;
        }
        int min = this.fileManager.getConfig().getClansTagLengthMin();
        int max = this.fileManager.getConfig().getClansTagLengthMax();
        if (tag.length() < min || tag.length() > max) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("clan-name-length-info"), Map.of("min-length", String.valueOf((int)min), "max-length", String.valueOf((int)max)));
            return;
        }
        if (this.tagIsBusy(tag)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("clan-name-exists"));
            return;
        }
        User user = this.plugin.getUserManager().getUserData().get(player.getUniqueId());
        if (user == null) {
            return;
        }
        if (this.fileManager.getConfig().isEnablePayment() && !(status = this.checkPayments(player))) {
            return;
        }
        CreateClanEvent event = new CreateClanEvent(player, tag);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            Clan clan = new Clan(tag, UUID.randomUUID(), player.getUniqueId(), this.fileManager.getConfig().isPvpClan(), this.fileManager.getUpgradesConfig());
            this.plugin.getClanManager().updateItem(clan);
            this.clansData.put(tag, clan);
            user.setTag(clan.getTag());
            this.plugin.getUserManager().update(user);
            this.clanService.createClan(clan, player);
            this.plugin.getRankingManager().addClan(clan);
            MessageUtil.broadcast(this.fileManager.getLangConfig().getMessage("clan-created"), Map.of("tag", tag, "player", event.getPlayerName()));
        }
    }

    public void updateItem(Clan clan) {
        this.fileManager.getUpgradesConfig().getUpgrades().forEach(upgrade -> {
            if (!upgrade.isEnabled()) {
                return;
            }
            LevelData levelData = clan.getUpgrades().get(upgrade.getUpgradeType());
            if (levelData == null) {
                return;
            }
            UpgradeCost upgradeCost = upgrade.getUpgradesCost().get(levelData.getLevel());
            if (upgradeCost == null) {
                return;
            }
            double needAmount = 0.0;
            UpgradeCost nextLevel = upgrade.getUpgradesCost().get(levelData.getLevel() + 1);
            if (nextLevel != null) {
                needAmount = nextLevel.getCost();
            }
            this.plugin.getClanManager().updateItem(clan, upgradeCost, levelData, needAmount, upgrade.getSlot());
        });
    }

    private boolean checkPayments(Player player) {
        int needAmount;
        if (this.fileManager.getConfig().getCostType() == CostType.VAULT) {
            Economy economy = this.plugin.getEconomy();
            if (!economy.has((OfflinePlayer)player, this.fileManager.getConfig().getCostCreate())) {
                MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("clan-cost-vault"), Map.of("cost", String.valueOf((double)this.fileManager.getConfig().getCostCreate())));
                return false;
            }
            economy.withdrawPlayer((OfflinePlayer)player, this.fileManager.getConfig().getCostCreate());
            return true;
        }
        int amount = ItemUtil.calcItem(player, this.fileManager.getConfig().getItemCost());
        if (amount < (needAmount = (int)this.fileManager.getConfig().getCostCreate())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("clan-cost-item"), Map.of("amount", String.valueOf((int)needAmount)));
            return false;
        }
        ItemUtil.removeItem(player, this.fileManager.getConfig().getItemCost(), needAmount);
        return true;
    }

    public void forceKickUser(CommandSender sender, User user) {
        if (!user.hasClan()) {
            MessageUtil.sendMessage(sender, this.fileManager.getLangConfig().getMessage("admin-player-no-clan"));
            return;
        }
        Clan clan = this.getClan(user.getTag());
        this.handleKickUser(null, user, clan);
    }

    public void kickUser(DeputyOwner deputyOwner, String nickname) {
        Clan clan = deputyOwner.getClan();
        Player player = deputyOwner.getPlayer();
        Optional<UUID> optionalUUID = this.getPlayerUUIDByNickname(nickname);
        if (optionalUUID.isEmpty()) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-not-found"));
            return;
        }
        UUID targetUUID = (UUID)optionalUUID.get();
        if (!this.isYourClan(clan, targetUUID)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-not-in-your-clan"));
            return;
        }
        if (this.isSame(player, targetUUID)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("cannot-kick-yourself"));
            return;
        }
        if (this.isOwner(clan, targetUUID)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("cannot-kick-owner"));
            return;
        }
        User kUser = this.plugin.getUserManager().getUserData().get(targetUUID);
        this.handleKickUser(player, kUser, clan);
    }

    private void handleKickUser(Player player, User kickedUser, Clan clan) {
        PlayerKickClanEvent event = new PlayerKickClanEvent(clan, kickedUser.getUuid());
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            clan.removeMember(kickedUser.getUuid());
            kickedUser.setTag(null);
            if (player != null) {
                MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-kicked-from-clan"));
            }
        }
    }

    private Optional<UUID> getPlayerUUIDByNickname(String nickname) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer((String)nickname);
        if (offlinePlayer != null) {
            return Optional.of(offlinePlayer.getUniqueId());
        }
        Player player = Bukkit.getPlayer((String)nickname);
        if (player == null) {
            return Optional.empty();
        }
        return Optional.of(player.getUniqueId());
    }

    private boolean isSame(Player player, UUID targetUUID) {
        return player.getUniqueId().equals(targetUUID);
    }

    public void leaveClan(Member member) {
        Player player;
        Clan clan = member.getClan();
        if (this.isOwner(clan, (player = member.getPlayer()).getUniqueId())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("owner-cannot-leave"));
            return;
        }
        User user = this.plugin.getUserManager().getUserData().get(player.getUniqueId());
        LeaveClanEvent event = new LeaveClanEvent(player, clan);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            clan.removeMember(player.getUniqueId());
            user.setTag(null);
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-left-clan"));
        }
    }

    public boolean isYourClan(Clan clan, UUID uuid) {
        return clan.getMembers().contains(uuid);
    }

    private boolean isOwner(Clan clan, UUID playerUUID) {
        return clan.getOwnerUUID().equals(playerUUID);
    }

    private boolean tagIsBusy(String tag) {
        return this.clansData.get(tag) != null;
    }

    public void removeDeputy(Owner owner) {
        Clan clan = owner.getClan();
        Player player = owner.getPlayer();
        if (this.deputyIsEmpty(clan)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("clan-has-no-deputy"));
            return;
        }
        DeleteDeputyEvent event = new DeleteDeputyEvent(clan, clan.getDeputyOwnerUUID());
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            clan.setDeputyOwnerUUID(null);
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("deputy-removed"));
        }
    }

    private boolean deputyIsEmpty(Clan clan) {
        return clan.getDeputyOwnerUUID() == null;
    }

    public void setDeputy(Owner owner, Player target) {
        Player player = owner.getPlayer();
        User user = this.plugin.getUserManager().getUserData().get(player.getUniqueId());
        Clan clan = this.getClan(user.getTag());
        if (!this.isYourClan(clan, target.getUniqueId())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-not-in-your-clan"));
            return;
        }
        if (clan.isDeputy(target.getUniqueId())) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("player-is-deputy"));
            return;
        }
        DeputyChangeClanEvent event = new DeputyChangeClanEvent(clan, player, target);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            clan.setDeputyOwnerUUID(target.getUniqueId());
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("deputy-set"));
        }
    }

    public void updateClans() {
        this.clansData.values().forEach(clan -> {
            if (clan.isUpdate()) {
                this.clanService.updateClan((Clan)clan);
                clan.setUpdate(false);
            }
        });
    }

    public boolean doesClanFulfillThreshold(Clan clan) {
        return clan.getMembers().size() >= this.fileManager.getConfig().getMembersRequiredForRanking();
    }

    public boolean isLimitAlliance(Clan clan) {
        return clan.getAlliances().size() >= this.fileManager.getConfig().getLimitAlliance();
    }

    public Clan getClan(String tag) {
        return this.clansData.get(tag);
    }

    private Clan deleteClan(String tag) {
        return this.clansData.remove(tag);
    }

    public Map<String, Clan> getClansData() {
        return this.clansData;
    }

    public void loadClans() {
        Set<Clan> clans = this.clanService.loadClans();
        clans.forEach(clan -> this.clansData.put(clan.getTag(), clan));
    }

    public void clickInv(Player player, Clan clan, int slot, @NotNull ClickType clickType) {
        Optional<Upgrade> upgradeTypeBySlot = this.fileManager.getUpgradesConfig().findUpgradeTypeBySlot(slot);
        if (upgradeTypeBySlot.isEmpty()) {
            return;
        }
        Upgrade upgrade = (Upgrade)upgradeTypeBySlot.get();
        if (!upgrade.isEnabled()) {
            return;
        }
        LevelData levelData = clan.getUpgrades().get(upgrade.getUpgradeType());
        if (levelData == null) {
            return;
        }
        UpgradeCost upgradeCost = upgrade.getUpgradesCost().get(levelData.getLevel() + 1);
        if (upgradeCost == null) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("upgrade-max-level"));
            return;
        }
        double need = upgradeCost.getCost() - levelData.getDepositAmount();
        boolean update = false;
        if (clickType == ClickType.LEFT) {
            update = this.deposit(player, levelData, upgradeCost, need > 1.0 ? 1.0 : need, upgradeCost.getCostType());
        } else if (clickType == ClickType.SHIFT_LEFT) {
            update = this.deposit(player, levelData, upgradeCost, need, upgradeCost.getCostType());
        }
        if (update) {
            clan.setUpdate(true);
        }
        if (this.nextLevel(upgradeCost, levelData)) {
            levelData.nextLevel();
            this.updateItem(clan, upgradeCost, levelData, upgradeCost.getCost(), upgrade.getSlot());
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("upgrade-successful-upgrade"));
        } else {
            UpgradeCost actuallyItem = upgrade.getUpgradesCost().get(levelData.getLevel());
            this.updateItem(clan, actuallyItem, levelData, upgradeCost.getCost(), upgrade.getSlot());
        }
    }

    private boolean nextLevel(UpgradeCost upgradeCost, LevelData levelData) {
        return upgradeCost.getCost() <= levelData.getDepositAmount();
    }

    private void updateItem(Clan clan, UpgradeCost upgradeCost, LevelData levelData, double needAmount, int slot) {
        Inventory inventory = clan.getInventory();
        ItemStack itemStack = upgradeCost.getItem().getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList lore = new ArrayList();
        if (itemMeta.hasLore()) {
            lore.addAll((Collection)itemMeta.getLore());
        }
        for (int i = 0; i < lore.size(); ++i) {
            String line = (String)lore.get(i);
            lore.set(i, MessageUtil.toLegacy(line, Map.of("amount", this.getFormattedNumber(levelData.getDepositAmount()), "need-amount", this.getFormattedNumber(needAmount))));
        }
        itemMeta.setLore((List<String>)lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, ItemUtil.hideAttribute(itemStack));
    }

    private String getFormattedNumber(double number) {
        String formattedNumber = number % 1.0 == 0.0 ? String.format((String)"%.0f", (Object[])new Object[]{number}) : String.format((String)"%.2f", (Object[])new Object[]{number});
        return formattedNumber;
    }

    private boolean deposit(Player player, LevelData levelData, UpgradeCost upgradeCost, double amount, CostType costType) {
        int needAmount;
        if (costType == CostType.VAULT) {
            Economy economy = this.plugin.getEconomy();
            if (!economy.has((OfflinePlayer)player, amount)) {
                MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("upgrade-cost-vault"), Map.of("cost", String.valueOf((double)amount)));
                return false;
            }
            economy.withdrawPlayer((OfflinePlayer)player, amount);
            levelData.deposit(amount);
            return true;
        }
        int calcAmount = ItemUtil.calcItem(player, upgradeCost.getItemStack());
        if (calcAmount < (needAmount = (int)amount)) {
            MessageUtil.sendMessage(player, this.fileManager.getLangConfig().getMessage("upgrade-cost-item"), Map.of("amount", String.valueOf((int)needAmount), "item", ItemUtil.getItemName(upgradeCost.getItemStack())));
            return false;
        }
        ItemUtil.removeItem(player, upgradeCost.getItemStack(), needAmount);
        levelData.deposit(amount);
        return true;
    }

    private static /* synthetic */ void lambda$getClanPlayerOnline$0(List onlinePlayers, UUID uuid) {
        OfflinePlayer playerOffline = Bukkit.getOfflinePlayer((UUID)uuid);
        if (playerOffline.isOnline()) {
            onlinePlayers.add(playerOffline.getPlayer());
        }
    }
}
