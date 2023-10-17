package dev.ronse.redalert.config.models.notifiers;

import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BossbarNotifier implements Notifier {
    public BossBar.Color color;
    public int duration;
    public String format;

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "color", color.name(),
                "duration", duration,
                "format", format
        );
    }

    public static BossbarNotifier deserialize(Map<String, Object> map) {
        BossbarNotifier notifier = new BossbarNotifier();

        notifier.color = BossBar.Color.valueOf((String) map.getOrDefault("color", "RED"));
        notifier.duration = (int) map.getOrDefault("duration", 10);
        notifier.format = (String) map.getOrDefault("format", "");

        return notifier;
    }

    @Override
    public String toString() {
        return "BossbarNotifier{" +
                "color=" + color +
                ", duration=" + duration +
                ", format='" + format + '\'' +
                '}';
    }
}
