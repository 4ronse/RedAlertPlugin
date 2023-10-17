package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlertListener;

public abstract class BaseOrefAlertListener implements OrefAlertListener {
    protected final RedAlert plugin;

    public BaseOrefAlertListener(RedAlert plugin) { this.plugin = plugin; }
}
