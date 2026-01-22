package dev.gether.getclan.message;

import dev.gether.getclan.config.FileManager;
import dev.gether.getconfig.utils.ColorFixer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

public class MessageService {
    private final FileManager fileManager;
    private final MiniMessage miniMessage;
    private final LegacyComponentSerializer legacySerializer;

    public MessageService(FileManager fileManager) {
        this.fileManager = fileManager;
        this.miniMessage = MiniMessage.miniMessage();
        this.legacySerializer = LegacyComponentSerializer.legacySection();
    }

    public Component parse(String text, Map<String, String> placeholders) {
        return this.parse(text, placeholders, Collections.emptyMap());
    }

    public Component parse(String text, Map<String, String> placeholders, Map<String, Component> componentPlaceholders) {
        if (text == null) {
            return Component.empty();
        }
        Map<String, String> normalizedPlaceholders = normalizePlaceholders(placeholders);
        Map<String, Component> normalizedComponentPlaceholders = normalizeComponentPlaceholders(componentPlaceholders);
        if (shouldUseMiniMessage(text)) {
            String resolvedText = replacePlaceholderTokens(text, normalizedPlaceholders.keySet(), normalizedComponentPlaceholders.keySet());
            resolvedText = applyLegacyToMiniMessage(resolvedText);
            TagResolver resolver = buildTagResolver(normalizedPlaceholders, normalizedComponentPlaceholders);
            return this.miniMessage.deserialize(resolvedText, resolver);
        }
        String legacy = applyLegacyPlaceholders(text, normalizedPlaceholders, normalizedComponentPlaceholders);
        legacy = ColorFixer.addColors(legacy);
        return this.legacySerializer.deserialize(legacy);
    }

    public void send(CommandSender sender, String text, Map<String, String> placeholders) {
        this.send(sender, text, placeholders, Collections.emptyMap());
    }

    public void send(CommandSender sender, String text, Map<String, String> placeholders, Map<String, Component> componentPlaceholders) {
        sender.sendMessage(this.parse(text, placeholders, componentPlaceholders));
    }

    public String toLegacy(String text, Map<String, String> placeholders) {
        return this.toLegacy(text, placeholders, Collections.emptyMap());
    }

    public String toLegacy(String text, Map<String, String> placeholders, Map<String, Component> componentPlaceholders) {
        return this.legacySerializer.serialize(this.parse(text, placeholders, componentPlaceholders));
    }

    private boolean shouldUseMiniMessage(String text) {
        return this.fileManager.getConfig().getMessages().isUseMiniMessage() && text.contains("<") && text.contains(">");
    }

    private TagResolver buildTagResolver(Map<String, String> placeholders, Map<String, Component> componentPlaceholders) {
        TagResolver.Builder builder = TagResolver.builder();
        placeholders.forEach((key, value) -> builder.resolver(Placeholder.component(key, parseLegacyComponent(value))));
        componentPlaceholders.forEach((key, value) -> builder.resolver(Placeholder.component(key, value)));
        return builder.build();
    }

    private String replacePlaceholderTokens(String text, Set<String> stringPlaceholders, Set<String> componentPlaceholders) {
        String resolved = text;
        for (String key : stringPlaceholders) {
            resolved = resolved.replace("{" + key + "}", "<" + key + ">");
        }
        for (String key : componentPlaceholders) {
            resolved = resolved.replace("{" + key + "}", "<" + key + ">");
        }
        return resolved;
    }

    private String applyLegacyPlaceholders(String text, Map<String, String> placeholders, Map<String, Component> componentPlaceholders) {
        String resolved = text;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            resolved = resolved.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        for (Map.Entry<String, Component> entry : componentPlaceholders.entrySet()) {
            resolved = resolved.replace("{" + entry.getKey() + "}", this.legacySerializer.serialize(entry.getValue()));
        }
        return resolved;
    }

    private Map<String, String> normalizePlaceholders(Map<String, String> placeholders) {
        if (placeholders == null || placeholders.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> normalized = new HashMap<>();
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String key = normalizeKey(entry.getKey());
            normalized.put(key, entry.getValue());
        }
        return normalized;
    }

    private Map<String, Component> normalizeComponentPlaceholders(Map<String, Component> placeholders) {
        if (placeholders == null || placeholders.isEmpty()) {
            return Collections.emptyMap();
        }
        return placeholders.entrySet().stream()
            .collect(Collectors.toMap(entry -> normalizeKey(entry.getKey()), Map.Entry::getValue));
    }

    private String normalizeKey(String key) {
        if (key == null) {
            return "";
        }
        if (key.startsWith("{") && key.endsWith("}") && key.length() > 2) {
            return key.substring(1, key.length() - 1);
        }
        return key;
    }

    private Component parseLegacyComponent(String value) {
        if (value == null || value.isEmpty()) {
            return Component.empty();
        }
        String legacy = ColorFixer.addColors(value);
        return this.legacySerializer.deserialize(legacy);
    }

    private String applyLegacyToMiniMessage(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        StringBuilder output = new StringBuilder(text.length());
        boolean inTag = false;
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current == '<') {
                inTag = true;
                output.append(current);
                continue;
            }
            if (current == '>') {
                inTag = false;
                output.append(current);
                continue;
            }
            if (!inTag) {
                if (current == '&' && i + 1 < text.length()) {
                    char next = text.charAt(i + 1);
                    if (next == '#' && i + 7 < text.length()) {
                        String hex = text.substring(i + 2, i + 8);
                        if (isHex(hex)) {
                            output.append("<#").append(hex).append(">");
                            i += 7;
                            continue;
                        }
                    }
                    String tag = legacyCodeToMiniMessage(next);
                    if (tag != null) {
                        output.append(tag);
                        i++;
                        continue;
                    }
                }
                if (current == '#' && i + 6 < text.length()) {
                    String hex = text.substring(i + 1, i + 7);
                    if (isHex(hex)) {
                        output.append("<#").append(hex).append(">");
                        i += 6;
                        continue;
                    }
                }
            }
            output.append(current);
        }
        return output.toString();
    }

    private String legacyCodeToMiniMessage(char code) {
        switch (Character.toLowerCase(code)) {
            case '0':
                return "<black>";
            case '1':
                return "<dark_blue>";
            case '2':
                return "<dark_green>";
            case '3':
                return "<dark_aqua>";
            case '4':
                return "<dark_red>";
            case '5':
                return "<dark_purple>";
            case '6':
                return "<gold>";
            case '7':
                return "<gray>";
            case '8':
                return "<dark_gray>";
            case '9':
                return "<blue>";
            case 'a':
                return "<green>";
            case 'b':
                return "<aqua>";
            case 'c':
                return "<red>";
            case 'd':
                return "<light_purple>";
            case 'e':
                return "<yellow>";
            case 'f':
                return "<white>";
            case 'k':
                return "<obfuscated>";
            case 'l':
                return "<bold>";
            case 'm':
                return "<strikethrough>";
            case 'n':
                return "<underline>";
            case 'o':
                return "<italic>";
            case 'r':
                return "<reset>";
            default:
                return null;
        }
    }

    private boolean isHex(String value) {
        for (int i = 0; i < value.length(); i++) {
            char current = value.charAt(i);
            boolean valid = (current >= '0' && current <= '9')
                || (current >= 'a' && current <= 'f')
                || (current >= 'A' && current <= 'F');
            if (!valid) {
                return false;
            }
        }
        return value.length() == 6;
    }
}
