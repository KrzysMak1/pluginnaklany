/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Optional
 *  java.util.Set
 *  java.util.UUID
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package dev.gether.getclan.core.user;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserService;
import dev.gether.getclan.event.PlayerInfoMessageEvent;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class UserManager {
    private HashMap<UUID, User> userData = new HashMap<>();
    private UserService userService;
    private final GetClan plugin;
    private final FileManager fileManager;

    public UserManager(UserService userService, GetClan plugin, FileManager fileManager) {
        this.userService = userService;
        this.plugin = plugin;
        this.fileManager = fileManager;
    }

    public void loadUser(Player player) {
        User user = this.userData.get(player.getUniqueId());
        if (user == null) {
            user = new User(player, this.fileManager.getConfig().getDefaultPoints());
            this.userData.put(player.getUniqueId(), user);
            this.userService.createUser(player, user.getPoints());
        }
    }

    public void updateUsers() {
        this.userData.values().forEach(user -> {
            if (user.isUpdate()) {
                this.update((User)user);
                user.setUpdate(false);
            }
        });
    }

    public void resetUser(User user) {
        user.setPoints(this.fileManager.getConfig().getDefaultPoints());
        user.resetKill();
        user.resetDeath();
    }

    public void loadUsers() {
        Set<User> users = this.userService.loadUsers();
        users.forEach(user -> {
            if (user.hasClan()) {
                Clan clan = this.plugin.getClanManager().getClan(user.getTag());
                if (clan == null) {
                    MessageUtil.logMessage("\u001b[0;31m", "Something is wrong! User has clan " + user.getTag() + ", but the clan doesn't exists!");
                    return;
                }
                if (!clan.isOwner(user.getUuid())) {
                    clan.addMember(user.getUuid());
                }
            }
            this.userData.put(user.getUuid(), user);
        });
    }

    public void update(User user) {
        this.userService.updateUser(user);
    }

    public void infoPlayer(Player player, User user) {
        int index = this.plugin.getRankingManager().findTopPlayerByName(user);
        String clanTag = user.hasClan() ? this.fileManager.getConfig().getFormatTag() : this.fileManager.getConfig().getNoneTag();
        String infoMessage = this.fileManager.getLangConfig().getMessage("info-user");
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("player", user.getName());
        placeholders.put("kills", String.valueOf((int)user.getKills()));
        placeholders.put("deaths", String.valueOf((int)user.getDeath()));
        placeholders.put("points", String.valueOf((int)user.getPoints()));
        placeholders.put("rank", String.valueOf((int)index));
        Map<String, Component> componentPlaceholders = new HashMap<>();
        componentPlaceholders.put("tag", this.plugin.getMessageService().parse(clanTag, Map.of("tag", user.getTag())));
        Player target = Bukkit.getPlayer((UUID)user.getUuid());
        if (target == null) {
            return;
        }
        PlayerInfoMessageEvent event = new PlayerInfoMessageEvent(target, infoMessage);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled()) {
            return;
        }
        MessageUtil.sendMessage(player, event.getMessage(), placeholders, componentPlaceholders);
    }

    public Optional<User> findUserByUUID(UUID uuid) {
        return Optional.ofNullable(this.userData.get(uuid));
    }

    public Optional<User> findUserByPlayer(Player player) {
        return this.findUserByUUID(player.getUniqueId());
    }

    public void resetPoints(User user) {
        user.setPoints(this.fileManager.getConfig().getDefaultPoints());
    }

    public void resetKill(User user) {
        user.resetKill();
    }

    public void resetDeath(User user) {
        user.resetDeath();
    }

    public HashMap<UUID, User> getUserData() {
        return this.userData;
    }
}
