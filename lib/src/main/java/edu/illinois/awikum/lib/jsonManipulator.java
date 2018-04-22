package edu.illinois.awikum.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class jsonManipulator {

    public static String json = "";

    public static String getTimeStamp(final String json) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonObject metadata = result.get("Meta Data").getAsJsonObject();
        return metadata.get("3. Last Refreshed").getAsString();
    }
}
