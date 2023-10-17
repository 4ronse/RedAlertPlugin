package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
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
        final TextComponent pref = Component.text("Tseva Adom in ").color(TextColor.fromHexString("#AA0000"));
        final TextComponent areas = Component.text(String.join(", ", Translator.translateAllToEnglish(alert.getAffectedAreas())))
                .color(TextColor.fromHexString("#FF0000"))
                .decoration(TextDecoration.BOLD, TextDecoration.State.TRUE);

        plugin.getServer().getConsoleSender().sendMessage(pref.append(areas));
    }
}
