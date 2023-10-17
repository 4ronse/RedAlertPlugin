package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.util.Format;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarListener extends BaseOrefAlertListener {
    public BossBarListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        String format = Format.format(RedAlert.config.notifiers.bossbarNotifier.format, alert);

        BossBar bar = BossBar.bossBar(
                Format.colorize_legacy(format),
                BossBar.MAX_PROGRESS,
                RedAlert.config.notifiers.bossbarNotifier.color,
                BossBar.Overlay.PROGRESS);

        plugin.getServer().getOnlinePlayers().forEach(p -> {
            p.showBossBar(bar);

            BukkitRunnable disableBar = new BukkitRunnable() {
                @Override
                public void run() {
                    p.hideBossBar(bar);
                }
            };

            disableBar.runTaskLater(plugin, 20L * RedAlert.config.notifiers.bossbarNotifier.duration);

        });
    }
}
