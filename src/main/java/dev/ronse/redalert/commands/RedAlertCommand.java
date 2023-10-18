package dev.ronse.redalert.commands;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.commands.validator.Validator;
import dev.ronse.redalert.exceptions.ArgumentCountException;
import dev.ronse.redalert.exceptions.ArgumentException;
import dev.ronse.redalert.exceptions.NotEnoughArguments;
import dev.ronse.redalert.exceptions.TooManyArguments;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RedAlertCommand implements CommandExecutor, TabCompleter {
    private final List<IRedAlertCommand> commands = new ArrayList<>();

    public void registerCommand(IRedAlertCommand command) { commands.add(command); }

    private IRedAlertCommand getCommand(String cmdName) {
        for(var cmd : commands) if(cmd.getName().equalsIgnoreCase(cmdName)) return cmd;
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command _ignore, @NotNull String label, @NotNull String[] aArgs) {
        List<String> args = new ArrayList<>(Arrays.asList(aArgs));

        String commandName = args.isEmpty() ? "help" : args.remove(0);
        IRedAlertCommand command = getCommand(commandName);

        if (command == null) {
            sender.sendMessage("Piss off");
            return true;
        }

        // Validate command
        String perm = command.permission();
        boolean consoleAllowed = command.consoleAllowed();

        if(sender instanceof ConsoleCommandSender && !consoleAllowed) {
            sender.sendMessage("Console not allowed (racism)");
            return true;
        }

        if(perm != null && !sender.hasPermission(perm)) {
            sender.sendMessage("You lil nigga, try becoming a big nigga");
            return true;
        }

        try {
            List<String> invalidArgs = validateArgs(command, args);

            if(invalidArgs.isEmpty()) {
                command.onCommand(sender, args);
                return true;
            }

            invalidArgs.forEach(arg -> sender.sendMessage("Value for argument " + arg + " is invalid."));

        } catch (ArgumentException e) {
            sender.sendMessage(e.getMessage());
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command _ignore, @NotNull String label, @NotNull String[] aArgs) {
        List<String> args = new ArrayList<>(Arrays.asList(aArgs));
        String commandName = args.remove(0);

        IRedAlertCommand command = getCommand(commandName);

        if(commandName.isEmpty() || commandName.isBlank()) return commands.stream().map(IRedAlertCommand::getName).toList();
        if(command != null) return command.onTabComplete(sender, args);
        return List.of();
    }

    protected List<IRedAlertCommand> getCommands() {
        return List.copyOf(commands);
    }


    private List<String> validateArgs(IRedAlertCommand cmd, List<String> args) throws ArgumentException {
        // Validate number of args
        int numArgs = cmd.numArgs();
        int minArgs = cmd.minArgs();
        int maxArgs = cmd.maxArgs();

        if(numArgs != -1 && args.size() != numArgs) throw new ArgumentCountException(numArgs, args.size());
        if(minArgs != -1 && args.size() < minArgs) throw new NotEnoughArguments(minArgs, args.size());
        if(maxArgs != -1 && args.size() > maxArgs) throw new TooManyArguments(maxArgs, args.size());


        // Check validators
        List<String> invalidArgs = new ArrayList<>();

        Class<? extends IRedAlertCommand> clazz = cmd.getClass();
        Method[] publicMethods = clazz.getMethods();
        Method[] privateMethods = clazz.getDeclaredMethods();

        Set<Method> methods = new HashSet<>();
        methods.addAll(Arrays.asList(publicMethods));
        methods.addAll(Arrays.asList(privateMethods));

        for(var method : methods) {
            final Validator v = method.getAnnotation(Validator.class);
            if(v == null) continue;

            Class<?>[] paramTypes = method.getParameterTypes();
            if(paramTypes.length != 1 && paramTypes[0] != String.class) continue;  // Throw exception

            method.setAccessible(true);

            try {
                try {
                    boolean valid = (boolean) method.invoke(cmd, args.get(v.position()));
                    if (!valid) invalidArgs.add(0, v.name());
                } catch (IndexOutOfBoundsException ignored) {
                    if(v.required()) invalidArgs.add(0, v.name());
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
                if(!v.required()) continue;
                invalidArgs.add(0, v.name());
            } catch (IllegalAccessException | InvocationTargetException e) {
                RedAlert.getInstance().getSLF4JLogger().error("Failed to validate " + v.name(), e);
            }
        }

        return invalidArgs;
    }
}
