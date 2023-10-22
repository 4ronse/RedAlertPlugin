package dev.ronse.redalert.config;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.config.annotations.ConfigEntry;
import dev.ronse.redalert.config.annotations.Key;
import dev.ronse.redalert.config.models.AlertTypeMap;

public class Config extends ConfigBase {

    @ConfigEntry
    public boolean debug;

    @ConfigEntry
    @Key(name = "debug_source")
    public String debugSource;

    @ConfigEntry
    public int interval;

    @ConfigEntry(required = false)
    public AlertTypeMap notifiers;

    public Config(RedAlert plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public String toString() {
        return "Config{" +
                "debug=" + debug +
                ", debugSource='" + debugSource + '\'' +
                ", interval=" + interval +
                ", notifiers=" + notifiers +
                '}';
    }
}
