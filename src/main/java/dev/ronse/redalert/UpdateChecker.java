package dev.ronse.redalert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.ronse.redalert.exceptions.ResponseStatusException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.function.Consumer;

public class UpdateChecker {
    public static JsonObject getLatestRelease() throws IOException, ResponseStatusException {
        OkHttpClient client = new OkHttpClient();

        try (Response res = client.newCall(new Request.Builder()
                        .url("https://api.github.com/repos/4ronse/RedAlertPlugin/releases/latest")
                        .build())
                .execute()
        ) {
            if (res.code() != 200) throw new ResponseStatusException(res.code(), 200);

            try (ResponseBody rb = res.body()) {
                if (rb == null) return null;

                String jsonString = rb.string();
                return JsonParser.parseString(jsonString).getAsJsonObject();
            }
        }
    }

    public static void getVersion(Consumer<String> success, Consumer<Exception> error) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RedAlert.getInstance(), () -> {
            try {
                JsonObject release = getLatestRelease();
                if(release == null) {
                    success.accept("");
                    return;
                }

                success.accept(release.get("tag_name").getAsString());
            } catch (IOException | ResponseStatusException e) {
                error.accept(e);
            }
        }, 0, 20L * 60 * 60);
    }
}
