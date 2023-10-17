package dev.ronse.redalert.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.ronse.redalert.RedAlert;

import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Translator {
    private static JsonArray ALL_DISTRICTS = null;

    private static void load() {
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(RedAlert.class.getResourceAsStream("/formatted.min.json")));
        JsonElement jsonElement = JsonParser.parseReader(inputStreamReader);

        ALL_DISTRICTS = jsonElement.getAsJsonArray();
    }

    public static String getDistrictInEnglish(String heb) {
        if(ALL_DISTRICTS == null) load();

        for(JsonElement dist : ALL_DISTRICTS) {
            JsonObject obj = dist.getAsJsonObject();
            if(obj.get("label_he").getAsString().equals(heb)) return obj.get("label").getAsString();
        }

        return null;
    }

    public static Set<String> translateAllToEnglish(Iterable<String> itr) {
        Set<String> set = new HashSet<>();
        for(String dist : itr) set.add(getDistrictInEnglish(dist));

        return set;
    }
}