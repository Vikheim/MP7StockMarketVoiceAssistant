package edu.illinois.awikum.mp7_stockmarketvoiceassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.illinois.awikum.lib.jsonManipulator;

import static edu.illinois.awikum.lib.jsonManipulator.getTimeStamp;

public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "MP7:Main";
    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;
    /** Common company data to display on launch. */
    private String[] ticker = {"GSPC","IXIC","GOOG","AAPL","MSFT","EEM","EFA","SPY"};
    /** Individual company data request */
    private String companyCode = "";


    public String timeStamp;
    public String high;
    public String close;

    public void setCompanyCode(final String newCode) {
        companyCode = newCode;
    }
    public String getCompanyCode() {
        return companyCode;
    }
    public String[] getTicker() {
        return ticker;
    }


    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);
        //for (String i: getTicker()) {
            setCompanyCode("MSFT");
            Log.d(TAG, "HERE");
        final Button button = findViewById(R.id.button17);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startAPICall();

            }
        });

        Log.d(TAG, "HERE2");
        //}
    }

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void processFinDataJson(final String jsonResult) {
        timeStamp = jsonManipulator.getTimeStamp(jsonResult);
        close = jsonManipulator.getClose(jsonResult, timeStamp);
        Log.d(TAG, (timeStamp + "\n" + close));
    }

    /**
     * Make a call to the Alpha Vantage API.
     */
    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=60min&apikey=" + BuildConfig.ALPHA_VANTAGE_API_KEY,

                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                processFinDataJson(response.toString());
                                Log.d(TAG, response.toString(2));

                            } catch (JSONException ignored) { }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
