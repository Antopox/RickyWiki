package com.example.rickywiki.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnectRick {

    private static final String URL_BASE = "https://rickandmortyapi.com/api";

    public static String getRequest(String endpoint){
        HttpURLConnection http = null;
        String content = null;

        try{
            URL url = new URL(URL_BASE + endpoint);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (http != null) http.disconnect();
        }

        return content;
    }
}
