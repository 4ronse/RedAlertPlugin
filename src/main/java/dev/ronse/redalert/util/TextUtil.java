package dev.ronse.redalert.util;

import dev.ronse.redalert.orefalerts.OrefAlert;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class TextUtil {

    public static Component deserialize(String msg, TagResolver... tags) {
        return MiniMessage.miniMessage().deserialize(msg, tags);
    }

    public static Component deserialize(String msg, OrefAlert alert) {
        var ae = alert.getAffectedAreas();

        return MiniMessage.miniMessage().deserialize(msg,
                Placeholder.parsed("cities_he", String.join(", ", ae)),
                Placeholder.parsed("cities_en", String.join(", ", OrefDistrictsUtil.translateAllToEnglish(ae))),
                Placeholder.parsed("desc", alert.description())
        );
    }

    @SuppressWarnings("unused")
    public static TextComponent colorize_legacy(String msg) {
        return LegacyComponentSerializer.legacy('&').deserialize(msg);
    }

    public static String format(String msg, OrefAlert alert) {
        var ae = alert.getAffectedAreas();
        return msg
                .replaceAll("%cities_he%", String.join(", ", ae))
                .replaceAll("%cities_en%", String.join(", ", OrefDistrictsUtil.translateAllToEnglish(ae)))
                .replaceAll("%desc%", alert.description());
    }

}
