/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package dev.gether.getclan.config.domain;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.domain.LangType;
import dev.gether.getconfig.utils.MessageUtil;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LangConfig {
    private HashMap<String, String> langData = new HashMap<>();

    public LangConfig(GetClan getClan, LangType langType) {
        try {
            File file = new File(getClan.getDataFolder() + "/lang/", langType.name().toLowerCase() + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                MessageUtil.logMessage("\u001b[0;33m", "Creating file... " + file.getName());
                MessageUtil.logMessage("\u001b[0;31m", "Path: " + file.getAbsolutePath());
                getClan.saveResource("lang/" + file.getName(), false);
            }
            this.implementLangMessage(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void implementLangMessage(File file) throws IOException, InvalidConfigurationException {
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        config.save(file);
        config.getKeys(true).forEach(arg_0 -> this.lambda$implementLangMessage$0((FileConfiguration)config, arg_0));
    }

    public String getMessage(String key) {
        String message = this.langData.get(key);
        return message != null ? message : key;
    }

    private /* synthetic */ void lambda$implementLangMessage$0(FileConfiguration config, String key) {
        String value = config.isList(key) ? String.join((CharSequence)"\n", (Iterable)config.getStringList(key)) : config.getString(key);
        this.langData.put(key, value);
    }
}
