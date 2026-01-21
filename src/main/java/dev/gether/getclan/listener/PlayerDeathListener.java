/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.HashMap
 *  java.util.Optional
 *  java.util.UUID
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getclan.listener;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.AntySystemRank;
import dev.gether.getclan.core.LastHitInfo;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.upgrade.LevelData;
import dev.gether.getclan.core.upgrade.Upgrade;
import dev.gether.getclan.core.upgrade.UpgradeCost;
import dev.gether.getclan.core.upgrade.UpgradeType;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getclan.event.PlayerNameEvent;
import dev.gether.getclan.event.PointsChangeUserEvent;
import dev.gether.getclan.utils.SystemPoint;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener
implements Listener {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final HashMap<UUID, AntySystemRank> antySystem = new HashMap<>();
    private final HashMap<UUID, LastHitInfo> lastHits = new HashMap<>();
    private final Function powFunction = new Function("pow", 2){

        @Override
        public double apply(double ... args) {
            return Math.pow((double)args[0], (double)args[1]);
        }
    };

    public PlayerDeathListener(GetClan plugin, FileManager fileManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Player victim;
        Entity entity;
        block3: {
            block2: {
                entity = event.getEntity();
                if (!(entity instanceof Player)) break block2;
                victim = (Player)entity;
                entity = event.getDamager();
                if (entity instanceof Player) break block3;
            }
            return;
        }
        Player attacker = (Player)entity;
        this.lastHits.put(victim.getUniqueId(), new LastHitInfo(attacker.getUniqueId(), System.currentTimeMillis() + (long)this.fileManager.getConfig().getKillCountDuration() * 1000L, attacker.getInventory().getItemInMainHand()));
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        UserManager userManager = this.plugin.getUserManager();
        User victimUser = (User)userManager.getUserData().get(victim.getUniqueId());
        if (victimUser == null) {
            return;
        }
        victimUser.increaseDeath();
        LastHitInfo lastHitInfo = this.lastHits.get(victim.getUniqueId());
        if (killer == null && (lastHitInfo == null || System.currentTimeMillis() >= lastHitInfo.getExpirationTime())) {
            this.handleSelfInflictedDeath(victim, event);
            return;
        }
        UUID killerUUID = killer != null ? killer.getUniqueId() : lastHitInfo.getAttackerUUID();
        User killerUser = (User)userManager.getUserData().get(killerUUID);
        if (killerUser == null) {
            return;
        }
        killerUser.increaseKill();
        OfflinePlayer offlineKiller = Bukkit.getOfflinePlayer((UUID)killerUUID);
        if (offlineKiller.isOnline() && this.fileManager.getConfig().isSystemAntiabuse() && this.handleAntiAbuse(victim, offlineKiller.getPlayer(), event)) {
            return;
        }
        this.handlePointsCalculation(victim, offlineKiller, victimUser, killerUser, lastHitInfo, event);
        this.lastHits.remove(victim.getUniqueId());
    }

    private void handleSelfInflictedDeath(Player player, PlayerDeathEvent event) {
        if (!this.fileManager.getConfig().isDeathMessage()) {
            return;
        }
        if (this.fileManager.getConfig().isTitleAlert()) {
            this.sendTitle(player, this.fileManager.getLangConfig().getMessage("death-self-inflicted-title"), this.fileManager.getLangConfig().getMessage("death-self-inflicted-subtitle"), Map.of());
        }
        event.setDeathMessage(MessageUtil.toLegacy(this.fileManager.getLangConfig().getMessage("death-self-inflicted"), Map.of("victim", player.getName())));
    }

    private boolean handleAntiAbuse(Player victim, Player killer, PlayerDeathEvent event) {
        String victimIp = victim.getAddress().getAddress().getHostAddress();
        String killerIp = killer.getAddress().getAddress().getHostAddress();
        AntySystemRank antySystemRank = this.antySystem.computeIfAbsent(killer.getUniqueId(), k -> new AntySystemRank(killerIp));
        boolean isKillable = antySystemRank.isPlayerKillable(victimIp, killer);
        if (!isKillable) {
            if (this.fileManager.getConfig().isDeathMessage()) {
                event.setDeathMessage("");
            }
            int second = SystemPoint.roundUpToMinutes(antySystemRank.getRemainingCooldown(victimIp));
            if (this.fileManager.getConfig().isTitleAlert()) {
                this.sendTitle(victim, this.fileManager.getLangConfig().getMessage("abuse-victim-title"), this.fileManager.getLangConfig().getMessage("abuse-victim-subtitle"), Map.of());
                this.sendTitle(killer, this.fileManager.getLangConfig().getMessage("abuse-killer-title"), this.fileManager.getLangConfig().getMessage("abuse-killer-subtitle"), Map.of());
            }
            MessageUtil.sendMessage(killer, this.fileManager.getLangConfig().getMessage("cooldown-kill"), Map.of("time", String.valueOf((int)second)));
            return true;
        }
        antySystemRank.addCooldown(victimIp, this.fileManager.getConfig().getCooldown());
        return false;
    }

    private void handlePointsCalculation(Player victim, OfflinePlayer killer, User victimUser, User killerUser, LastHitInfo lastHitInfo, PlayerDeathEvent event) {
        int pointsDeath = this.calculatePoints(victimUser.getPoints(), killerUser.getPoints(), 0);
        int pointsKiller = this.calculatePoints(killerUser.getPoints(), victimUser.getPoints(), 1);
        int deathPointTake = victimUser.getPoints() - pointsDeath;
        int killerPointAdd = pointsKiller - killerUser.getPoints();
        killerPointAdd = this.getPointsBoosted(killerUser, killerPointAdd);
        PointsChangeUserEvent pointsChangeUserEvent = new PointsChangeUserEvent(killer.getPlayer(), victim, killerPointAdd, deathPointTake);
        Bukkit.getPluginManager().callEvent((Event)pointsChangeUserEvent);
        if (pointsChangeUserEvent.isCancelled()) {
            return;
        }
        if (pointsDeath >= 0) {
            killerUser.addPoint(pointsChangeUserEvent.getPointKiller());
            victimUser.takePoint(pointsChangeUserEvent.getPointVictim());
        }
        if (!this.fileManager.getConfig().isDeathMessage()) {
            return;
        }
        this.updateDeathMessage(victim, killer, killerUser, lastHitInfo, pointsChangeUserEvent, event);
        if (this.fileManager.getConfig().isTitleAlert()) {
            this.updateTitles(victim, killer, pointsChangeUserEvent);
        }
    }

    private int calculatePoints(int oldRating, int opponentRating, int score) {
        String expression = this.fileManager.getConfig().getCalcPoints().replace((CharSequence)"{old_rating}", (CharSequence)String.valueOf((int)oldRating)).replace((CharSequence)"{opponent_rating}", (CharSequence)String.valueOf((int)opponentRating)).replace((CharSequence)"{score}", (CharSequence)String.valueOf((int)score));
        Expression pointExpression = new ExpressionBuilder(expression).functions(this.powFunction).build();
        try {
            return (int)pointExpression.evaluate();
        }
        catch (Exception e) {
            throw new RuntimeException("Error calculating points", (Throwable)e);
        }
    }

    private void updateDeathMessage(Player victim, OfflinePlayer killer, User killerUser, LastHitInfo lastHitInfo, PointsChangeUserEvent pointsChangeUserEvent, PlayerDeathEvent event) {
        PlayerNameEvent victimEvent = new PlayerNameEvent(victim.getName(), victim.getUniqueId());
        PlayerNameEvent killerEvent = new PlayerNameEvent(killerUser.getName(), killerUser.getUuid());
        Bukkit.getPluginManager().callEvent((Event)victimEvent);
        Bukkit.getPluginManager().callEvent((Event)killerEvent);
        String killerName = killer.isOnline() ? killerEvent.getPlayerName() : killerUser.getName();
        ItemStack itemStack = lastHitInfo.getItemStack();
        String weaponName = this.getWeaponName(itemStack);
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("victim", victimEvent.getPlayerName());
        placeholders.put("killer", killerName);
        placeholders.put("victim-points", String.valueOf((int)pointsChangeUserEvent.getPointVictim()));
        placeholders.put("killer-points", String.valueOf((int)pointsChangeUserEvent.getPointKiller()));
        placeholders.put("weapon", weaponName);
        event.setDeathMessage(MessageUtil.toLegacy(this.fileManager.getLangConfig().getMessage("death-info"), placeholders));
    }

    private String getWeaponName(ItemStack itemStack) {
        if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            return itemStack.getItemMeta().getDisplayName();
        }
        if (itemStack != null) {
            return this.fileManager.getConfig().getTranslate().getOrDefault(itemStack.getType(), itemStack.getType().name());
        }
        return "";
    }

    private void updateTitles(Player victim, OfflinePlayer killer, PointsChangeUserEvent pointsChangeUserEvent) {
        Map<String, String> killerPlaceholders = Map.of("killer-points", String.valueOf((int)pointsChangeUserEvent.getPointKiller()));
        if (killer.isOnline()) {
            this.sendTitle(killer.getPlayer(), this.fileManager.getLangConfig().getMessage("killer-title"), this.fileManager.getLangConfig().getMessage("killer-subtitle"), killerPlaceholders);
        }
        Map<String, String> victimPlaceholders = Map.of("victim-points", String.valueOf((int)pointsChangeUserEvent.getPointVictim()));
        this.sendTitle(victim, this.fileManager.getLangConfig().getMessage("victim-title"), this.fileManager.getLangConfig().getMessage("victim-subtitle"), victimPlaceholders);
    }

    private void sendTitle(Player player, String title, String subtitle, Map<String, String> placeholders) {
        player.sendTitle(MessageUtil.toLegacy(title, placeholders), MessageUtil.toLegacy(subtitle, placeholders), this.fileManager.getConfig().getFadeIn(), this.fileManager.getConfig().getStay(), this.fileManager.getConfig().getFadeOut());
    }

    private int getPointsBoosted(User userKiller, int killerPointAdd) {
        if (!userKiller.hasClan()) {
            return killerPointAdd;
        }
        Clan clan = this.plugin.getClanManager().getClan(userKiller.getTag());
        LevelData levelData = (LevelData)clan.getUpgrades().get((Object)UpgradeType.POINTS_BOOST);
        if (levelData == null) {
            return killerPointAdd;
        }
        Optional<Upgrade> upgradeByType = this.fileManager.getUpgradesConfig().findUpgradeByType(UpgradeType.POINTS_BOOST);
        if (upgradeByType.isEmpty() || !((Upgrade)upgradeByType.get()).isEnabled()) {
            return killerPointAdd;
        }
        UpgradeCost upgradeCost = (UpgradeCost)((Upgrade)upgradeByType.get()).getUpgradesCost().get((Object)levelData.getLevel());
        if (upgradeCost == null) {
            return killerPointAdd;
        }
        return (int)((1.0 + upgradeCost.getBoostValue()) * (double)killerPointAdd);
    }
}
