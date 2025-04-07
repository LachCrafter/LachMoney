package de.lachcrafter.lachMoney.database;

import de.lachcrafter.lachMoney.LachMoney;
import de.lachcrafter.lachMoney.managers.ConfigManager;
import de.lachcrafter.lachMoney.managers.Money;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class DatabaseManager {

    private final LachMoney plugin;
    private final ConfigManager configManager;
    private Connection connection;

    public DatabaseManager(LachMoney plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        switch (configManager.getDatabaseType()) {
            case SQLITE -> connectSQLite();
            case MYSQL -> connectMySQL();
        }
    }

    private void connectSQLite() {
        plugin.getLogger().info("USING SQLITE");
        File dataFolder = plugin.getDataFolder();
        File dataFile = new File(dataFolder, "data.db");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String url = "jdbc:sqlite:" + dataFile;

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
            plugin.getLogger().info("Connected to SQLite database successfully");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            plugin.getLogger().info("Could not connect to SQLite database, disabling...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
        init();
    }

    private void connectMySQL() {
        plugin.getLogger().info("USING MYSQL");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection("jdbc:mysql://" + configManager.getMySQLHost() + ":"
                            + configManager.getMySQLPort() + "/"
                            + configManager.getMySQLDatabase(),
                    configManager.getMySQLUsername(),
                    configManager.getMySQLPassword());
            plugin.getLogger().info("Connected to MySQL database successfully");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            plugin.getLogger().info("Could not connect to MySQL database, disabling...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
        init();
    }

    public void init() {
        String SQL = "CREATE TABLE IF NOT EXISTS player_data (" +
                "uuid VCHAR(36) PRIMARY KEY, " +
                "money DECIMAL(19,4));";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.executeUpdate();
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    public void registerPlayer(String uuid) {
        String checkSQL = "SELECT COUNT(*) FROM player_data WHERE uuid = ?";

        try (PreparedStatement checkPs = connection.prepareStatement(checkSQL)) {
            String SQL = "INSERT INTO player_data (uuid, money) VALUES (?, ?)";
            checkPs.setString(1, uuid);
            checkPs.setString(2, configManager.getStartMoney() + "");
            checkPs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(String uuid, Money amount) {
        String SQL = "UPDATE player_data SET money = money + ? WHERE uuid = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, amount + "");
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMoney(String uuid, Money amount) {
        String SQL = "UPDATE player_data SET money = money - ? WHERE uuid = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, amount + "");
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Money getMoney(String uuid) {
        String SQL = "SELECT money FROM player_data WHERE uuid = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, uuid);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return new Money(rs.getBigDecimal("money").toString());
            }
            return new Money("");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setBalance(String uuid, long amount) {
        String SQL = "UPDATE player_data SET money = ? WHERE uuid = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setLong(1, amount);
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPlayer(String uuid) {
        String SQL = "SELECT COUNT(*) FROM player_data WHERE uuid = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
