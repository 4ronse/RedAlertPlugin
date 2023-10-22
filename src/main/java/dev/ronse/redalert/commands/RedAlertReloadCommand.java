package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.util.TextUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RedAlertReloadCommand implements IRedAlertCommand {
    @Override
    public void onCommand(CommandSender sender, List<String> args) {
        RedAlert.getInstance().reloadConfig();

        sender.sendMessage(TextUtil.deserialize("<color:#A00000>[Red Alert] <color:#00A000>Reloaded config.yml"));
    }

    @Override
    public String permission() {
        return "redalert.admin";
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getHelp() {
        return "Reloads config. Usage: /redalert reload";
    }

    @Override
    public int numArgs() {
        return 0;
    }
}
