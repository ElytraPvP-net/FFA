package net.elytrapvp.ffa.runnables;

import net.elytrapvp.ffa.MySQL;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeaderboardUpdate extends BukkitRunnable {
    public static Map<Integer, Value> kills = new HashMap<>();
    public static Map<Integer, Value> deaths = new HashMap<>();
    public static Map<Integer, Value> coins = new HashMap<>();
    public static Map<Integer, Value> killStreaks = new HashMap<>();

    @Override
    public void run() {
        kills();
        deaths();
        coins();
        killStreaks();
    }

    private void kills() {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from ep_statistics ORDER BY kills DESC");
            ResultSet results = statement.executeQuery();

            int i = 0;
            while(results.next() && i < 28) {
                i++;

                UUID u = UUID.fromString(results.getString(1));
                int num = results.getInt(2);

                kills.put(i, new Value(u, num));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deaths() {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from ep_statistics ORDER BY deaths DESC");
            ResultSet results = statement.executeQuery();

            int i = 0;
            while(results.next() && i < 28) {
                i++;

                UUID u = UUID.fromString(results.getString(1));
                int num = results.getInt(3);

                deaths.put(i, new Value(u, num));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void coins() {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from ep_players ORDER BY coins DESC");
            ResultSet results = statement.executeQuery();

            int i = 0;
            while(results.next() && i < 28) {
                i++;

                UUID u = UUID.fromString(results.getString(1));
                int num = results.getInt(2);

                coins.put(i, new Value(u, num));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void killStreaks() {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from ep_statistics ORDER BY highestKillStreak DESC");
            ResultSet results = statement.executeQuery();

            int i = 0;
            while(results.next() && i < 28) {
                i++;

                UUID u = UUID.fromString(results.getString(1));
                int num = results.getInt(5);

                killStreaks.put(i, new Value(u, num));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Value {
        public UUID u;
        public int i;

        public Value(UUID u, int i) {
            this.u = u;
            this.i = i;
        }
    }
}
