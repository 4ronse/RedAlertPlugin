package dev.ronse.redalert;

import dev.ronse.redalert.commands.*;
import dev.ronse.redalert.config.Config;
import dev.ronse.redalert.config.ConfigNew;
import dev.ronse.redalert.listeners.*;
import dev.ronse.redalert.orefalerts.OrefAlertNotifier;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RedAlert extends JavaPlugin {
    public static ConfigNew config;
    private static RedAlert instance = null;
    public static RedAlert getInstance() { return instance; }

    public OrefAlertNotifier notifier;

    @Override
    public void onEnable() {
        instance = this;
        // config = new Config(this);

        // try {
        //     var cfg = new ConfigNew(this);
        //     getSLF4JLogger().info(cfg.toString());
        // } catch (Exception e) {
        //     getSLF4JLogger().error("Failed to load new config", e);
        // }

        reloadConfig();
        registerCommands();
    }

    @Override
    public void reloadConfig() {
        shutdownNotifier();
        config = new ConfigNew(this);

        var builder = new OrefAlertNotifier.Builder()
                .every(config.interval)
                .onException(ex -> getSLF4JLogger().error("Failed to check for alerts", ex))
                .onTimeout(ex -> getSLF4JLogger().warn("Alert source timed out."));

        config.notifiers.values().forEach(list -> list.forEach(builder::listener));
        notifier = builder.build();
        notifier.listen();
    }

    private void shutdownNotifier() {
        if(notifier != null) {
            notifier.stop();

            notifier.getListeners().forEach(listener -> {
                if(listener instanceof IDisableAction)
                    ((IDisableAction) listener).onDisable();
            });

            notifier.clear();
        }

        notifier = null;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        shutdownNotifier();

        instance = null;
        config = null;
    }

    void _registerCommand(String name, CommandExecutor executor) {
        PluginCommand cmd = getCommand(name);

        if(cmd == null) {
            getSLF4JLogger().error("Failed to initiate /" + name, new NullPointerException());
            return;
        }

        cmd.setExecutor(executor);
    }

    void registerCommands() {
        RedAlertCommand redAlertCommand = new RedAlertCommand();

        redAlertCommand.registerCommand(new RedAlertHelpCommand(redAlertCommand));
        redAlertCommand.registerCommand(new RedAlertReloadCommand());
        redAlertCommand.registerCommand(new RedAlertTestCommand());

        _registerCommand("redalert", redAlertCommand);
    }
}
