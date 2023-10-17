package dev.ronse.redalert.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.ronse.redalert.RedAlert;

import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Translator {
    private static JsonArray ALL_DISTRICTS = null;

    private static void load() {
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(RedAlert.class.getResourceAsStream("/formatted.min.json")));
        JsonElement jsonElement = JsonParser.parseReader(inputStreamReader);

        ALL_DISTRICTS = jsonElement.getAsJsonArray();
    }

    static { load(); }

    public static String getDistrictInEnglish(String heb) {
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

    public static Set<String> getRandomStates(int n) {
        Set<String> set = new HashSet<>();
        Random random = new Random(System.currentTimeMillis());

        while(set.size() < n)
            set.add(
                    ALL_DISTRICTS.get(
                            random.nextInt(ALL_DISTRICTS.size())
                    ).getAsJsonObject().get("label_he").getAsString()
            );

        return set;
    }
}