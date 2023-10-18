package dev.ronse.redalert.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface IRedAlertCommand {
    void onCommand(CommandSender sender, List<String> args);
    default List<String> onTabComplete(CommandSender sender, List<String> args) { return List.of(); }

    default String permission() { return null; }
    default int numArgs() { return -1; }
    default int minArgs() { return -1; }
    default int maxArgs() { return -1; }
    default boolean consoleAllowed() { return true; }

    String getName();
    String getHelp();
}
