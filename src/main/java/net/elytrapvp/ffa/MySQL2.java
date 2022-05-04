package net.elytrapvp.ffa;

import net.elytrapvp.ffa.managers.SettingsManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL2 {
    private static final SettingsManager settings = SettingsManager.getInstance();

    private static Connection connection;
    private static final String host = settings.getConfig().getString("MySQL.host");
    private static final String database = "development";
    private static final String username = settings.getConfig().getString("MySQL.username");
    private static final String password = settings.getConfig().getString("MySQL.password");
    private static final int port = settings.getConfig().getInt("MySQL.port");

    /**
     * Close a connection.
     */
    public static void closeConnection() {
        if(isConnected()) {
            try {
                connection.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the connection.
     * @return Connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Get if plugin is connected to the database.
     * @return Connected
     */
    private static boolean isConnected() {
        return (connection != null);
    }

    /**
     * Open a MySQL Connection
     */
    public static void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            synchronized(FFA.class) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false", username, password);
            }
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}