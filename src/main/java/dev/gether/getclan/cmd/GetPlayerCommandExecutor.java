package dev.gether.getclan.cmd;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.config.domain.LangType;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.core.user.UserManager;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class GetPlayerCommandExecutor implements TabExecutor {
    private final GetClan plugin;
    private final FileManager fileManager;
    private final UserManager userManager;
    private final PlayerENCommand playerEnCommand;
    private final PlayerPLCommand playerPlCommand;

    public GetPlayerCommandExecutor(GetClan plugin, FileManager fileManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.userManager = plugin.getUserManager();
        this.playerEnCommand = new PlayerENCommand(plugin);
        this.playerPlCommand = new PlayerPLCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePermission(sender, "getclan.use")) {
            return true;
        }
        if (!(sender instanceof Player player)) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("not-possible-via-console"));
            return true;
        }
        if (args.length < 1) {
            MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("usage-cmd"), Map.of("usage", "/player [nickname]"));
            return true;
        }
        User user = resolveUser(sender, args[0]);
        if (user == null) {
            return true;
        }
        if (isPolish()) {
            playerPlCommand.infoClan(player, user);
        } else {
            playerEnCommand.infoClan(player, user);
        }
        return true;
    }

    private boolean isPolish() {
        return fileManager.getConfig().getLangType() == LangType.PL;
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

    private boolean ensurePermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        MessageUtil.sendMessage(sender, fileManager.getLangConfig().getMessage("no-permission"), Map.of("permission", permission));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase(Locale.ROOT);
            return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(prefix))
                .toList();
        }
        return List.of();
    }
}
