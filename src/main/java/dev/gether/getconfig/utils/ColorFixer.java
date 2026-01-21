/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.md_5.bungee.api.ChatColor
 */
package dev.gether.getconfig.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;

public class ColorFixer {
    static Pattern pattern = Pattern.compile((String)"#[a-fA-F0-9]{6}");

    private ColorFixer() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> addColors(List<String> input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        for (int i = 0; i < input.size(); ++i) {
            input.set(i, ColorFixer.addColors(input.get(i)));
        }
        return input;
    }

    public static String addColors(String text) {
        Matcher matcher = pattern.matcher((CharSequence)text);
        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace((CharSequence)color, (CharSequence)("" + ChatColor.of((String)color)));
            matcher = pattern.matcher((CharSequence)text);
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)text);
    }
}
