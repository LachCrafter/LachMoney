package de.lachcrafter.lachMoney;

import de.lachcrafter.lachMoney.database.DatabaseManager;
import de.lachcrafter.lachMoney.managers.ConfigManager;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class LachMoney extends JavaPlugin {

    private ConfigManager configManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Initializing LachMoney...");
        this.configManager = new ConfigManager(this, databaseManager);
        this.databaseManager = new DatabaseManager(this, configManager);
        regCommands();
        getDataFolder().mkdirs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        databaseManager.closeConnection();
    }

    public void regCommands() {
        LifecycleEventManager<@NotNull Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
        });
    }
}
