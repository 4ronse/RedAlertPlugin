package dev.ronse.redalert;

import dev.ronse.redalert.commands.RedAlertDump;
import dev.ronse.redalert.commands.RedAlertTest;
import dev.ronse.redalert.config.Config;
import dev.ronse.redalert.listeners.BossBarListener;
import dev.ronse.redalert.listeners.ChatAlertListener;
import dev.ronse.redalert.listeners.ConsoleAlertListener;
import dev.ronse.redalert.orefalerts.OrefAlertNotifier;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RedAlert extends JavaPlugin {
    public static Config config;
    private static RedAlert instance = null;
    public static RedAlert getInstance() { return instance; }

    public OrefAlertNotifier notifier;

    @Override
    public void onEnable() {
        instance = this;
        config = new Config(this);

        notifier = new OrefAlertNotifier.Builder()
                .every(config.delay)
                .listener(new ConsoleAlertListener(this))
                .listener(new ChatAlertListener(this))
                .listener(new BossBarListener(this))
                .onException(ex -> getSLF4JLogger().error("Failed to check for alerts", ex))
                .build();
        notifier.listen();

        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        notifier.stop();

        instance = null;
        notifier = null;
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
        _registerCommand("redalertdump", new RedAlertDump());
        _registerCommand("redalerttest", new RedAlertTest());
    }
}
