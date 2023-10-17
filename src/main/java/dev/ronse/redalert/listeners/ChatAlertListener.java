package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.util.Format;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ChatAlertListener extends BaseOrefAlertListener {
    public ChatAlertListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        plugin.getServer().sendMessage(Format.colorize_legacy(
                Format.format(RedAlert.config.notifiers.chatNotifier.format, alert)
        ));
    }
}
