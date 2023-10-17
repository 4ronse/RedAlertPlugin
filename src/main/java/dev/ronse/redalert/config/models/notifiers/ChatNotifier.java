package dev.ronse.redalert.config.models.notifiers;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ChatNotifier implements Notifier {
    public String format;

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of("format", format);
    }

    public static ChatNotifier deserialize(Map<String, Object> map) {
        ChatNotifier notifier = new ChatNotifier();
        notifier.format = (String) map.getOrDefault("format", "");
        return notifier;
    }

    @Override
    public String toString() {
        return "ChatNotifier{" +
                "format='" + format + '\'' +
                '}';
    }
}

