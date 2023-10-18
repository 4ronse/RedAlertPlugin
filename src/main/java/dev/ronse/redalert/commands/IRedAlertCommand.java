package dev.ronse.redalert.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface IRedAlertCommand {
    default String permission() { return null; }
    default int numArgs() { return 0; }
    default int minArgs() { return -1; }
    default int maxArgs() { return -1; }

    String getName();

    void onCommand(CommandSender sender, List<String> args);
    default List<String> onTabComplete(CommandSender sender, List<String> args) { return List.of(); };
}
