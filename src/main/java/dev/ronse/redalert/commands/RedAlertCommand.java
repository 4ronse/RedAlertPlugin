package dev.ronse.redalert.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedAlertCommand implements CommandExecutor, TabCompleter {
    private final List<IRedAlertCommand> commands = new ArrayList<>();

    public void registerCommand(IRedAlertCommand command) { commands.add(command); }

    private IRedAlertCommand getCommand(String cmdName) {
        for(var cmd : commands) if(cmd.getName().equalsIgnoreCase(cmdName)) return cmd;
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command _ignore, @NotNull String label, @NotNull String[] aArgs) {
        List<String> args = new ArrayList<>(Arrays.asList(aArgs));

        String commandName = args.isEmpty() ? "help" : args.remove(0);
        IRedAlertCommand command = getCommand(commandName);

        if (command != null) {
            command.onCommand(sender, args);
        } else {
            sender.sendMessage("Piss off");
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command _ignore, @NotNull String label, @NotNull String[] aArgs) {
        List<String> args = new ArrayList<>(Arrays.asList(aArgs));
        String commandName = args.remove(0);

        IRedAlertCommand command = getCommand(commandName);

        if(commandName.isEmpty() || commandName.isBlank()) return commands.stream().map(IRedAlertCommand::getName).toList();
        if(command != null) return command.onTabComplete(sender, args);
        return List.of();
    }

}
