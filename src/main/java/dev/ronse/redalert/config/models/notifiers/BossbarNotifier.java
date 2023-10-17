package dev.ronse.redalert.config.models.notifiers;

import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BossbarNotifier implements Notifier {
    public BossBar.Color color;
    public int duration;
    public int newLineEvery;
    public String contentFormat;
    public String titleFormat;

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "color", color.name(),
                "duration", duration,
                "new_line_every", newLineEvery,
                "title_format", titleFormat,
                "content_format", contentFormat
        );
    }

    public static BossbarNotifier deserialize(Map<String, Object> map) {
        BossbarNotifier notifier = new BossbarNotifier();

        notifier.color = BossBar.Color.valueOf((String) map.getOrDefault("color", "RED"));
        notifier.duration = (int) map.getOrDefault("duration", 10);
        notifier.newLineEvery = (int) map.getOrDefault("new_line_every", 8);
        notifier.titleFormat = (String) map.getOrDefault("title_format", "");
        notifier.contentFormat = (String) map.getOrDefault("content_format", "");

        return notifier;
    }

    @Override
    public String toString() {
        return "BossbarNotifier{" +
                "color=" + color +
                ", duration=" + duration +
                ", newLineEvery=" + newLineEvery +
                ", contentFormat='" + contentFormat + '\'' +
                ", titleFormat='" + titleFormat + '\'' +
                '}';
    }
}
