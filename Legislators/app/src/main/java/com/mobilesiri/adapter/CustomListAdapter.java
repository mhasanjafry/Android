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
import com.mobilesiri.model.Legislators;
import com.mobilesiri.volleycustomlistview.AppController;
import com.mobilesiri.volleycustomlistview.R;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Legislators> LegislatorsItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Legislators> LegislatorsItems) {
        this.activity = activity;
        this.LegislatorsItems = LegislatorsItems;
    }

    @Override
    public int getCount() {
        return LegislatorsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return LegislatorsItems.get(location);
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

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        NetworkImageView arr = (NetworkImageView) convertView
                .findViewById(R.id.arrow);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView worth = (TextView) convertView.findViewById(R.id.worth);
//        TextView source = (TextView) convertView.findViewById(R.id.source);
//        TextView year = (TextView) convertView.findViewById(R.id.inYear);

        // getting billionaires data for the row
        Legislators m = LegislatorsItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getImgURL(), imageLoader);
        arr.setImageUrl("http://www.clker.com/cliparts/B/5/C/M/6/i/right-grey-arrow-hi.png", imageLoader);

        // name
        name.setText(m.getName());

        // Wealth Source
        String t = "(" + String.valueOf(m.getpartyfirstChar()) + ")" + String.valueOf(m.getFullstateName());
        if (!((String.valueOf(m.getDistrict())).isEmpty())){
            t += " - District " + String.valueOf(m.getDistrict());
        }

//        source.setText(String.valueOf(m.getChamber()));

        worth.setText(t);

//        // release year
//        year.setText(String.valueOf(m.getFacebook()));

        return convertView;
    }
}
