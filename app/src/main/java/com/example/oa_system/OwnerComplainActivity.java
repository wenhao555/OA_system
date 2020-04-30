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

import com.adapter.ComplainAdapter;
import com.adapter.FixAdapter;
import com.entity.Complain;
import com.entity.Fix;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OwnerComplainActivity extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String getComplainUrl = UrlConnectionUtil.IP + "/oaServer/owner/getComplainUrl";

    private Complain[] complainArray;
    private List<Complain> complainList;
    private ArrayList<Complain> complains;
    private ComplainAdapter complainAdapter;
    private ListView complainListView;
    private Complain complain;
    private String oName = "ee";
    private Button btnToComplain;

    private String cFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_complain);

        cFeedback = "";
        initView();
        initListener();
        new RequestNetworkDataTasks().execute(getComplainUrl, oName);
        //Intent intent = getIntent();


    }

    private void initListener() {
        btnToComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerComplainActivity.this, OwnerAddComplain.class);
                intent.putExtra("oName", oName);
                intent.putExtra("cFeedback",cFeedback);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        complainListView = (ListView) findViewById(R.id.complain_listView);
        btnToComplain = (Button) findViewById(R.id.btn_to_complain);
    }

    class RequestNetworkDataTasks extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String[] params) {
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            System.out.print(result);
            try{
                complainArray = new Gson().fromJson(result, Complain[].class);
                complainList = Arrays.asList(complainArray);
                complains = new ArrayList<>(complainList);
                oName = complains.get(0).getoName();

                complainAdapter = new ComplainAdapter(OwnerComplainActivity.this, complains);
                complainListView.setAdapter(complainAdapter);
            }catch (Exception e){
                oName = result.trim();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owner_complain, menu);
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
