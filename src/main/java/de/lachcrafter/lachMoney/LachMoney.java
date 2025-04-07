package de.lachcrafter.lachMoney;

import de.lachcrafter.lachMoney.commands.MoneyCommand;
import de.lachcrafter.lachMoney.database.DatabaseManager;
import de.lachcrafter.lachMoney.listener.PlayerJoinListener;
import de.lachcrafter.lachMoney.managers.ConfigManager;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class LachMoney extends JavaPlugin {

    public ConfigManager configManager;
    public DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Initializing LachMoney...");
        this.configManager = new ConfigManager(this);
        this.databaseManager = new DatabaseManager(this, configManager);
        regCommands();
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.databaseManager.closeConnection();
    }

    public void regListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    public void regCommands() {
        LifecycleEventManager<@NotNull Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("money", "Displays your current balance.", new MoneyCommand(this));
        });
    }
}
