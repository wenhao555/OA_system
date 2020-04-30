package com.example.oa_system;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.adapter.OwnerAdapter;
import com.adapter.OwnerOnPayAdapter;
import com.entity.Owner;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminPaymentActivity extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String url = UrlConnectionUtil.IP + "/oaServer/admin/getOwners";
    private ListView paymentListView;
    private Owner[] ownersArray;
    private List<Owner> ownerList;
    private List<Owner> owners;
    private OwnerOnPayAdapter ownerOnPayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_payment);
        initView();
        initListener();
        new RequestNetworkDataTasks().execute(url);
    }

    private void initListener() {
        paymentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminPaymentActivity.this, AdminGetPayment.class);
                intent.putExtra("oName", owners.get(position).getoName());
                startActivity(intent);
            }
        });
        paymentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminPaymentActivity.this, AdminAddPayment.class);
                intent.putExtra("oName", owners.get(position).getoName());
                startActivity(intent);
                return true;
            }

        });
    }

    private void initView() {
        paymentListView = (ListView) findViewById(R.id.owner_onpayment_listView);
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

            ownersArray = new Gson().fromJson(result, Owner[].class);
            ownerList = Arrays.asList(ownersArray);
            owners = new ArrayList<>(ownerList);

            ownerOnPayAdapter = new OwnerOnPayAdapter(AdminPaymentActivity.this, owners);
            paymentListView.setAdapter(ownerOnPayAdapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_payment, menu);
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
