package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.config.models.AlertTypeMap;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertListener;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import org.bukkit.configuration.MemorySection;

public abstract class BaseOrefAlertListener implements OrefAlertListener {
    protected final RedAlert plugin;
    private boolean disabled = false;
    private OrefAlertType listensFor = OrefAlertType.DEFAULT;

    public BaseOrefAlertListener(RedAlert plugin) {
        this.plugin = plugin;
    }

    public abstract void onAlert(OrefAlert alert);

    public final boolean isDisabled() {
        return disabled;
    }

    @Override
    public final void onOrefAlert(OrefAlert alert) {
        if(isDisabled() || !this.listensFor.equals(OrefAlertType.DEFAULT)) return;
        onAlert(alert);
    }

    public final void _loadFromMemorySection(MemorySection section) {
        disabled = section.getBoolean("disabled", false);
        loadFromMemorySection(section);
    }

    public final void listenFor(OrefAlertType type) {
        this.listensFor = type;
    }

    public abstract void loadFromMemorySection(MemorySection section);
}
