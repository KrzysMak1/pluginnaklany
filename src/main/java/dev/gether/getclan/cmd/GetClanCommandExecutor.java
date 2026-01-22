package dev.gether.getclan.cmd;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.cmd.context.domain.DeputyOwner;
import dev.gether.getclan.cmd.context.domain.Member;
import dev.gether.getclan.cmd.context.domain.Owner;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.config.domain.LangType;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.clan.ClanManager;
import dev.gether.getclan.core.upgrade.UpgradeCost;
import dev.gether.getclan.core.upgrade.UpgradeManager;
import dev.gether.getclan.core.upgrade.UpgradeType;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class GetClanCommandExecutor implements TabExecutor {
    private static final Pattern TAG_PATTERN = Pattern.compile("^[a-zA-ZąąćęłńóśźżĄĆĘŁŃÓŚŹŻ0-9]+$");
    private final GetClan plugin;
    private final FileManager fileManager;
    private final ClanManager clanManager;
    private final UpgradeManager upgradeManager;
    private final UserManager userManager;
    private final ClanENCommand clanEnCommand;
    private final ClanPLCommand clanPlCommand;

    public GetClanCommandExecutor(GetClan plugin, FileManager fileManager, ClanManager clanManager, UpgradeManager upgradeManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.clanManager = clanManager;
        this.upgradeManager = upgradeManager;
        this.userManager = plugin.getUserManager();
        this.clanEnCommand = new ClanENCommand(plugin, fileManager, clanManager, upgradeManager);
        this.clanPlCommand = new ClanPLCommand(plugin, fileManager, clanManager, upgradeManager);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePermission(sender, "getclan.use")) {
            return true;
        }
        if (args.length == 0) {
            sendUsageList(sender);
            return true;
        }
        String subcommand = args[0].toLowerCase(Locale.ROOT);
        if (isAny(subcommand, "reload")) {
            if (!ensurePermission(sender, "getclan.admin")) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.reloadConfig(sender);
            } else {
                clanEnCommand.reloadConfig(sender);
            }
            return true;
        }
        if (isAny(subcommand, "setitem")) {
            if (!ensurePermission(sender, "getclan.admin")) {
                return true;
            }
            Optional<Player> player = requirePlayer(sender);
            if (player.isEmpty()) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.setItemCost(player.get());
            } else {
                clanEnCommand.setItemCost(player.get());
            }
            return true;
        }
        if (isAny(subcommand, "admin")) {
            return handleAdmin(sender, args);
        }
        return handlePlayer(sender, subcommand, args);
    }

    private boolean handlePlayer(CommandSender sender, String subcommand, String[] args) {
        if (isAny(subcommand, "create", "stworz")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan create [tag]");
                return true;
            }
            String tag = args[1];
            Optional<Player> player = requirePlayer(sender);
            if (player.isEmpty()) {
                return true;
            }
            Matcher matcher = TAG_PATTERN.matcher(tag);
            if (!matcher.matches()) {
                MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("invalid-tag-characters"));
                return true;
            }
            clanManager.createClan(player.get(), tag);
            return true;
        }
        if (isAny(subcommand, "join", "dolacz")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan join [tag]");
                return true;
            }
            Optional<Player> player = requirePlayer(sender);
            if (player.isEmpty()) {
                return true;
            }
            Clan clan = resolveClan(sender, args[1]);
            if (clan == null) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.joinClan(player.get(), clan);
            } else {
                clanEnCommand.joinClan(player.get(), clan);
            }
            return true;
        }
        if (isAny(subcommand, "leave", "opusc")) {
            Optional<Member> member = resolveMember(sender);
            if (member.isEmpty()) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.leaveClan(member.get());
            } else {
                clanEnCommand.leaveClan(member.get());
            }
            return true;
        }
        if (isAny(subcommand, "info")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan info [tag]");
                return true;
            }
            Clan clan = resolveClan(sender, args[1]);
            if (clan == null) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.infoClan(sender, clan);
            } else {
                clanEnCommand.infoClan(sender, clan);
            }
            return true;
        }
        if (isAny(subcommand, "alliance", "sojusz")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan alliance [tag]");
                return true;
            }
            Optional<DeputyOwner> deputyOwner = resolveDeputy(sender);
            if (deputyOwner.isEmpty()) {
                return true;
            }
            Clan clan = resolveClan(sender, args[1]);
            if (clan == null) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.alliance(deputyOwner.get(), clan);
            } else {
                clanEnCommand.alliace(deputyOwner.get(), clan);
            }
            return true;
        }
        if (isAny(subcommand, "invite", "zapros")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan invite [nickname]");
                return true;
            }
            Optional<DeputyOwner> deputyOwner = resolveDeputy(sender);
            if (deputyOwner.isEmpty()) {
                return true;
            }
            Player target = resolvePlayer(sender, args[1]);
            if (target == null) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.inviteUser(deputyOwner.get(), target);
            } else {
                clanEnCommand.inviteUser(deputyOwner.get(), target);
            }
            return true;
        }
        if (isAny(subcommand, "kick", "wyrzuc")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan kick [nickname]");
                return true;
            }
            Optional<DeputyOwner> deputyOwner = resolveDeputy(sender);
            if (deputyOwner.isEmpty()) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.kickUser(deputyOwner.get(), args[1]);
            } else {
                clanEnCommand.kickUser(deputyOwner.get(), args[1]);
            }
            return true;
        }
        if (isAny(subcommand, "setowner", "ustawlidera")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan setowner [nickname]");
                return true;
            }
            Optional<Owner> owner = resolveOwner(sender);
            if (owner.isEmpty()) {
                return true;
            }
            Player target = resolvePlayer(sender, args[1]);
            if (target == null) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.setOwner(owner.get(), target);
            } else {
                clanEnCommand.setOwner(owner.get(), target);
            }
            return true;
        }
        if (isAny(subcommand, "deputy", "zastepca")) {
            if (args.length < 2) {
                sendUsage(sender, "/clan deputy [nickname]");
                return true;
            }
            Optional<Owner> owner = resolveOwner(sender);
            if (owner.isEmpty()) {
                return true;
            }
            Player target = resolvePlayer(sender, args[1]);
            if (target == null) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.setDeputy(owner.get(), target);
            } else {
                clanEnCommand.setDeputy(owner.get(), target);
            }
            return true;
        }
        if (isAny(subcommand, "removedeputy", "usunzastepce")) {
            Optional<Owner> owner = resolveOwner(sender);
            if (owner.isEmpty()) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.removeDeputy(sender, owner.get());
            } else {
                clanEnCommand.removeDeputy(sender, owner.get());
            }
            return true;
        }
        if (isAny(subcommand, "delete", "usun")) {
            Optional<Owner> owner = resolveOwner(sender);
            if (owner.isEmpty()) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.deleteClan(owner.get());
            } else {
                clanEnCommand.deleteClan(owner.get());
            }
            return true;
        }
        if (isAny(subcommand, "pvp")) {
            Optional<DeputyOwner> deputyOwner = resolveDeputy(sender);
            if (deputyOwner.isEmpty()) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.changePvpStatusClan(deputyOwner.get());
            } else {
                clanEnCommand.changePvpStatusClan(deputyOwner.get());
            }
            return true;
        }
        if (isAny(subcommand, "upgrade", "ulepszenia")) {
            Optional<Player> player = requirePlayer(sender);
            if (player.isEmpty()) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.upgradePanel(player.get());
            } else {
                clanEnCommand.upgradePanel(player.get());
            }
            return true;
        }
        sendUsageList(sender);
        return true;
    }

    private boolean handleAdmin(CommandSender sender, String[] args) {
        if (!ensurePermission(sender, "getclan.admin")) {
            return true;
        }
        if (args.length < 2) {
            sendUsageList(sender);
            return true;
        }
        String subcommand = args[1].toLowerCase(Locale.ROOT);
        if (isAny(subcommand, "reset", "resetuj")) {
            return handleAdminReset(sender, args);
        }
        if (isAny(subcommand, "delete", "usun")) {
            return handleAdminDelete(sender, args);
        }
        if (isAny(subcommand, "setpoints", "ustawpunkty")) {
            return handleAdminSetPoints(sender, args, 2);
        }
        if (isAny(subcommand, "set") && args.length > 2 && isAny(args[2], "points")) {
            return handleAdminSetPoints(sender, args, 3);
        }
        if (isAny(subcommand, "ustaw") && args.length > 2 && isAny(args[2], "punkty")) {
            return handleAdminSetPoints(sender, args, 3);
        }
        if (isAny(subcommand, "upgrade", "ulepszenia")) {
            return handleAdminUpgradeToggle(sender, args);
        }
        if (isAny(subcommand, "give")) {
            return handleAdminGive(sender, args);
        }
        if (isAny(subcommand, "setitem")) {
            return handleAdminSetUpgradeItem(sender, args);
        }
        sendUsageList(sender);
        return true;
    }

    private boolean handleAdminReset(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sendUsage(sender, "/clan admin reset <all|kills|deaths|points> [player]");
            return true;
        }
        String target = args[2].toLowerCase(Locale.ROOT);
        User user = resolveUser(sender, args[3]);
        if (user == null) {
            return true;
        }
        if (isAny(target, "all", "*")) {
            if (isPolish()) {
                clanPlCommand.adminReset(sender, user);
            } else {
                clanEnCommand.adminReset(sender, user);
            }
            return true;
        }
        if (isAny(target, "kills", "kill", "zabojstwa")) {
            if (isPolish()) {
                clanPlCommand.adminResetKill(sender, user);
            } else {
                clanEnCommand.adminResetKill(sender, user);
            }
            return true;
        }
        if (isAny(target, "deaths", "death", "smierci")) {
            if (isPolish()) {
                clanPlCommand.adminResetDeath(sender, user);
            } else {
                clanEnCommand.adminResetDeath(sender, user);
            }
            return true;
        }
        if (isAny(target, "points", "punkty")) {
            if (isPolish()) {
                clanPlCommand.adminResetPoints(sender, user);
            } else {
                clanEnCommand.adminResetPoints(sender, user);
            }
            return true;
        }
        sendUsage(sender, "/clan admin reset <all|kills|deaths|points> [player]");
        return true;
    }

    private boolean handleAdminDelete(CommandSender sender, String[] args) {
        if (args.length < 4 || !isAny(args[2], "clan", "klan")) {
            sendUsage(sender, "/clan admin delete clan [player]");
            return true;
        }
        Owner owner = resolveOwnerByName(sender, args[3]);
        if (owner != null) {
            if (isPolish()) {
                clanPlCommand.adminRemove(sender, owner);
            } else {
                clanEnCommand.adminRemove(sender, owner);
            }
            return true;
        }
        Clan clan = resolveClan(sender, args[3]);
        if (clan == null) {
            return true;
        }
        if (isPolish()) {
            clanPlCommand.adminRemoveByTag(sender, clan);
        } else {
            clanEnCommand.adminRemoveByTag(sender, clan);
        }
        return true;
    }

    private boolean handleAdminSetPoints(CommandSender sender, String[] args, int offset) {
        if (args.length <= offset + 1) {
            sendUsage(sender, "/clan admin setpoints [player] [points]");
            return true;
        }
        User user = resolveUser(sender, args[offset]);
        if (user == null) {
            return true;
        }
        Integer points = parseInt(sender, args[offset + 1], "/clan admin setpoints [player] [points]");
        if (points == null) {
            return true;
        }
        if (isPolish()) {
            clanPlCommand.adminSetPoint(sender, user, points);
        } else {
            clanEnCommand.adminSetPoint(sender, user, points);
        }
        return true;
    }

    private boolean handleAdminUpgradeToggle(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sendUsage(sender, "/clan admin upgrade <enable|disable>");
            return true;
        }
        String toggle = args[2].toLowerCase(Locale.ROOT);
        if (isAny(toggle, "disable", "off")) {
            if (isPolish()) {
                clanPlCommand.disableUpgrade(sender);
            } else {
                clanEnCommand.disableUpgrade(sender);
            }
            return true;
        }
        if (isAny(toggle, "enable", "on")) {
            if (isPolish()) {
                clanPlCommand.enableUpgrade(sender);
            } else {
                clanEnCommand.enableUpgrade(sender);
            }
            return true;
        }
        sendUsage(sender, "/clan admin upgrade <enable|disable>");
        return true;
    }

    private boolean handleAdminGive(CommandSender sender, String[] args) {
        if (args.length < 5) {
            sendUsage(sender, "/clan admin give [type] [player] [amount]");
            return true;
        }
        String typeRaw = args[2];
        boolean isDefault = isDefaultType(typeRaw);
        Player target = resolvePlayer(sender, args[3]);
        if (target == null) {
            return true;
        }
        if (isDefault) {
            Integer amount = parseInt(sender, args[4], "/clan admin give [type] [player] [amount]");
            if (amount == null) {
                return true;
            }
            if (isPolish()) {
                clanPlCommand.giveDefaultItem(sender, target, amount);
            } else {
                clanEnCommand.giveDefaultItem(sender, target, amount);
            }
            return true;
        }
        Optional<UpgradeType> upgradeType = parseUpgradeType(typeRaw);
        if (upgradeType.isEmpty()) {
            sendUsage(sender, "/clan admin give [type] [player] [amount]");
            return true;
        }
        int level = resolveDefaultLevel(upgradeType.get());
        int amountIndex = 4;
        if (args.length > 5) {
            Integer parsedLevel = parseInt(sender, args[4], "/clan admin give [type] [player] [level] [amount]");
            if (parsedLevel == null) {
                return true;
            }
            level = parsedLevel;
            amountIndex = 5;
        }
        Integer amount = parseInt(sender, args[amountIndex], "/clan admin give [type] [player] [amount]");
        if (amount == null) {
            return true;
        }
        if (isPolish()) {
            clanPlCommand.giveLevelItem(sender, upgradeType.get(), target, level, amount);
        } else {
            clanEnCommand.giveLevelItem(sender, upgradeType.get(), target, level, amount);
        }
        return true;
    }

    private boolean handleAdminSetUpgradeItem(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sendUsage(sender, "/clan admin setitem [type] [level]");
            return true;
        }
        Optional<Player> player = requirePlayer(sender);
        if (player.isEmpty()) {
            return true;
        }
        Optional<UpgradeType> upgradeType = parseUpgradeType(args[2]);
        if (upgradeType.isEmpty()) {
            sendUsage(sender, "/clan admin setitem [type] [level]");
            return true;
        }
        Integer level = parseInt(sender, args[3], "/clan admin setitem [type] [level]");
        if (level == null) {
            return true;
        }
        Optional<UpgradeCost> upgradeCost = upgradeManager.findUpgradeCost(upgradeType.get(), level);
        if (upgradeCost.isEmpty()) {
            sendUpgradeNotFound(sender);
            return true;
        }
        if (isPolish()) {
            clanPlCommand.adminUpgradeItem(player.get(), upgradeType.get(), level);
        } else {
            clanEnCommand.adminUpgradeItem(player.get(), upgradeType.get(), level);
        }
        return true;
    }

    private boolean isPolish() {
        return fileManager.getConfig().getLangType() == LangType.PL;
    }

    private Optional<Player> requirePlayer(CommandSender sender) {
        if (sender instanceof Player player) {
            return Optional.of(player);
        }
        MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("not-possible-via-console"));
        return Optional.empty();
    }

    private Optional<Member> resolveMember(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-not-found"));
            return Optional.empty();
        }
        User user = userManager.getUserData().get(player.getUniqueId());
        if (user == null || !user.hasClan()) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-has-no-clan"));
            return Optional.empty();
        }
        Clan clan = clanManager.getClan(user.getTag());
        if (clan == null) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("clan-does-not-exist"));
            return Optional.empty();
        }
        return Optional.of(new Member(player, clan));
    }

    private Optional<Owner> resolveOwner(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-not-found"));
            return Optional.empty();
        }
        User user = userManager.getUserData().get(player.getUniqueId());
        if (user == null || !user.hasClan()) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-has-no-clan"));
            return Optional.empty();
        }
        Clan clan = clanManager.getClan(user.getTag());
        if (clan == null) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("clan-does-not-exist"));
            return Optional.empty();
        }
        if (!clan.isOwner(player.getUniqueId())) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("not-clan-owner"));
            return Optional.empty();
        }
        return Optional.of(new Owner(player, clan));
    }

    private Optional<DeputyOwner> resolveDeputy(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-not-found"));
            return Optional.empty();
        }
        User user = userManager.getUserData().get(player.getUniqueId());
        if (user == null || !user.hasClan()) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-has-no-clan"));
            return Optional.empty();
        }
        Clan clan = clanManager.getClan(user.getTag());
        if (clan == null) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("clan-does-not-exist"));
            return Optional.empty();
        }
        if (!clan.isOwner(player.getUniqueId()) && !clan.isDeputy(player.getUniqueId())) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("not-clan-owner"));
            return Optional.empty();
        }
        return Optional.of(new DeputyOwner(player, clan));
    }

    private User resolveUser(CommandSender sender, String name) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-not-found"));
            return null;
        }
        User user = userManager.getUserData().get(target.getUniqueId());
        if (user == null) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-not-found"));
            return null;
        }
        return user;
    }

    private Owner resolveOwnerByName(CommandSender sender, String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return null;
        }
        User user = userManager.getUserData().get(player.getUniqueId());
        if (user == null || !user.hasClan()) {
            return null;
        }
        Clan clan = clanManager.getClan(user.getTag());
        if (clan == null) {
            return null;
        }
        return new Owner(player, clan);
    }

    private Player resolvePlayer(CommandSender sender, String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("player-not-found"));
            return null;
        }
        return player;
    }

    private Clan resolveClan(CommandSender sender, String tag) {
        Clan clan = clanManager.getClan(tag);
        if (clan == null) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("clan-does-not-exist"));
            return null;
        }
        return clan;
    }

    private Optional<UpgradeType> parseUpgradeType(String raw) {
        try {
            return Optional.of(UpgradeType.valueOf(raw.toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    private boolean isDefaultType(String raw) {
        return isAny(raw, "default", "odlamek");
    }

    private int resolveDefaultLevel(UpgradeType upgradeType) {
        return fileManager.getUpgradesConfig()
            .findUpgradeByType(upgradeType)
            .flatMap(upgrade -> upgrade.getUpgradesCost().keySet().stream().min(Integer::compareTo))
            .orElse(0);
    }

    private void sendUpgradeNotFound(CommandSender sender) {
        if (isPolish()) {
            MessageUtil.sendMessage(sender, "&cNie można znaleźć typu ulepszenia na tym poziomie!");
        } else {
            MessageUtil.sendMessage(sender, "&cCannot find the upgrade type with this level!");
        }
    }

    private boolean ensurePermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("no-permission"), Map.of("permission", permission));
        return false;
    }

    private void sendUsage(CommandSender sender, String usage) {
        MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("usage-cmd"), Map.of("usage", usage));
    }

    private void sendUsageList(CommandSender sender) {
        MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("usage-list"));
    }

    private Integer parseInt(CommandSender sender, String raw, String usage) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            sendUsage(sender, usage);
            return null;
        }
    }

    private boolean isAny(String value, String... values) {
        for (String candidate : values) {
            if (candidate.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            return List.of();
        }
        String prefix = args[args.length - 1].toLowerCase(Locale.ROOT);
        if (args.length == 1) {
            return filterOptions(baseSubcommands(sender), prefix);
        }
        String root = args[0].toLowerCase(Locale.ROOT);
        if (isAny(root, "admin")) {
            return handleAdminTabComplete(sender, args, prefix);
        }
        return handlePlayerTabComplete(root, args, prefix);
    }

    private List<String> handlePlayerTabComplete(String root, String[] args, String prefix) {
        if (args.length != 2) {
            return List.of();
        }
        if (isAny(root, "join", "dolacz", "info", "alliance", "sojusz")) {
            return filterOptions(clanManager.getClansData().keySet(), prefix);
        }
        if (isAny(root, "invite", "zapros", "kick", "wyrzuc", "setowner", "ustawlidera", "deputy", "zastepca")) {
            return filterOptions(playerNames(), prefix);
        }
        return List.of();
    }

    private List<String> handleAdminTabComplete(CommandSender sender, String[] args, String prefix) {
        if (!sender.hasPermission("getclan.admin")) {
            return List.of();
        }
        if (args.length == 2) {
            return filterOptions(adminSubcommands(), prefix);
        }
        String subcommand = args[1].toLowerCase(Locale.ROOT);
        if (isAny(subcommand, "reset", "resetuj")) {
            if (args.length == 3) {
                return filterOptions(adminResetTargets(), prefix);
            }
            if (args.length == 4) {
                return filterOptions(playerNames(), prefix);
            }
        }
        if (isAny(subcommand, "delete", "usun") && args.length == 3) {
            return filterOptions(List.of("clan", "klan"), prefix);
        }
        if (isAny(subcommand, "delete", "usun") && args.length == 4) {
            return filterOptions(mergeOptions(playerNames(), clanManager.getClansData().keySet()), prefix);
        }
        if (isAny(subcommand, "setpoints", "ustawpunkty") && args.length == 3) {
            return filterOptions(playerNames(), prefix);
        }
        if (isAny(subcommand, "set", "ustaw") && args.length == 4) {
            return filterOptions(playerNames(), prefix);
        }
        if (isAny(subcommand, "upgrade", "ulepszenia") && args.length == 3) {
            return filterOptions(List.of("enable", "disable", "on", "off"), prefix);
        }
        if (isAny(subcommand, "give") && args.length == 3) {
            return filterOptions(adminGiveTypes(), prefix);
        }
        if (isAny(subcommand, "give") && args.length == 4) {
            return filterOptions(playerNames(), prefix);
        }
        if (isAny(subcommand, "setitem") && args.length == 3) {
            return filterOptions(adminGiveTypes(), prefix);
        }
        return List.of();
    }

    private List<String> baseSubcommands(CommandSender sender) {
        List<String> subcommands = new ArrayList<>();
        if (isPolish()) {
            subcommands.addAll(List.of("stworz", "dolacz", "opusc", "info", "sojusz", "zapros", "wyrzuc", "ustawlidera", "zastepca",
                "usunzastepce", "usun", "pvp", "ulepszenia"));
        } else {
            subcommands.addAll(List.of("create", "join", "leave", "info", "alliance", "invite", "kick", "setowner", "deputy",
                "removedeputy", "delete", "pvp", "upgrade"));
        }
        if (sender.hasPermission("getclan.admin")) {
            subcommands.addAll(List.of("admin", "reload", "setitem"));
        }
        return subcommands;
    }

    private List<String> adminSubcommands() {
        if (isPolish()) {
            return List.of("resetuj", "usun", "ustawpunkty", "ustaw", "ulepszenia", "give", "setitem");
        }
        return List.of("reset", "delete", "setpoints", "set", "upgrade", "give", "setitem");
    }

    private List<String> adminResetTargets() {
        if (isPolish()) {
            return List.of("all", "*", "zabojstwa", "smierci", "punkty");
        }
        return List.of("all", "*", "kills", "kill", "deaths", "death", "points");
    }

    private List<String> adminGiveTypes() {
        List<String> types = Arrays.stream(UpgradeType.values())
            .map(type -> type.name().toLowerCase(Locale.ROOT))
            .toList();
        if (isPolish()) {
            return mergeOptions(types, List.of("odlamek"));
        }
        return mergeOptions(types, List.of("default"));
    }

    private List<String> playerNames() {
        return Bukkit.getOnlinePlayers()
            .stream()
            .map(Player::getName)
            .toList();
    }

    private List<String> mergeOptions(Collection<String> first, Collection<String> second) {
        List<String> merged = new ArrayList<>(first.size() + second.size());
        merged.addAll(first);
        merged.addAll(second);
        return merged;
    }

    private List<String> filterOptions(Collection<String> options, String prefix) {
        return options.stream()
            .filter(option -> option.toLowerCase(Locale.ROOT).startsWith(prefix))
            .toList();
    }
}
