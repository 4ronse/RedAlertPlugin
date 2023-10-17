package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RedAlertDump implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final TextComponent comp = Component.text("Red alert")
                .append(Component.newline())
                .append(Component.text("Debug: " + RedAlert.config.debug))

                .append(Component.newline())
                .append(Component.text("DebugSource: " + RedAlert.config.debugSource))

                .append(Component.newline())
                .append(Component.text("Delay: " + RedAlert.config.delay));

        commandSender.sendMessage(comp.appendNewline().append(Component.text(RedAlert.config.toString())));

        return true;
    }
}
