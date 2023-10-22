package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.commands.validator.Validator;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import dev.ronse.redalert.util.OrefDistrictsUtil;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class RedAlertTestCommand implements IRedAlertCommand {
    @Override
    public void onCommand(CommandSender sender, List<String> args) {
        int alertCategory = 1;
        int alertCount = 1;

        if(!args.isEmpty()) alertCategory = OrefAlertType.valueOf(args.get(0)).getId();
        if(args.size() > 1) alertCount = Integer.parseInt(args.get(1));

        RedAlert.getInstance().notifier.test(new OrefAlert(
                0,
                OrefAlertType.get(alertCategory),
                OrefDistrictsUtil.getRandomStates(alertCount),
                "התראת בדיקה"
        ));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        if(args.size() == 1)
            return Arrays.stream(OrefAlertType.values()).map(Enum::name).toList();

        return List.of();
    }

    @Validator(name = "Category", position = 0, required = false)
    boolean validateCategory(String value) {
        try {
            OrefAlertType.valueOf(value);
        } catch (IllegalArgumentException ignored) {
            return false;
        }

        return true;
    }

    @Validator(name = "Count", position = 1, required = false)
    boolean validateCount(String value) {
        try {
            int n = Integer.parseInt(value);
            return n > 0 && n <= 500;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public String permission() {
        return "redalert.admin";
    }

    @Override
    public int maxArgs() {
        return 2;
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getHelp() {
        return "Creates a test notification. Usage: /redalert test <Category> <Number of districts>";
    }
}

