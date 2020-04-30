package com.example.oa_system;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.adapter.ComplainAdapter;
import com.entity.Complain;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminComplainActivity extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String getComplainsUrl = UrlConnectionUtil.IP + "/oaServer/admin/getComplainsUrl";

    private Complain[] complainArray;
    private List<Complain> complainList;
    private ArrayList<Complain> complains;
    private ComplainAdapter complainAdapter;
    private ListView complainListView;
    private Complain complain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complain);
        initView();
        initListener();
        new RequestNetworkDataTasks().execute(getComplainsUrl);
        //Intent intent = getIntent();


    }

    private void initListener() {
        complainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                complain = complains.get(position);
                Intent intent = new Intent(AdminComplainActivity.this, AdminAddFeedback.class);
                intent.putExtra("cId", complain.getcId().toString());
                startActivity(intent);
            }
        });
    }


    private void initView() {
        complainListView = (ListView) findViewById(R.id.admin_complain_listView);

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

            System.out.print(result);
            complainArray = new Gson().fromJson(result, Complain[].class);
            complainList = Arrays.asList(complainArray);
            complains = new ArrayList<>(complainList);

            complainAdapter = new ComplainAdapter(AdminComplainActivity.this, complains);
            complainListView.setAdapter(complainAdapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_complain, menu);
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
