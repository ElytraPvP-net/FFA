package net.elytrapvp.ffa.runnables;

import net.elytrapvp.ffa.MySQL;
import net.elytrapvp.ffa.MySQL2;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class MySQLHeartBeat extends BukkitRunnable {
    @Override
    public void run() {
        try {
            MySQL.getConnection().isValid(0);
            MySQL2.getConnection().isValid(0);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}