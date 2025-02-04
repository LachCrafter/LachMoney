package de.lachcrafter.lachMoney.managers;

import de.lachcrafter.lachMoney.LachMoney;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private final LachMoney plugin;
    private final MiniMessage mm = MiniMessage.miniMessage();

    private FileConfiguration config;
    private FileConfiguration database;
    private FileConfiguration messages;

    private File configFile;
    private File databaseFile;
    private File messagesFile;

    public ConfigManager(LachMoney plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        databaseFile = new File(plugin.getDataFolder(), "database.yml");
        if (!databaseFile.exists()) {
            plugin.saveResource("database.yml", false);
        }
        database = YamlConfiguration.loadConfiguration(databaseFile);

        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public Component getNoPermissionMessage() {
        return mm.deserialize(messages.getString("no-permission", "<red>You don't have permission to use this command."));
    }

}
