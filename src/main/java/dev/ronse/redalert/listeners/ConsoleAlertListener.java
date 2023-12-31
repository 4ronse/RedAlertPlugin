package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.util.TextUtil;
import org.bukkit.configuration.MemorySection;

public class ConsoleAlertListener extends BaseOrefAlertListener {
    private String format;

    public ConsoleAlertListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void loadFromMemorySection(MemorySection section) {
        format = section.getString("format");
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        plugin.getServer().getConsoleSender().sendMessage(TextUtil.deserialize(format, alert));
    }
}
