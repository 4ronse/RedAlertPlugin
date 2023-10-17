package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SoundListener extends BaseOrefAlertListener {
    public SoundListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(Sound.sound(Key.key("block.note_block.iron_xylophone"),
                    Sound.Source.MASTER, 2f, .1f));
    }
}
