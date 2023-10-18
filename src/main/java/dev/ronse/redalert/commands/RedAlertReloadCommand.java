package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.commands.validator.Validator;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RedAlertReloadCommand implements IRedAlertCommand {
    @Override
    public String permission() {
        return "redalert.reload";
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public int numArgs() {
        return 0;
    }

    @Override
    public void onCommand(CommandSender sender, List<String> args) {
        RedAlert.config.reload();

        sender.sendMessage(
                Component.text("[Red Alert]").color(TextColor.fromHexString("#A00000"))
                        .append(Component.text(" Reloaded config.yml").color(TextColor.fromHexString("#00A000"))));
    }
}
