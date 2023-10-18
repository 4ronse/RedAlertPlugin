package dev.ronse.redalert.config.models.notifiers;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SoundNotifier implements Notifier {
    public Key soundKey;
    public Sound.Source source;
    public float volume;
    public float pitch;

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "sound_key", soundKey.asString(),
                "source", source.name(),
                "volume", volume,
                "pitch", pitch
        );
    }

    public static SoundNotifier deserialize(Map<String, Object> map) {
        SoundNotifier notifier = new SoundNotifier();

        notifier.soundKey = Key.key((String) map.get("sound_key"));
        notifier.source = Sound.Source.valueOf((String) map.get("source"));
        notifier.volume = ((Double) map.get("volume")).floatValue();
        notifier.pitch = ((Double) map.get("pitch")).floatValue();

        return notifier;
    }

    @Override
    public String toString() {
        return "SoundNotifier{" +
                "soundKey=" + soundKey +
                ", source=" + source +
                ", volume=" + volume +
                ", pitch=" + pitch +
                '}';
    }
}
