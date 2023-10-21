package dev.ronse.redalert;

import dev.ronse.redalert.commands.*;
import dev.ronse.redalert.config.ConfigNew;
import dev.ronse.redalert.listeners.*;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertNotifier;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RedAlert extends JavaPlugin {
    public static ConfigNew config;
    private static RedAlert instance;

    public static RedAlert getInstance() {
        return instance;
    }

    public OrefAlertNotifier notifier;

    @Override
    public void onEnable() {
        instance = this;

        reloadConfig();
        registerCommands();
    }

    @Override
    public void reloadConfig() {
        shutdownNotifier();
        config = new ConfigNew(this);

        OrefAlertNotifier.Builder builder = new OrefAlertNotifier.Builder()
                .every(config.interval)
                .onException(ex -> getSLF4JLogger().error("Failed to check for alerts", ex))
                .onTimeout(ex -> getLogger().warning("Alert source timed out."))
                .listener(new RedAlertListener() {
                    @Override
                    public void onOrefAlert(OrefAlert alert) {
                        config.notifiers.getOrDefault(alert.alertType(), config.notifiers.get(OrefAlertType.DEFAULT))
                                .forEach(l -> l.onOrefAlert(alert));
                    }

                    @Override
                    public void onDisable() {
                        config.notifiers.values().forEach(listOfListeners -> listOfListeners.forEach(listener -> {
                            if (listener instanceof IDisableAction) {
                                ((IDisableAction) listener).onDisable();
                            }
                        }));
                    }
                });

        // config.notifiers.values().forEach(list -> list.forEach(builder::listener));
        notifier = builder.build();
        notifier.listen();
    }

    private void shutdownNotifier() {
        if (notifier != null) {
            notifier.stop();
            notifier.getListeners().forEach(listener -> {
                if (listener instanceof IDisableAction) {
                    ((IDisableAction) listener).onDisable();
                }
            });
        }
        notifier = null;
    }

    @Override
    public void onDisable() {
        shutdownNotifier();
        instance = null;
        config = null;
    }

    void registerCommand(String name, CommandExecutor executor) {
        PluginCommand cmd = getCommand(name);
        if (cmd == null) {
            getLogger().severe("Failed to initiate /" + name);
            return;
        }
        cmd.setExecutor(executor);
    }

    void registerCommands() {
        RedAlertCommand redAlertCommand = new RedAlertCommand();

        redAlertCommand.registerCommand(new RedAlertHelpCommand(redAlertCommand));
        redAlertCommand.registerCommand(new RedAlertReloadCommand());
        redAlertCommand.registerCommand(new RedAlertTestCommand());

        registerCommand("redalert", redAlertCommand);
    }
}
