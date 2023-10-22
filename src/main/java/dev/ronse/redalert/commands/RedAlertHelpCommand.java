package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.util.TextUtil;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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
            sender.sendMessage(TextUtil.deserialize(
                    String.join("\n",
                            "<red>RedAlert הוא פלאגין חינמי שמתריע כאשר מתקבלת התראה מפיקוד העורף",
                            "<red><bold> הפלאגין אינו מהווה תחליף לאפליקציית פקע\"ר או להתראות הקוליות עצמן ולא ניתן להסתמך עליו!</bold>",
                            "<red>להורדה: <dl>",
                            "<red>לתרומה ❤: <donate>",
                            ""
                    ),
                    Placeholder.parsed("dl", String.format("<reset><color:#A00000><underlined><click:open_url:'%s'>%s<reset>",
                            RedAlert.GITHUB_PAGE, RedAlert.GITHUB_PAGE)),
                    Placeholder.parsed("donate", String.format("<reset><blue><underlined><click:open_url:'%s'>%s<reset>",
                            RedAlert.PAYPAL_PAGE, RedAlert.PAYPAL_PAGE))
            ));

            allowedCommands.forEach(cmd -> sender.sendMessage(
                    TextUtil.deserialize("<color:#FF0000>/redalert <color:#A00000><cmdname><color:#FF0000> - <color:#A00000><cmdhelp>",
                            Placeholder.parsed("cmdname", cmd.getName()),
                            Placeholder.parsed("cmdhelp", cmd.getHelp()))
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
                TextUtil.deserialize("<color:#FF0000>/redalert <color:#A00000><cmdname><color:#FF0000> - <color:#A00000><cmdhelp>",
                        Placeholder.parsed("cmdname", cmd.getName()),
                        Placeholder.parsed("cmdhelp", cmd.getHelp()))
        );
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return getAllowedCommands(sender).stream().map(IRedAlertCommand::getName).toList();
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
