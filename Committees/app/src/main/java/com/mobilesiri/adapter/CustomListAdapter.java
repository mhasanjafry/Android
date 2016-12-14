package com.mobilesiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mobilesiri.model.Committees;
import com.mobilesiri.volleycustomlistview.AppController;
import com.mobilesiri.volleycustomlistview.R;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Committees> BillItems;

    public CustomListAdapter(Activity activity, List<Committees> BillItems) {
        this.activity = activity;
        this.BillItems = BillItems;
    }

    @Override
    public int getCount() {
        return BillItems.size();
    }

    @Override
    public Object getItem(int location) {
        return BillItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView name = (TextView) convertView.findViewById(R.id.billid);
        TextView title = (TextView) convertView.findViewById(R.id.billtitle);
        TextView date = (TextView) convertView.findViewById(R.id.billdate);

        // getting billionaires data for the row
        Committees m = BillItems.get(position);

        // name
        name.setText(m.getID());

        title.setText(m.getname());

        date.setText(m.getChamber());

        return convertView;
    }
}
