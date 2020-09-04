package com.example.dropnote;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class tabMain extends Fragment {

    //String urlAddress = "http://192.168.178.210/dropnote/create_note.php";
    String urlAddress = "https://mk001.ahodesu.com/API/create_note.php";

    private EditText eTxtContent;
    private Button btnDropNote; //TODO: Add Button Animation

    public tabMain() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_main, container, false);

        //Initialize
        eTxtContent = (EditText) v.findViewById(R.id.eTxtContent);
        btnDropNote = (Button) v.findViewById(R.id.btnDropNote);

        btnDropNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = eTxtContent.getText().toString();
                if(!TextUtils.isEmpty(content)) {
                    //START ASYNC TASK
                    SenderCreateNote s = new SenderCreateNote(getActivity(), urlAddress, eTxtContent);
                    s.execute();
                }else {
                    //TODO: Error handling
                    Toast.makeText(getActivity(), "You have to write a note!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}