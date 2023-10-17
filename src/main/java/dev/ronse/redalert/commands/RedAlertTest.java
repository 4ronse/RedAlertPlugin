package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class RedAlertTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        RedAlert.getInstance().notifier.test(new OrefAlert(
                0,
                OrefAlertType.MISSILE_ALERT,
                Set.of("אילת"),
                "שצ"
        ));

        return true;
    }
}
