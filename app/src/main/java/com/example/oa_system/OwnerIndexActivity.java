package com.example.oa_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.AdvertiseAdapter;
import com.adapter.OwnerAdapter;
import com.entity.Advertise;
import com.entity.Owner;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OwnerIndexActivity extends Activity {

    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();

    private String getAdvertiseUrl = UrlConnectionUtil.IP + "/oaServer/admin/getAdvertiseUrl";

    private List<Advertise> advertises;
    private AdvertiseAdapter advertiseAdapter;
    private ListView advertiseListView;
    private Advertise[] advertiseArray;
    private List<Advertise> advertiseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_index);

        initView();
        initListener();
        new RequestNetworkDataTasks().execute(getAdvertiseUrl);
        //Intent intent = getIntent();


    }

    private void initListener() {

    }

    private void initView() {
        advertiseListView = (ListView) findViewById(R.id.advertise_listView);
    }

    class RequestNetworkDataTasks extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String[] params) {
            return urlConnectionUtil.sendRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            advertiseArray = new Gson().fromJson(result, Advertise[].class);
            advertiseList = Arrays.asList(advertiseArray);
            advertises = new ArrayList<>(advertiseList);

            advertiseAdapter = new AdvertiseAdapter(OwnerIndexActivity.this,advertises);
            advertiseListView.setAdapter(advertiseAdapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owner_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
