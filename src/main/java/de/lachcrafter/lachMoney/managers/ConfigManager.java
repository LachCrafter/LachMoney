package de.lachcrafter.lachMoney.managers;

import de.lachcrafter.lachMoney.LachMoney;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private final LachMoney plugin;
    private final MiniMessage mm = MiniMessage.miniMessage();

    private FileConfiguration config;
    private FileConfiguration database;
    private FileConfiguration messages;

    public ConfigManager(LachMoney plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        File databaseFile = new File(plugin.getDataFolder(), "database.yml");
        if (!databaseFile.exists()) {
            plugin.saveResource("database.yml", false);
        }
        database = YamlConfiguration.loadConfiguration(databaseFile);

        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public Component getNoPermissionMessage() {
        return mm.deserialize(messages.getString("no-permission", "<red>You don't have permission to use this command."));
    }

    public int getStartMoney() {
        return config.getInt("start_money", 0);
    }

    public DataBaseType getDatabaseType() {
        String dbType = database.getString("db_type", "sqlite");

        switch (dbType) {
            case "sqlite":
                return DataBaseType.SQLITE;
            case "mysql":
                return DataBaseType.MYSQL;
            default:
                plugin.getLogger().severe("Unknown Database defined in database.yml! Disabling to prevent data loss...");
                plugin.getServer().shutdown();
                return DataBaseType.UNKNOWN;
        }
    }

    public String getMySQLHost() {
        return database.getString("auth.host", "example.com");
    }

    public String getMySQLDatabase() {
        return database.getString("auth.database", "lachmoney");
    }

    public int getMySQLPort() {
        return database.getInt("auth.port", 3306);
    }

    public String getMySQLUsername() {
        return database.getString("auth.username", "lachmoney");
    }

    public String getMySQLPassword() {
        return database.getString("auth.password", "yourpassword");
    }

    public enum DataBaseType {
        SQLITE,
        MYSQL,
        UNKNOWN
    }
}
