package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertListener;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import org.bukkit.configuration.MemorySection;

public abstract class BaseOrefAlertListener implements OrefAlertListener {
    protected final RedAlert plugin;
    private boolean disabled = false;

    public BaseOrefAlertListener(RedAlert plugin) {
        this.plugin = plugin;
    }

    public final boolean isDisabled() {
        return disabled;
    }

    public abstract void onOrefAlert(OrefAlert alert);

    public final void _loadFromMemorySection(MemorySection section) {
        disabled = section.getBoolean("disabled", false);
        loadFromMemorySection(section);
    }

    public abstract void loadFromMemorySection(MemorySection section);
}
