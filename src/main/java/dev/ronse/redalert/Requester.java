package dev.ronse.redalert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.ronse.redalert.exceptions.ResponseStatusException;
import dev.ronse.redalert.orefalerts.OrefAlert;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
public class Requester {
    public static OrefAlert checkForAlerts() throws IOException, ResponseStatusException {
        String url = RedAlert.config.debug ? RedAlert.config.debugSource :
                "https://www.oref.org.il/WarningMessages/alert/alerts.json";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Referer", "https://www.oref.org.il/")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Content-Type", "application/json")
                .build();

        Response res = client.newCall(request).execute();
        ResponseBody rb = res.body();

        if(res.code() != 200) throw new ResponseStatusException(res.code(), 200);
        if(rb == null) return null;

        String json = rb.string();
        if(json.length() < 5) return null;

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        res.close();

        return new OrefAlert(jsonObject);
    }
}
