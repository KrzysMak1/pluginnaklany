/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map$Entry
 *  java.util.logging.Logger
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package dev.gether.getconfig.utils;

import dev.gether.getclan.message.MessageService;
import dev.gether.getconfig.domain.config.TitleMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {
    private static final Logger LOG = Bukkit.getLogger();
    private static MessageService messageService;

    private MessageUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void init(MessageService service) {
        messageService = service;
    }

    public static void logMessage(String consoleColor, String message) {
        LOG.info(consoleColor + message + "\u001b[0m");
    }

    public static void sendMessage(Player player, String message) {
        if (messageService == null) {
            player.sendMessage(ColorFixer.addColors(message));
            return;
        }
        messageService.send(player, message, Map.of());
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (messageService == null) {
            sender.sendMessage(ColorFixer.addColors(message));
            return;
        }
        messageService.send(sender, message, Map.of());
    }

    public static void sendMessage(CommandSender sender, String message, Map<String, String> placeholders) {
        if (messageService == null) {
            sender.sendMessage(ColorFixer.addColors(message));
            return;
        }
        messageService.send(sender, message, placeholders);
    }

    public static void sendMessage(CommandSender sender, String message, Map<String, String> placeholders, Map<String, Component> componentPlaceholders) {
        if (messageService == null) {
            sender.sendMessage(ColorFixer.addColors(message));
            return;
        }
        messageService.send(sender, message, placeholders, componentPlaceholders);
    }

    public static void titleMessage(Player player, TitleMessage titleMessage) {
        if (titleMessage == null || !titleMessage.isEnabled()) {
            return;
        }
        if (messageService == null) {
            player.sendTitle(ColorFixer.addColors(titleMessage.getTitle()), ColorFixer.addColors(titleMessage.getSubtitle()), titleMessage.getFadeIn(), titleMessage.getStay(), titleMessage.getFadeOut());
            return;
        }
        player.sendTitle(messageService.toLegacy(titleMessage.getTitle(), Map.of()), messageService.toLegacy(titleMessage.getSubtitle(), Map.of()), titleMessage.getFadeIn(), titleMessage.getStay(), titleMessage.getFadeOut());
    }

    public static void titleMessage(TitleMessage titleMessage, HashMap<String, String> variables) {
        if (titleMessage == null || !titleMessage.isEnabled()) {
            return;
        }
        if (messageService == null) {
            String title = titleMessage.getTitle();
            String subtitle = titleMessage.getSubtitle();
            for (Map.Entry entry : variables.entrySet()) {
                String variable = (String)entry.getKey();
                String value = (String)entry.getValue();
                title = title.replace((CharSequence)variable, (CharSequence)value);
                subtitle = subtitle.replace((CharSequence)variable, (CharSequence)value);
            }
            String finalTitle = title;
            String finalSubtitle = subtitle;
            Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(ColorFixer.addColors(finalTitle), ColorFixer.addColors(finalSubtitle), titleMessage.getFadeIn(), titleMessage.getStay(), titleMessage.getFadeOut()));
            return;
        }
        String baseTitle = titleMessage.getTitle();
        String baseSubtitle = titleMessage.getSubtitle();
        Map<String, String> placeholders = new HashMap<>();
        variables.forEach((key, value) -> placeholders.put(normalizeKey(key), value));
        Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(messageService.toLegacy(baseTitle, placeholders), messageService.toLegacy(baseSubtitle, placeholders), titleMessage.getFadeIn(), titleMessage.getStay(), titleMessage.getFadeOut()));
    }

    public static void sendMessage(CommandSender sender, List<String> message) {
        if (message.isEmpty()) {
            return;
        }
        String joined = String.join((CharSequence)"\n", message);
        if (messageService == null) {
            sender.sendMessage(ColorFixer.addColors(joined));
            return;
        }
        messageService.send(sender, joined, Map.of());
    }

    public static void broadcast(String message) {
        if (messageService == null) {
            Bukkit.broadcastMessage((String)ColorFixer.addColors(message));
            return;
        }
        Bukkit.broadcast(messageService.parse(message, Map.of()));
    }

    public static void broadcast(String message, Map<String, String> placeholders) {
        if (messageService == null) {
            Bukkit.broadcastMessage((String)ColorFixer.addColors(applyLegacyPlaceholders(message, placeholders)));
            return;
        }
        Bukkit.broadcast(messageService.parse(message, placeholders));
    }

    public static void broadcastNoneColor(String message) {
        Bukkit.broadcastMessage((String)message);
    }

    public static String toLegacy(String message, Map<String, String> placeholders) {
        if (messageService == null) {
            return ColorFixer.addColors(applyLegacyPlaceholders(message, placeholders));
        }
        return messageService.toLegacy(message, placeholders);
    }

    private static String applyLegacyPlaceholders(String message, Map<String, String> placeholders) {
        if (placeholders == null || placeholders.isEmpty()) {
            return message;
        }
        String resolved = message;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            resolved = resolved.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return resolved;
    }

    private static String normalizeKey(String key) {
        if (key == null) {
            return "";
        }
        if (key.startsWith("{") && key.endsWith("}") && key.length() > 2) {
            return key.substring(1, key.length() - 1);
        }
        return key;
    }
}
