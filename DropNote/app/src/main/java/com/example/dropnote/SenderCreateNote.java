package com.example.dropnote;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
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
import java.util.List;

public class SenderCreateNote extends AsyncTask<Void, Void, String> {

    Context context;
    String urlAddress;

    EditText eTxtContent;
    String content;

    String lat = "0", lon = "0";
    LocationManager locationManager;

    ProgressDialog pd;

    public SenderCreateNote(Context context, String urlAddress, EditText eTxtContent) {
        this.context = context;
        this.urlAddress = urlAddress;

        this.eTxtContent = eTxtContent;
        content = eTxtContent.getText().toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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

        if(response != null) {
            Toast.makeText(context, "Congratulation! You created a new Note.", Toast.LENGTH_LONG).show();
            eTxtContent.setText("");    //clear INPUT
        }else {
            Toast.makeText(context, "Oh no, something went wrong while creating a note.", Toast.LENGTH_LONG).show();
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
        Location bestLoc = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location loc = locationManager.getLastKnownLocation(provider);
            if (loc == null) {continue;}
            if (bestLoc == null || loc.getAccuracy() < bestLoc.getAccuracy()) {
                bestLoc = loc;
            }
        }

        return bestLoc;
    }

    private String send() {
        Location loc = getLastKnownLocation();
        this.lat = String.valueOf(loc.getLatitude());
        this.lon = String.valueOf(loc.getLongitude());

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
                //TODO: HTTP-Error handling
            }
        }catch (IOException e) {
            //TODO: IOException-Error handling
            e.printStackTrace();
        }

        return null;
    }
}
