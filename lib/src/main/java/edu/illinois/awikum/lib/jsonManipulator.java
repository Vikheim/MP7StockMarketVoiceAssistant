package edu.illinois.awikum.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class jsonManipulator {

    /**
     * Helper method to parse incoming an json toString().
     * @param json String representation of json data.
     * @return A Json Object representation of json data.
     */
    private static JsonObject parse(final String json) {
        JsonParser parser = new JsonParser();
        return parser.parse(json).getAsJsonObject();
    }

    /**
     * Returns the most recent time stamp of financial data.
     * @param json Json toString() of financial data.
     * @return Most recent time stamp.
     */
    public static String getTimeStamp(final String json) {
        JsonObject result = parse(json);
        JsonObject metadata = result.get("Meta Data").getAsJsonObject();
        return metadata.get("3. Last Refreshed").getAsString();
    }

    /**
     * Returns the close value for the most recent time stamp.
     * @param json Json toString() of financial data.
     * @param timeStamp Most recent timestamp.
     * @return Most recent close value.
     */
    public static String getClose(final String json, final String timeStamp) {
        JsonObject result = parse(json);
        JsonObject timeSeries = result.get("Time Series (60min)").getAsJsonObject();
        JsonObject currentData = timeSeries.get(timeStamp).getAsJsonObject();
        return currentData.get("4. close").getAsString();
    }
}
