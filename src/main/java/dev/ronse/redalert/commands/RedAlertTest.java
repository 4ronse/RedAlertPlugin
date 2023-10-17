package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import dev.ronse.redalert.util.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class RedAlertTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        int alertCategory = 1;
        int alertCount = 1;

        if(args.length > 0) alertCategory = Integer.parseInt(args[0]);
        if(args.length > 1) alertCount = Integer.parseInt(args[1]);

        RedAlert.getInstance().notifier.test(new OrefAlert(
                0,
                OrefAlertType.get(alertCategory),
                Translator.getRandomStates(alertCount),
                "שצ"
        ));

        return true;
    }
}
