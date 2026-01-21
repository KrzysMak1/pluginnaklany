/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  lombok.Generated
 */
package dev.gether.getclan.config.domain;

import dev.gether.getclan.database.DatabaseType;
import dev.gether.getconfig.GetConfig;
import dev.gether.getconfig.annotation.Comment;
import lombok.Generated;

public class DatabaseConfig
extends GetConfig {
    @Comment(value={"MYSQL, SQLITE"})
    private DatabaseType databaseType = DatabaseType.SQLITE;
    @Comment(value={"Host serwera MySQL"})
    private String host = "localhost";
    @Comment(value={"Nazwa u\u017cytkownika serwera MySQL"})
    private String username = "user";
    @Comment(value={"Has\u0142o do serwera MySQL"})
    private String password = "pass";
    @Comment(value={"Nazwa bazy danych"})
    private String database = "database_name";
    @Comment(value={"Port serwera MySQL"})
    private String port = "3306";
    @Comment(value={"U\u017cywa\u0107 SSL?"})
    private boolean ssl = false;

    @Generated
    public DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    @Generated
    public String getHost() {
        return this.host;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getDatabase() {
        return this.database;
    }

    @Generated
    public String getPort() {
        return this.port;
    }

    @Generated
    public boolean isSsl() {
        return this.ssl;
    }

    @Generated
    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    @Generated
    public void setHost(String host) {
        this.host = host;
    }

    @Generated
    public void setUsername(String username) {
        this.username = username;
    }

    @Generated
    public void setPassword(String password) {
        this.password = password;
    }

    @Generated
    public void setDatabase(String database) {
        this.database = database;
    }

    @Generated
    public void setPort(String port) {
        this.port = port;
    }

    @Generated
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    @Generated
    public DatabaseConfig(DatabaseType databaseType, String host, String username, String password, String database, String port, boolean ssl) {
        this.databaseType = databaseType;
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
        this.ssl = ssl;
    }

    @Generated
    public DatabaseConfig() {
    }
}

