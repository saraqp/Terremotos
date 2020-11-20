package quesado.prado.sara.terremotos.utils;

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

import quesado.prado.sara.terremotos.model.Terremoto;

public class QueryUtils {
    private QueryUtils(){}
    public static ArrayList<Terremoto> extraerTerremotos(String sample_json_response){
        ArrayList<Terremoto> terremotos=new ArrayList<>();
        try{
            JSONObject root=new JSONObject(sample_json_response);
            JSONArray features=root.getJSONArray("features");
            for (int i=0;i<features.length();i++){
                JSONObject object=features.getJSONObject(i);
                JSONObject properties=object.getJSONObject("properties");
                double magnitud=properties.getDouble("mag");
                String localizacionInicial=properties.getString("place");
                String url=properties.getString("url");
                long fecha=properties.getLong("time");
                terremotos.add(new Terremoto(magnitud,localizacionInicial,fecha,url));
            }

        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return terremotos;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("CREATEURL", "Error with creating URL ", e);
        }
        return url;
    }
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
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

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("MAKEHTTPREQUEST", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("MAKEHTTPREQUEST", "Problem retrieving the earthquake JSON results.", e);
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
}
