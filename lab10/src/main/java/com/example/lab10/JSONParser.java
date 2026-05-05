package com.example.lab10;

import android.os.AsyncTask;

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
import java.util.ArrayList;
import java.util.List;

public class JSONParser extends AsyncTask<String, Void, String> {

    private static final String BEARER_TOKEN =
            "zpka_0b990cd5208b4597b269d9adad728965_33465e89";

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);

            InputStream inputStream = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String linie = null;
            while ((linie = reader.readLine()) != null) {
                builder.append(linie);
            }

            JSONArray array = new JSONArray(builder.toString());
            if (array.length() > 0) {
                JSONObject oras = array.getJSONObject(0);
                return oras.getString("Key");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // obtine prognoza si returneaza lista de zile
    public static class GetForecast extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            List<String> rezultat = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);

                InputStream inputStream = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String linie = null;
                while ((linie = reader.readLine()) != null) {
                    builder.append(linie);
                }

                JSONObject object = new JSONObject(builder.toString());
                JSONArray vector = object.getJSONArray("DailyForecasts");

                // iteram prin toate zilele (1 sau 5)
                for (int i = 0; i < vector.length(); i++) {
                    JSONObject ziObject  = vector.getJSONObject(i);
                    String data          = ziObject.getString("Date").substring(0, 10);
                    JSONObject temp      = ziObject.getJSONObject("Temperature");
                    JSONObject minim     = temp.getJSONObject("Minimum");
                    JSONObject maxim     = temp.getJSONObject("Maximum");
                    double min           = minim.getDouble("Value");
                    double max           = maxim.getDouble("Value");
                    rezultat.add(data + ":  Min=" + min + "°C  /  Max=" + max + "°C");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return rezultat;
        }
    }
}