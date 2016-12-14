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
import com.mobilesiri.model.Legislators;

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
    private static final String url = "http://104.198.0.197:8080/legislators?all_legislators=true&per_page=all";
    private ProgressDialog pDialog;
    private List<Legislators> worldsBillionairesList = new ArrayList<>();
    private List<Legislators> houseLegisList = new ArrayList<>();
    private List<Legislators> senateLegisList = new ArrayList<>();

    HashMap<String, Integer> mapIndex1, mapIndex2, mapIndex3;

    private ListView listView, houseView, senateView;
    private CustomListAdapter adapter, houseadapter, senateadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab Tag");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab Tag");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third tab Tag");

        // Set the Tab name and Activit that will be opened when particular Tab will be selected
        tab1.setIndicator(createTabIndicator("By States"));
        tab1.setContent(R.id.i_layout_1);

        tab2.setIndicator(createTabIndicator("House"));
        tab2.setContent(R.id.i_layout_2);

        tab3.setIndicator(createTabIndicator("Senate"));
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
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                                    Legislators legis = new Legislators();
                                    legis.setName(c.getString("title"),c.getString("last_name"),c.getString("first_name"));
                                    if (!c.isNull("party")) legis.setParty(c.getString("party"));
                                    if (!c.isNull("term_start")) legis.setStart_term(c.getString("term_start"));
                                    if (!c.isNull("term_end")) legis.setEnd_term(c.getString("term_end"));
                                    if (!c.isNull("birthday")) legis.setBirthday(c.getString("birthday"));
                                    if (!c.isNull("district")) legis.setDistrict(c.getString("district"));
                                    if (!c.isNull("oc_email")) legis.setEmail(c.getString("oc_email"));
                                    if (!c.isNull("state")) legis.setState(c.getString("state"));
                                    if (!c.isNull("state_name")) legis.setFullstateName(c.getString("state_name"));
                                    if (!c.isNull("office")) legis.setOffice(c.getString("office"));
                                    if (!c.isNull("chamber")) legis.setChamber(c.getString("chamber"));
                                    if (!c.isNull("facebook_id")) legis.setFacebook(c.getString("facebook_id"));
                                    if (!c.isNull("twitter_id")) legis.setTwitter(c.getString("twitter_id"));
                                    if (!c.isNull("bioguide_id")) legis.setImgUrl(c.getString("bioguide_id"));
                                    if (!c.isNull("website")) legis.setWebsite(c.getString("website"));
                                    if (!c.isNull("phone")) legis.setContact(c.getString("phone"));
                                    if (!c.isNull("fax")) legis.setFax(c.getString("fax"));

                                    // adding Billionaire to worldsBillionaires array
                                    worldsBillionairesList.add(legis);
                                    Collections.sort(worldsBillionairesList, new Comparator<Legislators>() {
                                        public int compare(Legislators o1, Legislators o2)
                                        {
                                            return o1.getFullstateName().compareTo(o2.getFullstateName());
                                        }
                                    });
                                    switch (legis.getChamber()) {
                                        case "Senate":
                                            senateLegisList.add(legis);
                                            Collections.sort(senateLegisList, new Comparator<Legislators>() {
                                                public int compare(Legislators o1, Legislators o2)
                                                {
                                                    return o1.getName().compareTo(o2.getName());
                                                }
                                            });
                                            break;
                                        case "House":
                                            houseLegisList.add(legis);
                                            Collections.sort(houseLegisList, new Comparator<Legislators>() {
                                                public int compare(Legislators o1, Legislators o2)
                                                {
                                                    return o1.getName().compareTo(o2.getName());
                                                }
                                            });
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mapIndex1 = new LinkedHashMap<String, Integer>();
                            mapIndex2 = new LinkedHashMap<String, Integer>();
                            mapIndex3 = new LinkedHashMap<String, Integer>();
                            String[] sections1, sections2, sections3;
                            for (int x = 0; x < worldsBillionairesList.size(); x++) {
                                Legislators leg = worldsBillionairesList.get(x);
                                String ch = leg.getState().substring(0, 1);
                                ch = ch.toUpperCase(Locale.US);
                                mapIndex1.put(ch, x);
                            }
                            for (int x = 0; x < senateLegisList.size(); x++) {
                                Legislators leg = senateLegisList.get(x);
                                String ch = leg.getName().substring(0, 1);
                                ch = ch.toUpperCase(Locale.US);
                                mapIndex2.put(ch, x);
                            }
                            for (int x = 0; x < houseLegisList.size(); x++) {
                                Legislators leg = houseLegisList.get(x);
                                String ch = leg.getName().substring(0, 1);
                                ch = ch.toUpperCase(Locale.US);
                                mapIndex3.put(ch, x);
                            }
                            Set<String> sectionLetters1 = mapIndex1.keySet();
                            Set<String> sectionLetters2 = mapIndex2.keySet();
                            Set<String> sectionLetters3 = mapIndex3.keySet();
                            // create a list from the set to sort
                            ArrayList<String> sectionList1 = new ArrayList<String>(sectionLetters1);
                            Collections.sort(sectionList1);
                            sections1 = new String[sectionList1.size()];
                            sectionList1.toArray(sections1);
                            ArrayList<String> sectionList2 = new ArrayList<String>(sectionLetters2);
                            Collections.sort(sectionList2);
                            sections2 = new String[sectionList2.size()];
                            sectionList2.toArray(sections2);
                            ArrayList<String> sectionList3 = new ArrayList<String>(sectionLetters3);
                            Collections.sort(sectionList3);
                            sections3 = new String[sectionList3.size()];
                            sectionList3.toArray(sections3);

                            LinearLayout indexLayout = (LinearLayout) findViewById(R.id.sideIndex);
                            indexLayout.setBackgroundColor(Color.parseColor("#D3D3D3"));
                            TextView textView;
                            List<String> indexList = new ArrayList<String>(mapIndex1.keySet());
                            for (String index : indexList) {
                                textView = (TextView) getLayoutInflater().inflate(
                                        R.layout.side_index_item, null);
                                textView.setText(index);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView selectedIndex = (TextView) v;
//                                        listView = (ListView) findViewById(R.id.list);
                                        listView.setSelection(mapIndex1.get(selectedIndex.getText()));
                                    }
                                });
                                indexLayout.addView(textView);
                            }
                            LinearLayout indexLayout2 = (LinearLayout) findViewById(R.id.sideIndexsenate);
                            indexLayout2.setBackgroundColor(Color.parseColor("#D3D3D3"));
                            List<String> indexList2 = new ArrayList<String>(mapIndex2.keySet());
                            for (String index : indexList2) {
                                textView = (TextView) getLayoutInflater().inflate(
                                        R.layout.side_index_item, null);
                                textView.setText(index);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView selectedIndex = (TextView) v;
//                                        senateView = (ListView) findViewById(R.id.senate);
                                        senateView.setSelection(mapIndex2.get(selectedIndex.getText()));
                                    }
                                });
                                indexLayout2.addView(textView);
                            }
                            LinearLayout indexLayout3 = (LinearLayout) findViewById(R.id.sideIndexhouse);
                            indexLayout3.setBackgroundColor(Color.parseColor("#D3D3D3"));
                            List<String> indexList3 = new ArrayList<String>(mapIndex3.keySet());
                            for (String index : indexList3) {
                                textView = (TextView) getLayoutInflater().inflate(
                                        R.layout.side_index_item, null);
                                textView.setText(index);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView selectedIndex = (TextView) v;
//                                        senateView = (ListView) findViewById(R.id.senate);
                                        houseView.setSelection(mapIndex3.get(selectedIndex.getText()));
                                    }
                                });
                                indexLayout3.addView(textView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
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

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, worldsBillionairesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, DetailLegislator.class);
                Legislators data = (Legislators) listView.getAdapter().getItem(position);
                newActivity.putExtra("legislator", data);
                startActivity(newActivity);
            }
        });
        houseView = (ListView) findViewById(R.id.house);
        houseadapter = new CustomListAdapter(this, houseLegisList);
        houseView.setAdapter(houseadapter);
        houseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, DetailLegislator.class);
                Legislators data = (Legislators) houseView.getAdapter().getItem(position);
                newActivity.putExtra("legislator", data);
                startActivity(newActivity);
            }
        });
        senateView = (ListView) findViewById(R.id.senate);
        senateadapter = new CustomListAdapter(this, senateLegisList);
        senateView.setAdapter(senateadapter);
        senateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, DetailLegislator.class);
                Legislators data = (Legislators) senateView.getAdapter().getItem(position);
                newActivity.putExtra("legislator", data);
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
