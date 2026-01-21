/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.stream.Stream
 *  lombok.Generated
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package dev.gether.getclan;

import dev.gether.getclan.bstats.Metrics;
import dev.gether.getclan.cmd.ClanENCommand;
import dev.gether.getclan.cmd.ClanPLCommand;
import dev.gether.getclan.cmd.PlayerENCommand;
import dev.gether.getclan.cmd.PlayerPLCommand;
import dev.gether.getclan.cmd.argument.ClanTagArgument;
import dev.gether.getclan.cmd.argument.OwnerArgument;
import dev.gether.getclan.cmd.argument.UserArgument;
import dev.gether.getclan.cmd.context.DeputyOwnerContextual;
import dev.gether.getclan.cmd.context.MemberContextual;
import dev.gether.getclan.cmd.context.OwnerContextual;
import dev.gether.getclan.cmd.context.domain.DeputyOwner;
import dev.gether.getclan.cmd.context.domain.Member;
import dev.gether.getclan.cmd.context.domain.Owner;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.config.domain.LangType;
import dev.gether.getclan.core.CooldownManager;
import dev.gether.getclan.core.alliance.AllianceManager;
import dev.gether.getclan.core.alliance.AllianceService;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.clan.ClanService;
import dev.gether.getclan.core.upgrade.UpgradeManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getclan.core.user.UserService;
import dev.gether.getclan.database.MySQL;
import dev.gether.getclan.handler.CustomInvalidUsage;
import dev.gether.getclan.handler.PermissionMessage;
import dev.gether.getclan.listener.AsyncPlayerChatListener;
import dev.gether.getclan.listener.BreakBlockListener;
import dev.gether.getclan.listener.EntityDamageListener;
import dev.gether.getclan.listener.InventoryClickListener;
import dev.gether.getclan.listener.PlayerConnectionListener;
import dev.gether.getclan.listener.PlayerDeathListener;
import dev.gether.getclan.listener.PlayerInteractionEntityListener;
import dev.gether.getclan.message.MessageService;
import dev.gether.getclan.placeholder.ClanPlaceholder;
import dev.gether.getclan.ranking.RankingManager;
import dev.gether.getconfig.utils.MessageUtil;
import dev.gether.shaded.litecommands.LiteCommands;
import dev.gether.shaded.litecommands.argument.resolver.ArgumentResolverBase;
import dev.gether.shaded.litecommands.bukkit.LiteBukkitFactory;
import dev.gether.shaded.litecommands.bukkit.LiteBukkitMessages;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Generated;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class GetClan
extends JavaPlugin {
    private static GetClan instance;
    private Economy economy;
    private UserManager userManager;
    private ClanManager clanManager;
    private CooldownManager cooldownManager;
    private AllianceManager allianceManager;
    private UpgradeManager upgradeManager;
    private MySQL mySQL;
    private LiteCommands<CommandSender> liteCommands;
    private ClanPlaceholder clanPlaceholder;
    private RankingManager rankingManager;
    private FileManager fileManager;
    private MessageService messageService;

    public void onLoad() {
        instance = this;
        this.fileManager = new FileManager(this);
        this.messageService = new MessageService(this.fileManager);
        MessageUtil.init(this.messageService);
    }

    public void onEnable() {
        if (!this.setupEconomy()) {
            this.getServer().getLogger().severe(String.format((String)"[%s] - Disabled due to no Vault dependency found!", (Object[])new Object[]{this.getDescription().getName()}));
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        this.mySQL = new MySQL(this, this.fileManager);
        if (!this.mySQL.isConnected()) {
            this.getLogger().severe("Cannot connect to the database!");
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        ClanService clanService = new ClanService(this.mySQL, this.fileManager, this);
        UserService userService = new UserService(this.mySQL, this.fileManager);
        AllianceService allianceService = new AllianceService(this.mySQL);
        this.upgradeManager = new UpgradeManager(this.fileManager);
        this.userManager = new UserManager(userService, this, this.fileManager);
        this.clanManager = new ClanManager(this, clanService, allianceService, this.fileManager);
        this.allianceManager = new AllianceManager(this, allianceService, this.fileManager);
        this.cooldownManager = new CooldownManager();
        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.clanPlaceholder = new ClanPlaceholder(this, this.fileManager, this.clanManager);
            this.clanPlaceholder.register();
            MessageUtil.logMessage("\u001b[0;32m", "Successfully implement the placeholders");
        }
        this.loadDataMySQL();
        Stream.of(new Listener[]{new PlayerConnectionListener(this, this.cooldownManager, this.clanManager), new PlayerDeathListener(this, this.fileManager), new EntityDamageListener(this, this.fileManager, this.clanManager), new AsyncPlayerChatListener(this, this.fileManager, this.clanManager), new PlayerInteractionEntityListener(this.fileManager, this.userManager, this.cooldownManager), new InventoryClickListener(this.clanManager, this.userManager), new BreakBlockListener(this.userManager, this.clanManager, this.upgradeManager, this.fileManager)}).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, (Plugin)this));
        this.rankingManager = new RankingManager(this.clanManager, this);
        Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this, () -> {
            this.rankingManager.updateAll((Collection<User>)this.userManager.getUserData().values());
            this.rankingManager.sort();
        }, 0L, 600L);
        Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this, () -> {
            MessageUtil.logMessage("\u001b[0;32m", "Starting update data to mysql...");
            this.userManager.updateUsers();
            this.clanManager.updateClans();
            this.mySQL.executeQueued();
        }, 6000L, 6000L);
        this.registerCmd();
        Metrics metrics = new Metrics(this, 19808);
    }

    public void onDisable() {
        if (this.clanPlaceholder != null) {
            this.clanPlaceholder.unregister();
        }
        if (this.mySQL != null) {
            this.userManager.updateUsers();
            this.clanManager.updateClans();
            this.mySQL.executeQueued();
            this.mySQL.disconnect();
        }
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
        HandlerList.unregisterAll((Plugin)this);
        Bukkit.getScheduler().cancelTasks((Plugin)this);
    }

    public void reloadPlugin(CommandSender sender) {
        this.fileManager.reload();
        MessageUtil.sendMessage(sender, "&aSuccessfully reloaded plugin!");
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.economy = (Economy)rsp.getProvider();
        return true;
    }

    private void loadDataMySQL() {
        this.clanManager.loadClans();
        this.userManager.loadUsers();
        this.allianceManager.loadAlliances();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTask((Plugin)this, () -> Bukkit.getOnlinePlayers().forEach(player -> this.userManager.loadUser((Player)player)));
    }

    public void registerCmd() {
        this.liteCommands = LiteBukkitFactory.builder("getclan").commands(this.fileManager.getConfig().getLangType() == LangType.PL ? new ClanPLCommand(this, this.fileManager, this.clanManager, this.upgradeManager) : new ClanENCommand(this, this.fileManager, this.clanManager, this.upgradeManager), this.fileManager.getConfig().getLangType() == LangType.PL ? new PlayerPLCommand(this) : new PlayerENCommand(this)).context(Owner.class, new OwnerContextual(this, this.fileManager, this.clanManager)).context(DeputyOwner.class, new DeputyOwnerContextual(this, this.fileManager, this.clanManager)).context(Member.class, new MemberContextual(this, this.fileManager, this.clanManager)).message(LiteBukkitMessages.PLAYER_NOT_FOUND, (String)MessageUtil.toLegacy(this.fileManager.getLangConfig().getMessage("player-not-found"), Map.of())).argument(Clan.class, (ArgumentResolverBase<CommandSender, Clan>)new ClanTagArgument(this.clanManager, this.fileManager)).argument(Owner.class, (ArgumentResolverBase<CommandSender, Owner>)new OwnerArgument(this.userManager, this.fileManager, this.clanManager)).argument(User.class, (ArgumentResolverBase<CommandSender, User>)new UserArgument(this.userManager, this.fileManager)).missingPermission(new PermissionMessage(this.fileManager)).invalidUsage(new CustomInvalidUsage(this.fileManager)).build();
    }

    @Generated
    public Economy getEconomy() {
        return this.economy;
    }

    @Generated
    public UserManager getUserManager() {
        return this.userManager;
    }

    @Generated
    public ClanManager getClanManager() {
        return this.clanManager;
    }

    @Generated
    public CooldownManager getCooldownManager() {
        return this.cooldownManager;
    }

    @Generated
    public AllianceManager getAllianceManager() {
        return this.allianceManager;
    }

    @Generated
    public UpgradeManager getUpgradeManager() {
        return this.upgradeManager;
    }

    @Generated
    public MySQL getMySQL() {
        return this.mySQL;
    }

    @Generated
    public LiteCommands<CommandSender> getLiteCommands() {
        return this.liteCommands;
    }

    @Generated
    public ClanPlaceholder getClanPlaceholder() {
        return this.clanPlaceholder;
    }

    @Generated
    public RankingManager getRankingManager() {
        return this.rankingManager;
    }

    @Generated
    public FileManager getFileManager() {
        return this.fileManager;
    }

    @Generated
    public MessageService getMessageService() {
        return this.messageService;
    }
}
