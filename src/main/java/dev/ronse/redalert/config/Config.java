package dev.ronse.redalert.config;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.config.models.Notifiers;
import dev.ronse.redalert.config.models.notifiers.BossbarNotifier;
import dev.ronse.redalert.config.models.notifiers.ChatNotifier;
import dev.ronse.redalert.config.models.notifiers.ConsoleNotifier;
import dev.ronse.redalert.config.models.notifiers.SoundNotifier;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class Config {
    static {
        ConfigurationSerialization.registerClass(Notifiers.class);
        ConfigurationSerialization.registerClass(BossbarNotifier.class);
        ConfigurationSerialization.registerClass(ConsoleNotifier.class);
        ConfigurationSerialization.registerClass(ChatNotifier.class);
        ConfigurationSerialization.registerClass(SoundNotifier.class);
    }

    private final RedAlert PLUGIN;
    private FileConfiguration CONFIG;

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
        SoundNotifier soundNotifier = new SoundNotifier();

        bossbarNotifier.duration = 15;
        bossbarNotifier.color = BossBar.Color.RED;
        bossbarNotifier.format = "&#AA0000%cities_he%";

        chatNotifier.format = "&#AA0000צבע אדום ב&#FF0000&l%cities_he%";

        consoleNotifier.format = "&#AA0000Attacks on &#FF0000&l%cities_en%";

        soundNotifier.soundKey = Key.key("block.note_block.iron_xylophone");
        soundNotifier.source = Sound.Source.MASTER;
        soundNotifier.volume = 2f;
        soundNotifier.pitch = 0.1f;

        Notifiers notifiers = new Notifiers();
        notifiers.bossbarNotifier = bossbarNotifier;
        notifiers.chatNotifier = chatNotifier;
        notifiers.consoleNotifier = consoleNotifier;
        notifiers.soundNotifier = soundNotifier;


        CONFIG.addDefault("debug", false);
        CONFIG.addDefault("debug_url", "http://localhost:8000/alerts.json");
        CONFIG.addDefault("delay", 1);
        CONFIG.addDefault("notifiers", notifiers);
    }

    public void reload() {
        PLUGIN.reloadConfig();
        CONFIG = PLUGIN.getConfig();

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
