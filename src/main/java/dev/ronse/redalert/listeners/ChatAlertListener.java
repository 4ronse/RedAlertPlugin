package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.util.TextUtil;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.SerializableAs;

public class ChatAlertListener extends BaseOrefAlertListener {
    private String format;

    public ChatAlertListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void loadFromMemorySection(MemorySection section) {
        format = section.getString("format");
    }

    @Override
    public void onAlert(OrefAlert alert) {
        plugin.getServer().sendMessage(TextUtil.deserialize(format, alert));
    }
}
