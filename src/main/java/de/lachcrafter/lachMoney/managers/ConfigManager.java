package de.lachcrafter.lachMoney.managers;

import de.lachcrafter.lachMoney.LachMoney;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigManager {

    private final LachMoney plugin;
    private final MiniMessage mm = MiniMessage.miniMessage();

    private FileConfiguration config;
    private FileConfiguration database;
    private FileConfiguration messages;

    public ConfigManager(LachMoney plugin) {
        this.plugin = plugin;
        loadConfig();
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

    public Component getMessage(String path) {
        return mm.deserialize(config.getString(path, "message "+ path + "not found"));
    }

    public @NotNull Component getBalanceMessage(long amount) {
        String rawMessage = messages.getString("player_balance", "<gold>You currently have <red><amount><currency></red> in your wallet.");
        return mm.deserialize(rawMessage,
                Placeholder.component("amount", Component.text(amount)),
                Placeholder.unparsed("currency", getCurrency()));
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
                plugin.getLogger().severe("Unknown Database type defined in database.yml! Disabling to prevent data loss...");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return DataBaseType.UNKNOWN;
        }
    }

    public @NotNull String getCurrency() {
        return config.getString("currency", "$");
    }

    public String getMySQLHost() {
        return database.getString("mysql.host", "example.com");
    }

    public String getMySQLDatabase() {
        return database.getString("mysql.database", "lachmoney");
    }

    public int getMySQLPort() {
        return database.getInt("mysql.port", 3306);
    }

    public String getMySQLUsername() {
        return database.getString("mysql.username", "lachmoney");
    }

    public String getMySQLPassword() {
        return database.getString("mysql.password", "yourpassword");
    }

    public enum DataBaseType {
        SQLITE,
        MYSQL,
        UNKNOWN
    }
}
