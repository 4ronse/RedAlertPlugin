package dev.ronse.redalert.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RedAlertHelpCommand implements IRedAlertCommand {
    private final RedAlertCommand main;

    public RedAlertHelpCommand(RedAlertCommand main) {
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, List<String> args) {
        List<IRedAlertCommand> allowedCommands = getAllowedCommands(sender);

        if(args.isEmpty()) {
            allowedCommands.forEach(cmd -> sender.sendMessage(
                    Component.text("/redalert").color(TextColor.fromHexString("#FF0000"))
                            .append(Component.space())
                            .append(Component.text(cmd.getName()).color(TextColor.fromHexString("#A00000")))
                            .append(Component.text(" - ").color(TextColor.fromHexString("#FF0000")))
                            .append(Component.text(cmd.getHelp()).color(TextColor.fromHexString("#A00000")))
            ));

            return;
        }

        IRedAlertCommand cmd = null;

        for(var c : allowedCommands) {
            if(c.getName().equalsIgnoreCase(args.get(0))) {
                cmd = c;
                break;
            }
        }

        if(cmd == null) {
            sender.sendMessage("Command not found");
            return;
        }

        sender.sendMessage(
                Component.text("/redalert").color(TextColor.fromHexString("#FF0000"))
                        .append(Component.space())
                        .append(Component.text(cmd.getName()).color(TextColor.fromHexString("#A00000")))
                        .append(Component.text(" - ").color(TextColor.fromHexString("#FF0000")))
                        .append(Component.text(cmd.getHelp()).color(TextColor.fromHexString("#A00000")))
        );
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return IRedAlertCommand.super.onTabComplete(sender, List.of());
    }

    @Override
    public int maxArgs() {
        return 1;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows information about command. Usage: /redalert help [<subcommand>]";
    }

    private List<IRedAlertCommand> getAllowedCommands(CommandSender sender) {
        return main.getCommands().stream().filter(cmd -> {
            if(cmd.permission() == null) return true;
            return sender.hasPermission(cmd.permission());
        }).toList();
    }
}
