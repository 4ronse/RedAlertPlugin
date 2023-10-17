package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.util.Format;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BossBarAlertListener extends BaseOrefAlertListener {
    public BossBarAlertListener(RedAlert plugin) {
        super(plugin);
    }

    private final Map<Long, BossBar> bossBars = new HashMap<>();

    @Override
    public void onOrefAlert(OrefAlert alert) {
        BossBar bar = BossBar.bossBar(
                Format.colorize_legacy(
                        Format.format(RedAlert.config.notifiers.bossbarNotifier.format, alert)
                ),
                BossBar.MAX_PROGRESS,
                RedAlert.config.notifiers.bossbarNotifier.color,
                BossBar.Overlay.NOTCHED_20
        );

        for(var p : Bukkit.getOnlinePlayers()) {
            if(bossBars.containsKey(alert.getId())) p.hideBossBar(bossBars.get(alert.getId()));
            bossBars.put(alert.getId(), bar);

            p.showBossBar(bar);

            // Hide bossbar
            new BukkitRunnable() {
                @Override
                public void run() { p.hideBossBar(bar); }
            }.runTaskLater(plugin, 20L * RedAlert.config.notifiers.bossbarNotifier.duration);
        }
    }
}
