/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.lang.Object
 *  lombok.Generated
 */
package dev.gether.getclan.config;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.domain.Config;
import dev.gether.getclan.config.domain.DatabaseConfig;
import dev.gether.getclan.config.domain.LangConfig;
import dev.gether.getclan.config.domain.UpgradesConfig;
import dev.gether.getconfig.ConfigManager;
import java.io.File;
import lombok.Generated;

public class FileManager {
    private final GetClan getClan;
    private Config config;
    private LangConfig langConfig;
    private DatabaseConfig databaseConfig;
    private UpgradesConfig upgradesConfig;

    public FileManager(GetClan getClan) {
        this.getClan = getClan;
        this.config = ConfigManager.create(Config.class, it -> {
            it.file(new File(getClan.getDataFolder(), "config.yml"));
            it.load();
        });
        this.langConfig = new LangConfig(getClan, this.config.getLangType());
        this.databaseConfig = ConfigManager.create(DatabaseConfig.class, it -> {
            it.file(new File(getClan.getDataFolder(), "database.yml"));
            it.load();
        });
        this.upgradesConfig = ConfigManager.create(UpgradesConfig.class, it -> {
            it.file(new File(getClan.getDataFolder(), "upgrades.yml"));
            it.load();
        });
    }

    public void reload() {
        this.config.load();
        this.upgradesConfig.load();
        this.langConfig = new LangConfig(this.getClan, this.config.getLangType());
        this.databaseConfig.load();
    }

    @Generated
    public GetClan getGetClan() {
        return this.getClan;
    }

    @Generated
    public Config getConfig() {
        return this.config;
    }

    @Generated
    public LangConfig getLangConfig() {
        return this.langConfig;
    }

    @Generated
    public DatabaseConfig getDatabaseConfig() {
        return this.databaseConfig;
    }

    @Generated
    public UpgradesConfig getUpgradesConfig() {
        return this.upgradesConfig;
    }
}

