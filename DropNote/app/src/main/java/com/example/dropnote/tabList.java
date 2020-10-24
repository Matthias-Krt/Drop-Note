package com.example.dropnote;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class tabList extends Fragment {

    String urlAddress = "get_note_nearby";  //TODO: Change URL

    private ListView listView;

    public tabList() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_list, container, false);

        //Initialize
        listView = (ListView) v.findViewById(R.id.lvNotes);

        SenderNotesNearby s = new SenderNotesNearby(getActivity(), urlAddress, listView);
        s.execute();

        return v;
    }
}