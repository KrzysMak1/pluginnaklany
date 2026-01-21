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
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Map
 *  java.util.Objects
 *  java.util.Set
 *  java.util.UUID
 *  org.bukkit.entity.Player
 */
package dev.gether.getclan.core.clan;

import dev.gether.getclan.GetClan;
import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.clan.Clan;
import dev.gether.getclan.core.upgrade.LevelData;
import dev.gether.getclan.core.upgrade.UpgradeType;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public class ClanService
implements GetTable {
    private final String table = "get_clans";
    private final MySQL mySQL;
    private final FileManager fileManager;
    private final GetClan plugin;

    public ClanService(MySQL mySQL, FileManager fileManager, GetClan plugin) {
        this.mySQL = mySQL;
        this.fileManager = fileManager;
        this.plugin = plugin;
        this.createTable();
    }

    @Override
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS get_clans (id " + (this.fileManager.getDatabaseConfig().getDatabaseType() == DatabaseType.SQLITE ? "INTEGER PRIMARY KEY AUTOINCREMENT" : "INT(10) AUTO_INCREMENT PRIMARY KEY") + ",tag VARCHAR(10),uuid VARCHAR(100),owner_uuid VARCHAR(100),deputy_uuid VARCHAR(100),upgrade_members INTEGER(11) DEFAULT 0,members_deposit INTEGER(11) DEFAULT 0,upgrade_drop_boost INTEGER(11) DEFAULT 0,drop_boost_deposit INTEGER(11) DEFAULT 0,upgrade_points_boost INTEGER(11) DEFAULT 0,points_boost_deposit INTEGER(11) DEFAULT 0,pvpEnable " + (this.fileManager.getDatabaseConfig().getDatabaseType() == DatabaseType.SQLITE ? "INTEGER" : "BOOLEAN") + ")";
        try (Statement stmt = this.mySQL.getHikariDataSource().getConnection().createStatement();){
            stmt.execute(query);
        }
        catch (SQLException e) {
            MessageUtil.logMessage("\u001b[0;31m", "Cannot create the table get_clans. Error " + e.getMessage());
        }
    }

    public void createClan(Clan clan, Player player) {
        String sql = "INSERT INTO get_clans (tag, uuid, owner_uuid) VALUES (?, ?, ?)";
        List parameters = Arrays.asList((Object[])new Object[]{clan.getTag(), clan.getUuid().toString(), player.getUniqueId().toString()});
        this.mySQL.addQueue(new QueuedQuery(sql, (List<Object>)parameters));
    }

    public void deleteClan(String tag) {
        String sql = "DELETE FROM get_clans WHERE tag = ?";
        List parameters = Arrays.asList((Object[])new Object[]{tag});
        this.mySQL.addQueue(new QueuedQuery(sql, (List<Object>)parameters));
    }

    public Set<Clan> loadClans() {
        int countClan = 0;
        HashSet clans = new HashSet();
        MessageUtil.logMessage("\u001b[0;33m", "Loading clans...");
        String sql = "SELECT * FROM get_clans";
        try (Connection conn = this.mySQL.getHikariDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery();){
            while (resultSet.next()) {
                String tag = resultSet.getString("tag");
                String clanUuid = resultSet.getString("uuid");
                String ownerUuid = resultSet.getString("owner_uuid");
                String deputyUuid = resultSet.getString("deputy_uuid");
                boolean pvpEnable = resultSet.getBoolean("pvpEnable");
                int upgradeMembers = resultSet.getInt("upgrade_members");
                int membersDeposit = resultSet.getInt("members_deposit");
                int upgradeDropBoost = resultSet.getInt("upgrade_drop_boost");
                int dropBoostDeposit = resultSet.getInt("drop_boost_deposit");
                int upgradePointsBoost = resultSet.getInt("upgrade_points_boost");
                int pointsBoostDeposit = resultSet.getInt("points_boost_deposit");
                UUID ownerUUID = UUID.fromString((String)ownerUuid);
                UUID clanUUID = UUID.fromString((String)clanUuid);
                UUID deputyUUID = null;
                if (deputyUuid != null && !deputyUuid.isEmpty()) {
                    deputyUUID = UUID.fromString((String)deputyUuid);
                }
                HashMap upgrades = new HashMap();
                upgrades.put((Object)UpgradeType.MEMBERS, (Object)new LevelData(upgradeMembers, membersDeposit));
                upgrades.put((Object)UpgradeType.DROP_BOOST, (Object)new LevelData(upgradeDropBoost, dropBoostDeposit));
                upgrades.put((Object)UpgradeType.POINTS_BOOST, (Object)new LevelData(upgradePointsBoost, pointsBoostDeposit));
                Clan clan = new Clan(tag, clanUUID, ownerUUID, deputyUUID, pvpEnable, this.fileManager.getUpgradesConfig(), (Map<UpgradeType, LevelData>)upgrades);
                this.plugin.getClanManager().updateItem(clan);
                clans.add((Object)clan);
                ++countClan;
            }
        }
        catch (SQLException e) {
            MessageUtil.logMessage("\u001b[0;31m", "Error loading clans: " + e.getMessage());
        }
        MessageUtil.logMessage("\u001b[0;32m", "Successfully loaded " + countClan + " clans");
        return clans;
    }

    public void updateClan(Clan clan) {
        LevelData memberLevelData = (LevelData)clan.getUpgrades().get((Object)UpgradeType.MEMBERS);
        LevelData dropBoostLevelData = (LevelData)clan.getUpgrades().get((Object)UpgradeType.DROP_BOOST);
        LevelData pointsBoostLevelData = (LevelData)clan.getUpgrades().get((Object)UpgradeType.POINTS_BOOST);
        String sql = "UPDATE get_clans SET owner_uuid = ?, deputy_uuid = ?, pvpEnable = ?, upgrade_members = ?, members_deposit = ?, upgrade_drop_boost = ?, drop_boost_deposit = ?, upgrade_points_boost = ?, points_boost_deposit = ? WHERE tag = ?";
        List parameters = Arrays.asList((Object[])new Object[]{clan.getOwnerUUID() != null ? clan.getOwnerUUID().toString() : "", clan.getDeputyOwnerUUID() != null ? clan.getDeputyOwnerUUID().toString() : "", clan.isPvpEnable(), memberLevelData.getLevel(), memberLevelData.getDepositAmount(), dropBoostLevelData.getLevel(), dropBoostLevelData.getDepositAmount(), pointsBoostLevelData.getLevel(), pointsBoostLevelData.getDepositAmount(), clan.getTag()});
        this.mySQL.addQueue(new QueuedQuery(sql, (List<Object>)parameters));
    }

    @Override
    public String getTable() {
        Objects.requireNonNull((Object)this);
        return "get_clans";
    }
}

