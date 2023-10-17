package dev.ronse.redalert.config;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.config.models.Notifiers;
import dev.ronse.redalert.config.models.notifiers.BossbarNotifier;
import dev.ronse.redalert.config.models.notifiers.ChatNotifier;
import dev.ronse.redalert.config.models.notifiers.ConsoleNotifier;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.File;

public class Config {
    static {
        ConfigurationSerialization.registerClass(Notifiers.class);
        ConfigurationSerialization.registerClass(BossbarNotifier.class);
        ConfigurationSerialization.registerClass(ConsoleNotifier.class);
        ConfigurationSerialization.registerClass(ChatNotifier.class);
    }

    // private final File CONFIG_LOCATION;
    // private final YamlConfiguration CONFIG;

    private final RedAlert PLUGIN;
    private final FileConfiguration CONFIG;

    public boolean debug;
    public int delay;
    public String debugSource;

    public Notifiers notifiers;

    public Config(RedAlert redAlert) {
        PLUGIN = redAlert;
        CONFIG = PLUGIN.getConfig();
        setDefaults();
        CONFIG.options().copyDefaults(true);
        PLUGIN.saveConfig();

        reload();
    }

    private void setDefaults() {
        BossbarNotifier bossbarNotifier = new BossbarNotifier();
        ChatNotifier chatNotifier = new ChatNotifier();
        ConsoleNotifier consoleNotifier = new ConsoleNotifier();

        bossbarNotifier.duration = 10;
        bossbarNotifier.newLineEvery = 8;
        bossbarNotifier.color = BossBar.Color.RED;
        bossbarNotifier.titleFormat = "&#AA0000צבע אדום";
        bossbarNotifier.contentFormat = "&#F00000%cities_he%";

        chatNotifier.format = "&#AA0000צבע אדום ב&#FF0000&l%cities_he%";

        consoleNotifier.format = "&#AA0000Attacks on &#FF0000&l%cities_en%";

        Notifiers notifiers = new Notifiers();
        notifiers.bossbarNotifier = bossbarNotifier;
        notifiers.chatNotifier = chatNotifier;
        notifiers.consoleNotifier = consoleNotifier;


        CONFIG.addDefault("debug", false);
        CONFIG.addDefault("debug_url", "http://localhost:8000/alerts.json");
        CONFIG.addDefault("delay", 1);
        CONFIG.addDefault("notifiers", notifiers);
    }

    private void reload() {
        PLUGIN.reloadConfig();

        this.debug = CONFIG.getBoolean("debug");
        this.debugSource = CONFIG.getString("debug_url");
        this.delay = CONFIG.getInt("delay");
        this.notifiers = CONFIG.getObject("notifiers", Notifiers.class);
    }

    @Override
    public String toString() {
        return "Config{" +
                "debug=" + debug +
                ", delay=" + delay +
                ", debugSource='" + debugSource + '\'' +
                ", notifiers=" + notifiers +
                '}';
    }
}
