package com.mobilesiri.volleycustomlistview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mobilesiri.adapter.CustomListAdapter;
import com.mobilesiri.model.Bills;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends Activity {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabHost tabHost;

    // Billionaires json url
    private static final String url_active = "http://104.198.0.197:8080/bills?per_page=50&history.active=true&order=introduced_on__desc&last_version.urls.pdf__exists=true";
    private static final String url_new = "http://104.198.0.197:8080/bills?per_page=50&order=introduced_on__desc&last_version.urls.pdf__exists=true";
    private ProgressDialog pDialog;
    private List<Bills> activebills = new ArrayList<>();
    private List<Bills> newbills = new ArrayList<>();

    HashMap<String, Integer> mapIndex1, mapIndex2;

    private ListView activebillView, newbillView;
    private CustomListAdapter activebilladapter, newbilladapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab Tag");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab Tag");

        // Set the Tab name and Activit that will be opened when particular Tab will be selected
        tab1.setIndicator(createTabIndicator("ACTIVE BILLS"));
        tab1.setContent(R.id.i_layout_1);

        tab2.setIndicator(createTabIndicator("NEW BILLS"));
        tab2.setContent(R.id.i_layout_2);

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

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
                                    Bills active = new Bills();
                                    if (!c.isNull("sponsor")){
                                        JSONObject sponsor = c.getJSONObject("sponsor");
                                        active.setName(sponsor.getString("title"),sponsor.getString("last_name"),sponsor.getString("first_name"));
                                    }
                                    if (!c.isNull("short_title")) active.setshorttitle(c.getString("short_title"));
                                    if (!c.isNull("official_title")) active.setlongtitle(c.getString("official_title"));

                                    if (!c.isNull("bill_id")) active.setid(c.getString("bill_id"));
                                    if (!c.isNull("bill_type")) active.settype(c.getString("bill_type"));
                                    if (!c.isNull("chamber")) active.setChamber(c.getString("chamber"));
                                    if (!c.isNull("introduced_on")) active.setintroduced_on(c.getString("introduced_on"));
                                    if (!c.isNull("history")){
                                        JSONObject history = c.getJSONObject("history");
                                        if ((!history.isNull("active") && (Boolean)history.get("active"))){
                                            active.setBillActive();
                                        }
                                    }
                                    if (!c.isNull("last_version")){
                                        JSONObject last_version = c.getJSONObject("last_version");
                                        if (!last_version.isNull("version_name")) {
                                            active.setversion(last_version.getString("version_name"));
                                        }
                                        if (!last_version.isNull("urls")) {
                                            JSONObject urls = last_version.getJSONObject("urls");
                                            if (!urls.isNull("pdf")) {
                                                active.setWebsite(urls.getString("pdf"));
                                            }
                                        }
                                    }
                                    activebills.add(active);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        activebilladapter.notifyDataSetChanged();
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
        activebillView = (ListView) findViewById(R.id.activebill);
        activebilladapter = new CustomListAdapter(this, activebills);
        activebillView.setAdapter(activebilladapter);
        activebillView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, DetailBills.class);
                Bills data = (Bills) activebillView.getAdapter().getItem(position);
                newActivity.putExtra("bills", data);
                startActivity(newActivity);
            }
        });

        JsonObjectRequest newBillReq = new JsonObjectRequest
                (Request.Method.GET, url_new, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hidePDialog();
                        try {
                            JSONArray result = response.getJSONArray("results");
                            // Parsing json
                            for (int i = 0; i < result.length(); i++) {
                                try {
                                    JSONObject c = result.getJSONObject(i);
                                    Bills newbill = new Bills();
                                    if (!c.isNull("sponsor")){
                                        JSONObject sponsor = c.getJSONObject("sponsor");
                                        newbill.setName(sponsor.getString("title"),sponsor.getString("last_name"),sponsor.getString("first_name"));
                                    }
                                    if (!c.isNull("short_title")) newbill.setshorttitle(c.getString("short_title"));
                                    if (!c.isNull("official_title")) newbill.setlongtitle(c.getString("official_title"));
                                    if (!c.isNull("bill_id")) newbill.setid(c.getString("bill_id"));
                                    if (!c.isNull("bill_type")) newbill.settype(c.getString("bill_type"));
                                    if (!c.isNull("chamber")) newbill.setChamber(c.getString("chamber"));
                                    if (!c.isNull("introduced_on")) newbill.setintroduced_on(c.getString("introduced_on"));
                                    if (!c.isNull("history")){
                                        JSONObject history = c.getJSONObject("history");
                                        if ((!history.isNull("active") && (Boolean)history.get("active"))){
                                            newbill.setBillActive();
                                        }
                                    }
                                    if (!c.isNull("last_version")){
                                        JSONObject last_version = c.getJSONObject("last_version");
                                        if (!last_version.isNull("version_name")) {
                                            newbill.setversion(last_version.getString("version_name"));
                                        }
                                        if (!last_version.isNull("urls")) {
                                            JSONObject urls = last_version.getJSONObject("urls");
                                            if (!urls.isNull("pdf")) {
                                                newbill.setWebsite(urls.getString("pdf"));
                                            }
                                        }
                                    }
                                    newbills.add(newbill);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        newbilladapter.notifyDataSetChanged();
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
        AppController.getInstance().addToRequestQueue(newBillReq);


        newbillView = (ListView) findViewById(R.id.newbills);
        newbilladapter = new CustomListAdapter(this, newbills);
        newbillView.setAdapter(newbilladapter);
        newbillView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, DetailBills.class);
                Bills data = (Bills) newbillView.getAdapter().getItem(position);
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
