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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.adapter.FixAdapter;
import com.entity.Fix;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminFixActivity extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String getFixsUrl = UrlConnectionUtil.IP + "/oaServer/admin/getFixsUrl";

    private Fix[] fixArray;
    private List<Fix> fixList;
    private ArrayList<Fix> fixs;
    private FixAdapter fixAdapter;
    private ListView fixListView;
    private Fix fix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fix);
        initView();
        initListener();

        new RequestNetworkDataTasks().execute(getFixsUrl);
        //Intent intent = getIntent();


    }

    private void initListener() {

        fixListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminFixActivity.this, AdminGetFix.class);
                intent.putExtra("fId", fixs.get(position).getfId().toString());
                intent.putExtra("fStatus", fixs.get(position).getfStatus());
                startActivity(intent);
            }
        });
    }


    private void initView() {
        fixListView = (ListView) findViewById(R.id.admin_fix_listView);
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

            fixArray = new Gson().fromJson(result, Fix[].class);
            fixList = Arrays.asList(fixArray);
            fixs = new ArrayList<>(fixList);

            fixAdapter = new FixAdapter(AdminFixActivity.this, fixs);
            fixListView.setAdapter(fixAdapter);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_fix, menu);
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
