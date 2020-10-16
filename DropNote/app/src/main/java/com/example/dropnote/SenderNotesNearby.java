package com.example.dropnote;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class SenderNotesNearby extends AsyncTask<Void, Void, String> {

    Context context;
    String urlAddress;

    ListView listView;

    //TODO: Get Latitude and Longitude and radius
    String lat = "0", lon = "0", radius = "1000";

    ProgressDialog pd;

    public SenderNotesNearby(Context context, String urlAddress, ListView listView) {
        this.context = context;
        this.urlAddress = urlAddress;

        this.listView = listView;
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
            Toast.makeText(context, "There are notes nearby! Check them out.", Toast.LENGTH_SHORT).show();
            try {
                convertToJSONObj(response);
            } catch (JSONException e) {
                //TODO: JSON Error handling
                e.printStackTrace();
            }
        }else {
            Toast.makeText(context, "There are no notes around you. Be the first to write one!", Toast.LENGTH_SHORT).show();
        }
    }

    private String send() {
        HttpURLConnection conn = Connector.connect(urlAddress, "POST");

        if (conn == null) {
            return null;
        }

        try {
            OutputStream os = conn.getOutputStream();

            //WRITE
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(new POSTNotesNearbyPacker(lat, lon, radius).packData());
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

    private void convertToJSONObj(String JSON) throws JSONException {
        JSONArray JSONArr = new JSONArray(JSON);

        String[] notes = new String[JSONArr.length()];
        String[] creationDatetime = new String[JSONArr.length()];

        for (int i = 0; i < JSONArr.length(); i++) {
            JSONObject JSONObj = JSONArr.getJSONObject(i);

            notes[i] = JSONObj.getString("content");
            creationDatetime[i] = JSONObj.getString("creation_datetime");
        }

        ListAdapter listAdapter = new ListAdapter(context, creationDatetime, notes);
        listView.setAdapter(listAdapter);
    }
}
