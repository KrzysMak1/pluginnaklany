/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.List
 *  java.util.Map
 *  java.util.UUID
 *  java.util.stream.Collectors
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getclan.core.clan;

import dev.gether.getclan.config.domain.UpgradesConfig;
import dev.gether.getclan.core.upgrade.LevelData;
import dev.gether.getclan.core.upgrade.UpgradeType;
import dev.gether.getconfig.utils.ItemUtil;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Clan {
    private String tag;
    private UUID uuid;
    private UUID ownerUUID;
    private UUID deputyOwnerUUID;
    private List<UUID> invitedPlayers = new ArrayList();
    private List<UUID> members = new ArrayList();
    private List<String> alliances = new ArrayList();
    private List<String> inviteAlliances = new ArrayList();
    private Map<UpgradeType, LevelData> upgrades;
    private boolean pvpEnable;
    private boolean update = false;
    private Inventory inventory;

    public Clan(String tag, UUID uuid, UUID ownerUUID, UUID deputyOwnerUUID, boolean pvpEnable, UpgradesConfig upgradesConfig, Map<UpgradeType, LevelData> upgrades) {
        this(tag, uuid, ownerUUID, pvpEnable, upgradesConfig);
        this.deputyOwnerUUID = deputyOwnerUUID;
        this.upgrades = upgrades;
    }

    public Clan(String tag, UUID uuid, UUID ownerUUID, boolean pvpEnable, UpgradesConfig upgradesConfig) {
        this.tag = tag;
        this.uuid = uuid;
        this.ownerUUID = ownerUUID;
        this.members.add(ownerUUID);
        this.pvpEnable = pvpEnable;
        this.upgrades = Arrays.stream(UpgradeType.values()).collect(Collectors.toMap(type -> type, type -> new LevelData(0, 0.0)));
        this.inventory = Bukkit.createInventory(null, (int)upgradesConfig.getInventoryBase().getSize(), (String)MessageUtil.toLegacy(upgradesConfig.getInventoryBase().getTitle(), java.util.Map.of()));
        upgradesConfig.getInventoryBase().getItemsDecoration().forEach(itemDecoration -> {
            ItemStack itemStack = ItemUtil.hideAttribute(itemDecoration.getItemStack());
            itemDecoration.getSlots().forEach(slot -> this.inventory.setItem(slot.intValue(), itemStack));
        });
    }

    public boolean isAlliance(String tag) {
        return this.alliances.contains(tag.toUpperCase());
    }

    public boolean isMember(UUID uuid) {
        return this.members.contains(uuid);
    }

    public boolean removeAlliance(String tag) {
        return this.alliances.remove(tag.toUpperCase());
    }

    public void addAlliance(String tag) {
        this.alliances.add(tag.toUpperCase());
    }

    public boolean hasInvite(UUID uuid) {
        return this.invitedPlayers.contains(uuid);
    }

    public boolean isPvpEnable() {
        return this.pvpEnable;
    }

    public void joinUser(UUID uuid) {
        this.invitedPlayers.remove(uuid);
        this.members.add(uuid);
    }

    public void resetInvite() {
        this.inviteAlliances.clear();
        this.invitedPlayers.clear();
    }

    public void removeMember(UUID uuid) {
        if (this.deputyOwnerUUID != null && this.deputyOwnerUUID.equals(uuid)) {
            this.deputyOwnerUUID = null;
        }
        this.members.remove(uuid);
    }

    public void broadcast(String message) {
        this.members.forEach(memberUUID -> {
            Player player = Bukkit.getPlayer((UUID)memberUUID);
            if (player != null) {
                MessageUtil.sendMessage(player, message);
            }
        });
    }

    public void broadcast(String message, java.util.Map<String, String> placeholders) {
        this.members.forEach(memberUUID -> {
            Player player = Bukkit.getPlayer((UUID)memberUUID);
            if (player != null) {
                MessageUtil.sendMessage(player, message, placeholders);
            }
        });
    }

    public void invite(UUID uuid) {
        this.invitedPlayers.add(uuid);
    }

    public void cancelInvite(UUID uuid) {
        this.invitedPlayers.remove(uuid);
    }

    public List<UUID> getMembers() {
        return this.members;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public UUID getDeputyOwnerUUID() {
        return this.deputyOwnerUUID;
    }

    public List<String> getAlliances() {
        return this.alliances;
    }

    public String getTag() {
        return this.tag;
    }

    public void setDeputyOwnerUUID(UUID deputyOwnerUUID) {
        this.deputyOwnerUUID = deputyOwnerUUID;
        this.update = true;
    }

    public void setOwner(UUID newOwnerUUID) {
        this.ownerUUID = newOwnerUUID;
        this.update = true;
    }

    public boolean isSuggestAlliance(String tag) {
        return this.inviteAlliances.contains(tag);
    }

    public boolean inviteAlliance(String tag) {
        return this.inviteAlliances.add(tag);
    }

    public void removeInviteAlliance(String tag) {
        this.inviteAlliances.remove(tag);
    }

    public void removeSuggestAlliance(String tag) {
        this.inviteAlliances.remove(tag);
    }

    public boolean isOwner(UUID uuid) {
        return this.ownerUUID.equals(uuid);
    }

    public void addMember(UUID uuid) {
        this.members.add(uuid);
    }

    public boolean isDeputy(UUID uniqueId) {
        return this.deputyOwnerUUID != null && this.deputyOwnerUUID.equals(uniqueId);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void togglePvp() {
        this.pvpEnable = !this.pvpEnable;
        this.update = true;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Map<UpgradeType, LevelData> getUpgrades() {
        return this.upgrades;
    }
}
