package com.mobilesiri.volleycustomlistview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mobilesiri.adapter.CustomListAdapter;
import com.mobilesiri.model.Committees;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabHost tabHost;

    // Billionaires json url
    private static final String url_active = "http://104.198.0.197:8080/committees?order=committee_id__asc&per_page=all";
    private ProgressDialog pDialog;
    private List<Committees> house = new ArrayList<>();
    private List<Committees> senate = new ArrayList<>();
    private List<Committees> joint = new ArrayList<>();

    private ListView houseView, senateView, jointview;
    private CustomListAdapter houseadapter, senateadapter, jointadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab Tag");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab Tag");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab Tag");

        // Set the Tab name and Activit that will be opened when particular Tab will be selected
        tab1.setIndicator(createTabIndicator("HOUSE"));
        tab1.setContent(R.id.i_layout_1);

        tab2.setIndicator(createTabIndicator("SENATE"));
        tab2.setContent(R.id.i_layout_2);

        tab3.setIndicator(createTabIndicator("JOINT"));
        tab3.setContent(R.id.i_layout_3);

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest billionaireReq = new JsonObjectRequest
                (Request.Method.GET, url_active, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, response.toString());
                        hidePDialog();
                        try {
                            JSONArray result = response.getJSONArray("results");
                            // Parsing json
                            for (int i = 0; i < result.length(); i++) {
                                try {
                                    JSONObject c = result.getJSONObject(i);
//                                    WorldsBillionaires worldsBillionaires = new WorldsBillionaires();
                                    Committees active = new Committees();
                                    if (!c.isNull("committee_id")) active.setid(c.getString("committee_id"));
                                    if (!c.isNull("name")) active.setname(c.getString("name"));
                                    if (!c.isNull("chamber")) active.setChamber(c.getString("chamber"));
                                    if (!c.isNull("parent_committee_id")) active.setparent(c.getString("parent_committee_id"));

                                    if (!c.isNull("phone")) active.setcontact(c.getString("phone"));

                                    if (!c.isNull("office")) active.setoffice(c.getString("office"));
                                    if (active.getChamber().equals("House")){
                                        house.add(active);
                                    }else if ((active.getChamber().equals("Senate"))){
                                        senate.add(active);
                                    }else if ((active.getChamber().equals("Joint"))){
                                        joint.add(active);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        houseadapter.notifyDataSetChanged();
                        senateadapter.notifyDataSetChanged();
                        jointadapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        VolleyLog.e(TAG, "Error: " + error.getMessage());
                        hidePDialog();
                    }
                });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(billionaireReq);

        houseView = (ListView) findViewById(R.id.house);
        houseadapter = new CustomListAdapter(this, house);
        houseView.setAdapter(houseadapter);
        houseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, CommitteeDetail.class);
                Committees data = (Committees) houseView.getAdapter().getItem(position);
                newActivity.putExtra("bills", data);
                startActivity(newActivity);
            }
        });
        senateView = (ListView) findViewById(R.id.senate);
        senateadapter = new CustomListAdapter(this, senate);
        senateView.setAdapter(senateadapter);
        senateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, CommitteeDetail.class);
                Committees data = (Committees) senateView.getAdapter().getItem(position);
                newActivity.putExtra("bills", data);
                startActivity(newActivity);
            }
        });
        jointview = (ListView) findViewById(R.id.joint);
        jointadapter = new CustomListAdapter(this, joint);
        jointview.setAdapter(jointadapter);
        jointview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, CommitteeDetail.class);
                Committees data = (Committees) jointview.getAdapter().getItem(position);
                newActivity.putExtra("bills", data);
                startActivity(newActivity);
            }
        });
    }

    private View createTabIndicator(String text) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_indicator_label);
        textView.setText(text);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
