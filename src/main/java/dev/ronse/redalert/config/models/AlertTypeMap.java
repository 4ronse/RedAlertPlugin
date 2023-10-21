package dev.ronse.redalert.config.models;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.listeners.*;
import dev.ronse.redalert.orefalerts.OrefAlertListener;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class AlertTypeMap implements Map<OrefAlertType, List<OrefAlertListener>>, ConfigurationSerializable {
    private enum ListenerType {
        chat, console, bossbar, sound
    }

    private final Map<OrefAlertType, List<OrefAlertListener>> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public List<OrefAlertListener> get(Object key) {
        return map.get(key);
    }

    @Nullable
    @Override
    public List<OrefAlertListener> put(OrefAlertType key, List<OrefAlertListener> value) {
        return map.put(key, value);
    }

    @Override
    public List<OrefAlertListener> remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends OrefAlertType, ? extends List<OrefAlertListener>> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @NotNull
    @Override
    public Set<OrefAlertType> keySet() {
        return map.keySet();
    }

    @NotNull
    @Override
    public Collection<List<OrefAlertListener>> values() {
        return map.values();
    }

    @NotNull
    @Override
    public Set<Entry<OrefAlertType, List<OrefAlertListener>>> entrySet() {
        return map.entrySet();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> out = new HashMap<>();
        map.forEach((alert, list) -> out.put(alert.name(), list));
        return out;
    }

    public static AlertTypeMap deserialize(Map<String, Object> mmap) throws Exception {
        Map<String, MemorySection> filteredMap = new HashMap<>();

        for (var entry : mmap.entrySet()) {
            if (entry.getValue() instanceof MemorySection) {
                String key = entry.getKey();
                // Count the number of separators (".") in the key
                long separatorCount = key.chars().filter(ch -> ch == '.').count();
                if (separatorCount == 1) {
                    // If the key contains more than two separators, add it to the filtered map
                    filteredMap.put(key, (MemorySection) entry.getValue());
                }
            }
        }

        AlertTypeMap map = new AlertTypeMap();
        AtomicReference<Exception> atomicReference = new AtomicReference<>(null);

        filteredMap.forEach((path, ms) -> {
            String[] split = path.split("\\.");
            String alertTypeName = split[0];
            String listenerTypeName = split[1];

            BaseOrefAlertListener listener = null;
            OrefAlertType alertType = OrefAlertType.valueOf(alertTypeName);
            ListenerType listenerType = ListenerType.valueOf(listenerTypeName);

            switch (listenerType) {
                case chat -> listener = new ChatAlertListener(RedAlert.getInstance());
                case console -> listener = new ConsoleAlertListener(RedAlert.getInstance());
                case bossbar -> listener = new BossBarAlertListener(RedAlert.getInstance());
                case sound -> listener = new SoundAlertListener(RedAlert.getInstance());
                default -> atomicReference.set(new InvalidConfigurationException("Unknown type " + listenerTypeName));
            }

            if(atomicReference.get() != null) throw new RuntimeException(atomicReference.get());

            listener._loadFromMemorySection(ms);

            map.putIfAbsent(alertType, new ArrayList<>());
            map.get(alertType).add(listener);
        });

        // printMap(map, 0);

        return map;
    }

    public static void printMap(Map<?, ?> map, int indent) {
        var logger = RedAlert.getInstance().getSLF4JLogger();

        for(var entry : map.entrySet()) {
            if(entry.getValue() instanceof Map) {
                printMap((Map<?, ?>) entry.getValue(), indent + 2);
                continue;
            }
            logger.info(" ".repeat(indent) + entry.getKey().toString() + ": " + entry.getValue().toString());
        }
    }
}
