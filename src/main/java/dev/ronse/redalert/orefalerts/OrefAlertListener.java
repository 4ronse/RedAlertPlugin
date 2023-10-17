package dev.ronse.redalert.orefalerts;

@FunctionalInterface
public interface OrefAlertListener {
    void onOrefAlert(OrefAlert alert);
}
