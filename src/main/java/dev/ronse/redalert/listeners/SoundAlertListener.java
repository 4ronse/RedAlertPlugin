package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

public class SoundAlertListener extends BaseOrefAlertListener {
    private Key soundKey;
    private Sound.Source source;
    private float volume;
    private float pitch;

    public SoundAlertListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void loadFromMemorySection(MemorySection section) {
        soundKey = Key.key(section.getString("sound_key"));
        source = Sound.Source.valueOf(section.getString("source"));
        volume = ((Double) section.getDouble("volume")).floatValue();
        pitch = ((Double) section.getDouble("pitch")).floatValue();
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(
                    Sound.sound(
                            soundKey,
                            source,
                            volume,
                            pitch
                    )
            );
    }
}
