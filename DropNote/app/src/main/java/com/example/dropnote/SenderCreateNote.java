package com.example.dropnote;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class SenderCreateNote extends AsyncTask<Void, Void, String> {

    private GPSTracker GPS = new GPSTracker();

    Context context;
    String urlAddress;

    EditText eTxtContent;
    String content;

    //TODO: Get latitude and longitude
    String lat, lon;

    ProgressDialog pd;

    public SenderCreateNote(Context context, String urlAddress, EditText eTxtContent) {
        this.context = context;
        this.urlAddress = urlAddress;

        //INPUT
        this.eTxtContent = eTxtContent;
        //GET Text
        content = eTxtContent.getText().toString();

        //GPS
        lat = Double.toString(GPS.getLatitude());
        lon = Double.toString(GPS.getLongitude());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //TODO: Loading
        pd = new ProgressDialog(context);
        pd.setTitle("Send");
        pd.setMessage("Sending...Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.send();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();

        //TODO
        if(response != null) {
            Toast.makeText(context, response + lat, Toast.LENGTH_LONG).show();

            //clear INPUT
            eTxtContent.setText("");
        }else {
            //TODO: Error handling
            Toast.makeText(context, "Unsuccessful " + response, Toast.LENGTH_LONG).show();
        }
    }

    private String send() {
        HttpURLConnection conn = Connector.connect(urlAddress, "POST");

        if(conn == null) {
            return null;
        }

        try {
            OutputStream os = conn.getOutputStream();

            //WRITE
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(new POSTDataPacker(content, lat, lon).packData());
            bw.flush();

            //RELEASE RES
            bw.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if(responseCode == conn.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                //RELEASE RES
                br.close();

                return response.toString();
            }else {
                //TODO: Error handling for HTTP-Response
            }
        }catch (IOException e) {
            //TODO: Error handling
            e.printStackTrace();
        }

        return null;
    }
}
