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

    //String urlAddress = "http://192.168.178.210/dropnote/get_note_nearby.php";
    String urlAddress = "https://mk001.ahodesu.com/API/get_all_notes.php";          //TODO: Change URL to notes nearby
    //String urlAddress = "http://192.168.178.210/dropnote/get_all_notes.php";    //Test URL (for all Notes)

    private ListView listView;
    public ListAdapter listAdapter;

    public tabList() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_list, container, false);

        //Initialize
        listView = (ListView) v.findViewById(R.id.lvNotes);

        //TODO: Notes nearby
        SenderNotesNearby s = new SenderNotesNearby(getActivity(), urlAddress, listView);
        s.execute();

        return v;
    }
}