package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SoundAlertListener extends BaseOrefAlertListener {
    public SoundAlertListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(
                    Sound.sound(
                            RedAlert.config.notifiers.soundNotifier.soundKey,
                            RedAlert.config.notifiers.soundNotifier.source,
                            RedAlert.config.notifiers.soundNotifier.volume,
                            RedAlert.config.notifiers.soundNotifier.pitch
                    )
            );
    }
}
