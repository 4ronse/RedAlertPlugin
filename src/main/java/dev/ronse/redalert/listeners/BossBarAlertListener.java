package dev.ronse.redalert.listeners;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.orefalerts.OrefAlert;
import dev.ronse.redalert.orefalerts.OrefAlertType;
import dev.ronse.redalert.util.TextUtil;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BossBarAlertListener extends BaseOrefAlertListener implements IDisableAction {
    private String format;
    private BossBar.Color color;
    private int duration;

    public BossBarAlertListener(RedAlert plugin) {
        super(plugin);
    }

    @Override
    public void loadFromMemorySection(MemorySection section) {
        format = section.getString("format");
        color = BossBar.Color.valueOf(section.getString("color"));
        duration = section.getInt("duration");
    }

    private final Map<Long, BossBar> bossBars = new HashMap<>();

    @Override
    public void onOrefAlert(OrefAlert alert) {
        BossBar bar = BossBar.bossBar(
                TextUtil.deserialize(format, alert),
                BossBar.MAX_PROGRESS,
                color,
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
            }.runTaskLater(plugin, 20L * duration);
        }
    }

    @Override
    public void onDisable() {
        var onlinePlayers = Bukkit.getOnlinePlayers();
        var bars = bossBars.values();

        for(var player : onlinePlayers)
            for(var bar : bars)
                player.hideBossBar(bar);
    }
}
