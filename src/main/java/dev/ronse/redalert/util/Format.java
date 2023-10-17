package dev.ronse.redalert.util;

import dev.ronse.redalert.orefalerts.OrefAlert;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class Format {

    public static TextComponent colorize_legacy(String msg) {
        return LegacyComponentSerializer.legacy('&').deserialize(msg);
    }

    public static String format(String msg, OrefAlert alert) {
        var ae = alert.getAffectedAreas();
        return msg
                .replaceAll("%cities_he%", String.join(", ", ae))
                .replaceAll("%cities_en%", String.join(", ", Translator.translateAllToEnglish(ae)))
                .replaceAll("%desc%", alert.description());
    }

}
