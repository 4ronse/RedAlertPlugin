package dev.ronse.redalert.commands;

import dev.ronse.redalert.orefalerts.OrefAlertType;
import dev.ronse.redalert.util.TextUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RedAlertListNotifiers implements IRedAlertCommand {
    @Override
    public void onCommand(CommandSender sender, List<String> args) {
        StringBuilder builder = new StringBuilder("<color:#A00000>[RedAlert] <reset>Alert types: ");
        OrefAlertType[] alertTypes = OrefAlertType.values();
        String[] alertTypeNames = new String[alertTypes.length];

        for (int i = 0; i < alertTypes.length; i++)
            alertTypeNames[i] = String.format("<hover:show_text:'Copy %s'><click:copy_to_clipboard:%s>%s</click></hover>",
                    alertTypes[i].name(), alertTypes[i].name(), alertTypes[i].name());

        builder.append(String.join("<bold><red> | <reset>", alertTypeNames));

        sender.sendMessage(TextUtil.deserialize(builder.toString()));
    }

    @Override
    public int numArgs() {
        return 0;
    }

    @Override
    public String getName() {
        return "listnotifiers";
    }

    @Override
    public String getHelp() {
        return "Lists all available notifiers. Usage: /redalert listnotifiers";
    }
}
