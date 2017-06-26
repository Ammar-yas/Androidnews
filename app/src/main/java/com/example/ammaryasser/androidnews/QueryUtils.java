package com.example.ammaryasser.androidnews;


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
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    final static int READ_TIME_OUT = 10000;
    final static int CONNECT_TIME_OUT = 15000;

    //Private constructor used to prevent creating instances of this class
    // and method should be called directly
    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("URL: ", "create url method error");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("URL", "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("JSON", "Problem retrieving the news JSON results.", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
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
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static List<news> extractFeaturesFromJson(String newsJson) {

        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }

        List<news> newsList = new ArrayList<>();

        try {
            JSONObject baseJasonObject = new JSONObject(newsJson).getJSONObject("response");
            JSONArray newsArray = baseJasonObject.getJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentStory = newsArray.getJSONObject(i);
                String title = currentStory.getString("webTitle");
                String date = currentStory.getString("webPublicationDate");
                String section = currentStory.getString("sectionName");
                String url = currentStory.getString("webUrl");
                newsList.add(new news(title, date, section, url));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return newsList;
    }

    public static List<news> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("fetchNewsData", "Problem making the HTTP request.", e);
        }
        return extractFeaturesFromJson(jsonResponse);
    }

}

