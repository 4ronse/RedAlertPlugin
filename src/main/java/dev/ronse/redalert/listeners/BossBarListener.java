package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.util.Format;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BossBarListener extends BaseOrefAlertListener {
    public BossBarListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void onOrefAlert(OrefAlert alert) {
        String title = Format.format(RedAlert.config.notifiers.bossbarNotifier.titleFormat, alert);
        String content = Format.format(RedAlert.config.notifiers.bossbarNotifier.contentFormat, alert);

        String[] magic = content.split(",");
        StringBuilder contentBuilder = new StringBuilder();

        for (int i = 0; i < magic.length; i++) {
            if(i > 0 && (float) i / RedAlert.config.notifiers.bossbarNotifier.newLineEvery == 0.0f)
                contentBuilder.append('\n');
            contentBuilder.append(magic[i]).append(", ");
        }

        contentBuilder.setLength(contentBuilder.length() - 2);
        content = contentBuilder.toString();

        BossBar bar = BossBar.bossBar(
                Format.colorize_legacy(title).appendNewline().append(Format.colorize_legacy(content)),
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
