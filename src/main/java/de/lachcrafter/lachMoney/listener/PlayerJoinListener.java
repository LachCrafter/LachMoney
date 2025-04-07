package de.lachcrafter.lachMoney.listener;

import de.lachcrafter.lachMoney.LachMoney;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final LachMoney lachMoney;

    public PlayerJoinListener(LachMoney lachMoney) {
        this.lachMoney = lachMoney;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!lachMoney.databaseManager.checkPlayer(event.getPlayer().getUniqueId().toString())) {
            lachMoney.databaseManager.registerPlayer(player.getUniqueId().toString());
        }
    }

}
