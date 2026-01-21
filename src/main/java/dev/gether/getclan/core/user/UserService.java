/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.sql.Connection
 *  java.sql.PreparedStatement
 *  java.sql.ResultSet
 *  java.sql.SQLException
 *  java.sql.Statement
 *  java.util.Arrays
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Objects
 *  java.util.Set
 *  java.util.UUID
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.core.user;

import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.user.User;
import dev.gether.getclan.database.DatabaseType;
import dev.gether.getclan.database.GetTable;
import dev.gether.getclan.database.MySQL;
import dev.gether.getclan.database.QueuedQuery;
import dev.gether.getconfig.utils.MessageUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public class UserService
implements GetTable {
    private final String table = "get_clan_users";
    private final MySQL mySQL;
    private final FileManager fileManager;

    public UserService(MySQL mySQL, FileManager fileManager) {
        this.mySQL = mySQL;
        this.fileManager = fileManager;
        this.createTable();
    }

    @Override
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS get_clan_users (id " + (this.fileManager.getDatabaseConfig().getDatabaseType() == DatabaseType.SQLITE ? "INTEGER PRIMARY KEY AUTOINCREMENT" : "INT(10) AUTO_INCREMENT PRIMARY KEY") + ",uuid VARCHAR(100),username VARCHAR(100),kills INT(11) DEFAULT 0,deaths INT(11) DEFAULT 0,points INT(11) DEFAULT 0,clan_tag VARCHAR(100) NULL)";
        try (Statement stmt = this.mySQL.getHikariDataSource().getConnection().createStatement();){
            stmt.execute(query);
        }
        catch (SQLException e) {
            MessageUtil.logMessage("\u001b[0;31m", "Cannot create the table get_clan_users. Error " + e.getMessage());
        }
    }

    public void createUser(Player player, int points) {
        String sql = "INSERT INTO get_clan_users (uuid, username, points) VALUES (?, ?, ?)";
        List parameters = Arrays.asList((Object[])new Object[]{player.getUniqueId().toString(), player.getName(), points});
        this.mySQL.addQueue(new QueuedQuery(sql, (List<Object>)parameters));
    }

    public void updateUser(User user) {
        if (user == null) {
            MessageUtil.logMessage("\u001b[0;31m", "Something wrong! User is null " + user.getUuid());
            return;
        }
        String sql = "UPDATE get_clan_users SET kills = ? , deaths = ? , points = ? , clan_tag = ? WHERE uuid = ?";
        List parameters = Arrays.asList((Object[])new Object[]{user.getKills(), user.getDeath(), user.getPoints(), user.getTag(), user.getUuid().toString()});
        this.mySQL.addQueue(new QueuedQuery(sql, (List<Object>)parameters));
    }

    public Set<User> loadUsers() {
        HashSet users = new HashSet();
        int countUser = 0;
        MessageUtil.logMessage("\u001b[0;33m", "Loading users...");
        String sql = "SELECT * FROM get_clan_users";
        try (Connection conn = this.mySQL.getHikariDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery();){
            while (resultSet.next()) {
                UUID uuid = UUID.fromString((String)resultSet.getString("uuid"));
                String username = resultSet.getString("username");
                int kills = resultSet.getInt("kills");
                int deaths = resultSet.getInt("deaths");
                int points = resultSet.getInt("points");
                String tag = resultSet.getString("clan_tag");
                users.add((Object)new User(uuid, username, kills, deaths, points, tag));
                ++countUser;
            }
        }
        catch (SQLException e) {
            MessageUtil.logMessage("\u001b[0;31m", e.getMessage());
        }
        MessageUtil.logMessage("\u001b[0;32m", "Successfully loaded " + countUser + " users");
        return users;
    }

    @Override
    public String getTable() {
        Objects.requireNonNull((Object)this);
        return "get_clan_users";
    }
}

