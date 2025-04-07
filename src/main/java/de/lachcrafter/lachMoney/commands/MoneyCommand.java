package de.lachcrafter.lachMoney.commands;

import de.lachcrafter.lachMoney.LachMoney;
import de.lachcrafter.lachMoney.managers.Money;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public class MoneyCommand implements BasicCommand {

    private final LachMoney plugin;

    public MoneyCommand(LachMoney plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack stack, String @NotNull [] args) {
        Money amount = plugin.databaseManager.getMoney(stack.getExecutor().getUniqueId().toString());
        stack.getSender().sendMessage(plugin.configManager.getBalanceMessage(amount));
    }
}
