package dev.ronse.redalert.config.models;

import dev.ronse.redalert.config.models.notifiers.BossbarNotifier;
import dev.ronse.redalert.config.models.notifiers.ChatNotifier;
import dev.ronse.redalert.config.models.notifiers.ConsoleNotifier;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Notifiers implements ConfigurationSerializable {
    public BossbarNotifier bossbarNotifier;
    public ChatNotifier chatNotifier;
    public ConsoleNotifier consoleNotifier;

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "bossbar", bossbarNotifier,
                "chat", chatNotifier,
                "console", consoleNotifier
        );
    }

    public static Notifiers deserialize(Map<String, Object> map) {
        Notifiers notifiers = new Notifiers();

        notifiers.bossbarNotifier = (BossbarNotifier) map.getOrDefault("bossbar", null);
        notifiers.chatNotifier = (ChatNotifier) map.getOrDefault("chat", null);
        notifiers.consoleNotifier = (ConsoleNotifier) map.getOrDefault("console", null);

        return notifiers;
    }

    @Override
    public String toString() {
        return "Notifiers{" +
                "bossbarNotifier=" + bossbarNotifier +
                ", chatNotifier=" + chatNotifier +
                ", consoleNotifier=" + consoleNotifier +
                '}';
    }
}