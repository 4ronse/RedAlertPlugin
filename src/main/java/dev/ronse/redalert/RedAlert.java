package dev.ronse.redalert;

import dev.ronse.redalert.commands.*;
import dev.ronse.redalert.config.Config;
import dev.ronse.redalert.listeners.IDisableAction;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertListener;
import dev.ronse.redalert.orefalerts.OrefAlertNotifier;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public final class RedAlert extends JavaPlugin {
    public static final String GITHUB_PAGE = "https://github.com/4ronse/RedAlertPlugin/releases";
    public static final String PAYPAL_PAGE = "https://www.paypal.com/donate/?hosted_button_id=GDXSJCXNYFH7S";

    private static RedAlert instance;
    public static Config config;
    public OrefAlertNotifier notifier;

    public static RedAlert getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        reloadConfig();
        registerCommands();
        checkUpdate();
    }

    @Override
    public void reloadConfig() {
        shutdownNotifier();
        config = new Config(this);

        OrefAlertNotifier.Builder builder = new OrefAlertNotifier.Builder()
                .every(config.interval)
                .onException(ex -> getSLF4JLogger().error("Failed to check for alerts", ex))
                .onTimeout(ex -> getLogger().warning("Alert source timed out."))
                .listener(new RedAlertListener() {
                    @Override
                    public void onOrefAlert(OrefAlert alert) {
                        Map<OrefAlertType, List<OrefAlertListener>> notifiers = config.notifiers;
                        notifiers.getOrDefault(alert.alertType(), notifiers.get(OrefAlertType.DEFAULT))
                                .forEach(l -> l.onOrefAlert(alert));
                    }

                    @Override
                    public void onDisable() {
                        config.notifiers.values().stream()
                                .flatMap(List::stream)
                                .filter(listener -> listener instanceof IDisableAction)
                                .map(listener -> (IDisableAction) listener)
                                .forEach(IDisableAction::onDisable);
                    }
                });

        notifier = builder.build();
        notifier.listen();
    }

    private void shutdownNotifier() {
        if (notifier != null) {
            notifier.stop();
            notifier.getListeners().stream()
                    .filter(listener -> listener instanceof IDisableAction)
                    .map(listener -> (IDisableAction) listener)
                    .forEach(IDisableAction::onDisable);
        }
        notifier = null;
    }

    @Override
    public void onDisable() {
        shutdownNotifier();
        instance = null;
        config = null;
    }

    @SuppressWarnings("all")
    void registerCommand(String name, CommandExecutor executor) {
        PluginCommand cmd = getCommand(name);
        if (cmd != null) {
            cmd.setExecutor(executor);
            if (executor instanceof TabCompleter) {
                cmd.setTabCompleter((TabCompleter) executor);
            }
        } else {
            getLogger().severe("Failed to initiate /" + name);
        }
    }

    void registerCommands() {
        RedAlertCommand redAlertCommand = new RedAlertCommand();
        redAlertCommand.registerCommand(new RedAlertHelpCommand(redAlertCommand));
        redAlertCommand.registerCommand(new RedAlertReloadCommand());
        redAlertCommand.registerCommand(new RedAlertTestCommand());
        redAlertCommand.registerCommand(new RedAlertListNotifiers());
        registerCommand("redalert", redAlertCommand);
    }

    @SuppressWarnings("all")
    void checkUpdate() {
        UpdateChecker.getVersion(v -> {
            if (!v.isEmpty() && !getDescription().getVersion().equals(v)) {
                getSLF4JLogger().warn("A new version is available on https://github.com/4ronse/RedAlertPlugin/releases");
            }
        }, e -> getSLF4JLogger().error("Failed to check for updates", e));
    }
}
