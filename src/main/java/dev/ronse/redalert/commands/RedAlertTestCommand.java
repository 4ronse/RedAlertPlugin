package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.commands.validator.Validator;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import dev.ronse.redalert.util.Translator;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RedAlertTestCommand implements IRedAlertCommand {
    // TODO: Limit alert count to the amount of districts available

    @Override
    public void onCommand(CommandSender sender, List<String> args) {
        int alertCategory = 1;
        int alertCount = 1;

        if(!args.isEmpty()) alertCategory = OrefAlertType.valueOf(args.get(0)).getId();
        if(args.size() > 1) alertCount = Integer.parseInt(args.get(1));

        RedAlert.getInstance().notifier.test(new OrefAlert(
                0,
                OrefAlertType.get(alertCategory),
                Translator.getRandomStates(alertCount),
                "שצ"
        ));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        if(args.size() == 1)
            return Arrays.stream(OrefAlertType.values()).map(Enum::name).toList();

        if(args.size() == 2)
            return IntStream.rangeClosed(1, 50)
                    .boxed()
                    .toList().stream().map(String::valueOf).toList();

        return List.of();
    }

    @Override
    public String permission() {
        return "redalert.test";
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

