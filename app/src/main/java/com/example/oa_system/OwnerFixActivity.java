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
import com.adapter.PaymentAdapter;
import com.entity.Fix;
import com.entity.Payment;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OwnerFixActivity extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String getFixUrl = UrlConnectionUtil.IP + "/oaServer/owner/getFixUrl";
    private String updateFixUrl = UrlConnectionUtil.IP + "/oaServer/owner/updateFixUrl";
    private Fix[] fixArray;
    private List<Fix> fixList;
    private ArrayList<Fix> fixs;
    private FixAdapter fixAdapter;
    private ListView fixListView;
    private Fix fix;
    private Button btnAddFix;
    private String oName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_fix);
        initView();
        initListener();
        oName = "as";
        new RequestNetworkDataTasks().execute(getFixUrl, oName);
        //Intent intent = getIntent();


    }

    private void initListener() {
        btnAddFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerFixActivity.this, OwnerAddFix.class);
                intent.putExtra("oName",oName);
                startActivity(intent);
            }
        });
        fixListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                fix = fixs.get(position);
                if(!fix.getfStatus().trim().equals("处理中")){
                    Toast.makeText(OwnerFixActivity.this, "非处理中状态，无权确认", Toast.LENGTH_SHORT).show();
                    return true;
                }

                AlertDialog.Builder confirm = new AlertDialog.Builder(OwnerFixActivity.this);
                confirm.setTitle("提示");
                confirm.setMessage("是否确认完成？");
                confirm.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fix.setfStatus("已完成");
                        new updateFix().execute(updateFixUrl, fix.toString());
                        Toast.makeText(OwnerFixActivity.this, "已确认完成", Toast.LENGTH_SHORT).show();
                    }
                });
                confirm.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OwnerFixActivity.this, "未确认完成", Toast.LENGTH_SHORT).show();
                    }
                });
                confirm.setCancelable(false);
                AlertDialog dialog = confirm.create();
                dialog.show();
                return true;
            }

        });
    }


    private void initView() {
        fixListView = (ListView) findViewById(R.id.fix_listView);
        btnAddFix = (Button) findViewById(R.id.btn_add_fix);
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

            try{
                fixArray = new Gson().fromJson(result, Fix[].class);
                fixList = Arrays.asList(fixArray);
                fixs = new ArrayList<>(fixList);

                oName = fixs.get(0).getoName();

                fixAdapter = new FixAdapter(OwnerFixActivity.this, fixs);
                fixListView.setAdapter(fixAdapter);
            }catch (Exception e){
                oName = result.trim();
            }

        }
    }
    class updateFix extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);

            fixArray = new Gson().fromJson(result, Fix[].class);
            fixList = Arrays.asList(fixArray);
            fixs = new ArrayList<>(fixList);

            fixAdapter.refreshData(fixs);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owner_fix, menu);
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
