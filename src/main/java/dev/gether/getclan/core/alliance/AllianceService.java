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
 *  java.util.List
 *  java.util.Map
 *  java.util.Objects
 */
package dev.gether.getclan.core.alliance;

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
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AllianceService
implements GetTable {
    private final String table = "get_alliance";
    private final MySQL mySQL;

    public AllianceService(MySQL mySQL) {
        this.mySQL = mySQL;
        this.createTable();
    }

    public void createAlliance(String clanTagFirst, String clanTagSecond) {
        String sql = "INSERT INTO get_alliance (clan_tag1, clan_tag2) VALUES (?, ?)";
        List parameters = Arrays.asList((Object[])new Object[]{clanTagFirst, clanTagSecond});
        this.mySQL.addQueue(new QueuedQuery(sql, (List<Object>)parameters));
    }

    public void deleteAlliance(String tag) {
        String sql = "DELETE FROM get_alliance WHERE clan_tag1 = ? OR clan_tag2 = ?";
        List parameters = Arrays.asList((Object[])new Object[]{tag, tag});
        this.mySQL.addQueue(new QueuedQuery(sql, (List<Object>)parameters));
    }

    public Map<String, String> loadAlliances() {
        int allianceCount = 0;
        HashMap alliances = new HashMap();
        MessageUtil.logMessage("\u001b[0;32m", "Loading alliances...");
        String sql = "SELECT clan_tag1, clan_tag2 FROM get_alliance";
        try (Connection conn = this.mySQL.getHikariDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery();){
            while (resultSet.next()) {
                String clanTag1 = resultSet.getString("clan_tag1");
                String clanTag2 = resultSet.getString("clan_tag2");
                alliances.put((Object)clanTag1, (Object)clanTag2);
                ++allianceCount;
            }
        }
        catch (SQLException e) {
            MessageUtil.logMessage("\u001b[0;31m", "Error loading alliances: " + e.getMessage());
        }
        MessageUtil.logMessage("\u001b[0;32m", "Successfully loaded " + allianceCount + " alliances");
        return alliances;
    }

    @Override
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS get_alliance (clan_tag1 VARCHAR(20),clan_tag2 VARCHAR(20))";
        try (Statement stmt = this.mySQL.getHikariDataSource().getConnection().createStatement();){
            stmt.execute(query);
        }
        catch (SQLException e) {
            MessageUtil.logMessage("\u001b[0;31m", "Cannot create the table get_alliance. Error " + e.getMessage());
        }
    }

    @Override
    public String getTable() {
        Objects.requireNonNull((Object)this);
        return "get_alliance";
    }
}

