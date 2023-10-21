package dev.ronse.redalert.config;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.config.annotations.ConfigEntry;
import dev.ronse.redalert.config.annotations.Key;
import dev.ronse.redalert.config.models.AlertTypeMap;
import dev.ronse.redalert.config.models.notifiers.Notifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ConfigNew extends ConfigBase {
    public static class TestObject implements ConfigurationSerializable {
        public String test;

        @Override
        public @NotNull Map<String, Object> serialize() {
            return Map.of("test", test);
        }

        public static TestObject deserialize(Map<String, Object> map) {
            TestObject obj = new TestObject();

            obj.test = (String) map.get("test");

            return obj;
        }

        @Override
        public String toString() {
            return "TestObject{" +
                    "test='" + test + '\'' +
                    '}';
        }
    }

    @ConfigEntry
    public boolean debug;

    @ConfigEntry
    @Key(name = "debug_source")
    public String debugSource;

    @ConfigEntry
    public int interval;

    @ConfigEntry
    public TestObject obj;

    @ConfigEntry(required = false)
    public String test;

    @ConfigEntry
    public List<String> list;

    @ConfigEntry(required = false)
    public AlertTypeMap notifiers;

    public ConfigNew(RedAlert plugin) {
        super(plugin, "confignew.yml");
    }

    @Override
    public String toString() {
        return "ConfigNew{" +
                "debug=" + debug +
                ", debugSource='" + debugSource + '\'' +
                ", interval=" + interval +
                ", obj=" + obj +
                ", test='" + test + '\'' +
                ", list=" + list +
                ", notifiers=" + notifiers +
                '}';
    }
}
