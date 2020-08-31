package com.example.dropnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListAdapter extends BaseAdapter {
    Context context;
    String[] creationDatetime;
    String[] content;

    private static LayoutInflater inflater = null;

    public ListAdapter(Context context, String[] creationDatetime, String[] content) {
        this.context = context;
        this.creationDatetime = creationDatetime;
        this.content = content;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return content.length;
    }

    @Override
    public Object getItem(int position) {
        return content[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if(vi == null){
            vi = inflater.inflate(R.layout.row_note, null);
        }

        TextView txtCreationDatetime = (TextView) vi.findViewById(R.id.txtNoteCreationDatetime);
        TextView txtContent = (TextView) vi.findViewById(R.id.txtNoteContent);

        txtCreationDatetime.setText(creationDatetime[position]);
        txtContent.setText(content[position]);

        return vi;
    }
}
