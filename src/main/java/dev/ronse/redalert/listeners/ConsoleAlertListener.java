package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.util.Format;
import dev.ronse.redalert.util.Translator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ConsoleAlertListener extends BaseOrefAlertListener {
    public ConsoleAlertListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        plugin.getServer().getConsoleSender().sendMessage(Format.colorize_legacy(
                Format.format(RedAlert.config.notifiers.consoleNotifier.format, alert)
        ));}
}
