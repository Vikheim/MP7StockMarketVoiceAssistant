package edu.illinois.awikum.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class jsonManipulator {

    public static JsonObject parse(final String json) {
        JsonParser parser = new JsonParser();
        return parser.parse(json).getAsJsonObject();
    }

    public static String getTimeStamp(final String json) {
        JsonObject result = parse(json);
        JsonObject metadata = result.get("Meta Data").getAsJsonObject();
        return metadata.get("3. Last Refreshed").getAsString();
    }

    public static String getClose(final String json, final String timeStamp) {
        JsonObject result = parse(json);
        JsonObject timeSeries = result.get("Time Series (60min)").getAsJsonObject();
        JsonObject currentData = timeSeries.get(timeStamp).getAsJsonObject();
        return currentData.get("4. close").getAsString();
    }
}
