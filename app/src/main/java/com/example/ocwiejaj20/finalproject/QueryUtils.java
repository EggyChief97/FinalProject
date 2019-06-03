package com.example.ocwiejaj20.finalproject;
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

    public final class QueryUtils {
        private static final String LOG_TAG = CarSelectionActivity.class.getSimpleName();

        private QueryUtils() {
        }

        public static List<Car> extractBooks(String jsonResponce) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            ArrayList<Car> cars = new ArrayList<>();
            try {
                JSONObject baseJSON = new JSONObject(jsonResponce);
                JSONArray carArray = baseJSON.getJSONArray("Results");
                for (int i = 0; i < carArray.length(); i++) {
                    JSONObject currentBook = carArray.getJSONObject(i);
                    String makeOfCar = currentBook.getString("Make_Name");
                    String modelOfCar = currentBook.getString("Model_Name");
                    Car newCar = new Car(makeOfCar, modelOfCar);
                    cars.add(newCar);
                }
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the car JSON results", e);

            }
            return cars;
        }

        private static URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error with creating URL", e);
            }
            return url;
        }

        private static String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(10000);
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
                InputStreamReader inputStreamReader =
                        new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        public static List<Car> fetchBookData(String requestUrl) {
            URL url = createUrl(requestUrl);

            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request", e);
            }

            List<Car> cars = extractBooks(jsonResponse);
            return cars;
        }
    }
