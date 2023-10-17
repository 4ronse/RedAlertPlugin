package dev.ronse.redalert.config.models.notifiers;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ConsoleNotifier implements Notifier {
    public String format;

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of("format", format);
    }

    public static ConsoleNotifier deserialize(Map<String, Object> map) {
        ConsoleNotifier notifier = new ConsoleNotifier();
        notifier.format = (String) map.getOrDefault("format", "");
        return notifier;
    }

    @Override
    public String toString() {
        return "ConsoleNotifier{" +
                "format='" + format + '\'' +
                '}';
    }
}
