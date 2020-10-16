package com.example.dropnote;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class POSTNotesNearbyPacker {

    String lat, lon, radius;

    public POSTNotesNearbyPacker(String lat, String lon, String radius) {
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public String packData() {
        JSONObject JSONObj = new JSONObject();
        StringBuffer packedData = new StringBuffer();

        try {
            JSONObj.put("latitude", lat);
            JSONObj.put("longitude", lon);
            JSONObj.put("radius", radius);

            Boolean isFirstVal = true;

            Iterator it = JSONObj.keys();

            do {
                String key = it.next().toString();
                String val = JSONObj.get(key).toString();

                if(isFirstVal) {
                    isFirstVal = false;
                }else{
                    packedData.append("&");
                }

                packedData.append(URLEncoder.encode(key, "UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(val, "UTF-8"));
            }while (it.hasNext());

            return packedData.toString();

        }catch (JSONException | UnsupportedEncodingException e) {
            //TODO: JSON-Error handling
            e.printStackTrace();
        }

        return null;
    }
}
