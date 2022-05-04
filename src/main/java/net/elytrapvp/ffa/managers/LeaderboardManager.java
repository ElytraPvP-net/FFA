package net.elytrapvp.ffa.managers;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.MySQL;
import net.elytrapvp.ffa.MySQL2;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LeaderboardManager {
    private final FFA plugin;
    private final Map<String, Map<String, Integer>> leaderboard = new HashMap<>();

    public LeaderboardManager(FFA plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 0, 20*60*5);
    }

    public Map<String, Integer> getLeaderboard(String type) {
        return leaderboard.get(type);
    }

    private void update() {
        leaderboard.clear();
        String[] leaderboardTypes = new String[]{"kills", "deaths", "coins", "highestKillStreak"};

        for(String type : leaderboardTypes) {
            Map<String, Integer> data = new LinkedHashMap<>();
            leaderboard.put(type, data);

            try {
                String table = (type.equals("coins")) ? "ep_players" : "ep_statistics";

                PreparedStatement statement1 = MySQL.getConnection().prepareStatement("SELECT * FROM " + table + " ORDER BY " + type + " DESC");
                ResultSet results1 = statement1.executeQuery();

                int i = 0;
                while(i < 10) {
                    results1.next();
                    String uuid = results1.getString(1);

                    PreparedStatement statement2 = MySQL2.getConnection().prepareStatement("SELECT * from player_info WHERE uuid = ? LIMIT 1");
                    statement2.setString(1, uuid);
                    ResultSet results2 = statement2.executeQuery();

                    if(results2.next()) {
                        int row = 0;
                        switch (type) {
                            case "kills", "coins" -> row = 2;
                            case "deaths" -> row = 3;
                            case "highestKillStreak" -> row = 5;
                        }
                        data.put(results2.getString(2), results1.getInt(row));
                        i++;
                    }
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
