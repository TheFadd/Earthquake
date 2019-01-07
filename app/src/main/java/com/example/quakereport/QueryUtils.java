package com.example.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class  QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {
    }


    public static ArrayList<EarthQuake> extractFeatureFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }


        ArrayList<EarthQuake> earthquakes = new ArrayList<>();
        try {
            JSONObject rootResponse = new JSONObject(earthquakeJSON);
            JSONArray earthquakeArray = rootResponse.getJSONArray("features");
            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentEarthQuake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthQuake.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String location = properties.getString("place");
                long timeinmilisec = properties.getLong("time");

                String url = properties.getString("url");

                EarthQuake earthquake = new EarthQuake(mag, location, timeinmilisec, url);
                earthquakes.add(earthquake);
            }
            /** EarthQuake name2 = new EarthQuake(2.3, "BILA TSERKVA", 255555, null );
             earthquakes.add(name2);
             EarthQuake name1 = new EarthQuake(1.3, "BILA TSERKVA", 255555, null );
             earthquakes.add(name1);
             EarthQuake name3 = new EarthQuake(3.5, "BILA TSERKVA", 255555, null );
             earthquakes.add(name3);
             EarthQuake name4 = new EarthQuake(4.6, "BILA TSERKVA", 255555, null );
             earthquakes.add(name4);
             EarthQuake name5 = new EarthQuake(5.4, "BILA TSERKVA", 255555, null );
             earthquakes.add(name5);
             EarthQuake name6 = new EarthQuake(7.3, "BILA TSERKVA", 255555, null );
             earthquakes.add(name6);
             EarthQuake name7 = new EarthQuake(8.3, "BILA TSERKVA", 255555, null );
             earthquakes.add(name7);
             EarthQuake name8 = new EarthQuake(9.3, "BILA TSERKVA", 255555, null );
             earthquakes.add(name8);
             EarthQuake name9 = new EarthQuake(10, "BILA TSERKVA", 255555, null );
             earthquakes.add(name9);*/

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<EarthQuake> fetchEarthquakeData(String requestUrl) {


        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<EarthQuake> earthquakes = extractFeatureFromJson(jsonResponse);

        return earthquakes;
    }


}


