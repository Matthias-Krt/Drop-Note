package com.example.dropnote;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class POSTDataPacker {

    String content, lat, lon;

    public POSTDataPacker(String content, String lat, String lon) {
        this.content = content;
        this.lat = lat;
        this.lon = lon;
    }

    public String packData() {
        JSONObject JSONObj = new JSONObject();
        StringBuffer packedData = new StringBuffer();

        try {
            JSONObj.put("content", content);
            JSONObj.put("latitude", lat);
            JSONObj.put("longitude", lon);

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
            //TODO: Error handling
            e.printStackTrace();
        }

        return null;
    }
}
