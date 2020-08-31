package com.example.dropnote;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connector {

    public static HttpURLConnection connect(String urlAddress, String method) {

        try {
            URL url = new URL(urlAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //SET Properties
            conn.setRequestMethod(method);
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            return conn;
        }catch (MalformedURLException e) {
            //TODO: Error Handling
            e.printStackTrace();
        }catch (IOException e) {
            //TODO: Error Handling
            e.printStackTrace();
        }

        return null;

    }
}
