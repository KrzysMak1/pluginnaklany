/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariConfig
 *  com.zaxxer.hikari.HikariDataSource
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.sql.Connection
 *  java.sql.PreparedStatement
 *  java.sql.SQLException
 *  java.util.ArrayDeque
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Queue
 *  lombok.Generated
 *  org.bukkit.plugin.java.JavaPlugin
 */
package dev.gether.getclan.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.config.domain.DatabaseConfig;
import dev.gether.getclan.database.DatabaseType;
import dev.gether.getclan.database.QueuedQuery;
import dev.gether.getconfig.utils.MessageUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import lombok.Generated;
import org.bukkit.plugin.java.JavaPlugin;

public class MySQL {
    private final GetClan plugin;
    private final FileManager fileManager;
    private HikariDataSource hikariDataSource;
    private final Queue<QueuedQuery> queuedQueries = new ArrayDeque<>();

    public MySQL(GetClan plugin, FileManager fileManager) {
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.connect(plugin);
    }

    private void connect(JavaPlugin plugin) {
        DatabaseConfig databaseConfig = this.fileManager.getDatabaseConfig();
        HikariConfig config = new HikariConfig();
        if (databaseConfig.getDatabaseType() == DatabaseType.MYSQL) {
            config.setJdbcUrl("jdbc:mysql://" + databaseConfig.getHost() + ":" + databaseConfig.getPort() + "/" + databaseConfig.getDatabase());
            config.setUsername(databaseConfig.getUsername());
            config.setPassword(databaseConfig.getPassword());
        } else {
            config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + "/database.db");
        }
        config.addDataSourceProperty("cachePrepStmts", (Object)"true");
        config.addDataSourceProperty("prepStmtCacheSize", (Object)"250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", (Object)"2048");
        this.hikariDataSource = new HikariDataSource(config);
    }

    public void disconnect() {
        if (this.hikariDataSource != null) {
            this.hikariDataSource.close();
        }
    }

    public boolean isConnected() {
        return this.hikariDataSource != null && !this.hikariDataSource.isClosed();
    }

    public void executeQueued() {
        int BATCH_LIMIT = 1000;
        int countQuery = 0;
        int nullSize = 0;
        ArrayList<QueuedQuery> batchList = new ArrayList<>();
        ArrayList<QueuedQuery> allQueries = new ArrayList<>(this.queuedQueries);
        this.queuedQueries.clear();
        try {
            Connection conn = this.hikariDataSource.getConnection();
            try {
                conn.setAutoCommit(false);
                for (QueuedQuery queuedQuery : allQueries) {
                    if (queuedQuery == null) {
                        ++nullSize;
                        continue;
                    }
                    batchList.add(queuedQuery);
                    if (batchList.size() != 1000) continue;
                    try {
                        this.executeBatch(conn, batchList);
                        countQuery += batchList.size();
                        batchList.clear();
                    }
                    catch (SQLException e) {
                        conn.rollback();
                        MessageUtil.logMessage("\u001b[0;31m", "[MySQL] - Error executing batch: " + e.getMessage());
                        if (conn != null) {
                            conn.close();
                        }
                        return;
                    }
                }
                if (!batchList.isEmpty()) {
                    try {
                        this.executeBatch(conn, batchList);
                        countQuery += batchList.size();
                    }
                    catch (SQLException e) {
                        conn.rollback();
                        MessageUtil.logMessage("\u001b[0;31m", "[MySQL] - Error executing remaining batch: " + e.getMessage());
                        if (conn != null) {
                            conn.close();
                        }
                        return;
                    }
                }
                conn.commit();
                MessageUtil.logMessage("\u001b[0;32m", "Successfully executed " + countQuery + " queries.");
            }
            finally {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (SQLException e) {
                        MessageUtil.logMessage("\u001b[0;31m", "[MySQL] - Error closing connection: " + e.getMessage());
                    }
                }
            }
        }
        catch (SQLException e) {
            MessageUtil.logMessage("\u001b[0;31m", "[MySQL] - Error obtaining connection or handling transaction: " + e.getMessage());
        }
        MessageUtil.logMessage("\u001b[0;31m", "[getClan] Null size: " + nullSize);
    }

    private void executeBatch(Connection conn, List<QueuedQuery> batchList) throws SQLException {
        if (batchList.isEmpty()) {
            return;
        }
        try {
            for (QueuedQuery queuedQuery : batchList) {
                PreparedStatement statement = conn.prepareStatement(queuedQuery.getSql());
                try {
                    List<Object> parameters = queuedQuery.getParameters();
                    for (int i = 0; i < parameters.size(); ++i) {
                        Object param = parameters.get(i);
                        statement.setObject(i + 1, param);
                    }
                    statement.addBatch();
                    statement.executeBatch();
                }
                finally {
                    if (statement == null) continue;
                    statement.close();
                }
            }
        }
        catch (SQLException e) {
            throw new SQLException("Error executing batch: " + e.getMessage(), (Throwable)e);
        }
    }

    public void addQueue(QueuedQuery queuedQuery) {
        if (queuedQuery == null) {
            MessageUtil.logMessage("\u001b[0;31m", "HHHHHHHHERE!");
            return;
        }
        this.queuedQueries.add(queuedQuery);
    }

    @Generated
    public HikariDataSource getHikariDataSource() {
        return this.hikariDataSource;
    }
}
