package com.example.oa_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.OwnerAdapter;
import com.entity.Owner;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminIndexActivity extends Activity {

    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String url = UrlConnectionUtil.IP + "/oaServer/admin/getOwners";
    private String deleteOwner = UrlConnectionUtil.IP + "/oaServer/admin/doDelete";
    private String selectOwner = UrlConnectionUtil.IP + "/oaServer/admin/selectOwner";

    private TextView show;
    private ListView ownerListView;
    private Owner[] ownersArray;
    private List<Owner> ownerList;
    private List<Owner> owners;
    private OwnerAdapter ownerAdapter;
    private Button adminAddOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);
        initView();
        initListener();
        new RequestNetworkDataTasks().execute(url);

    }


    private void initListener() {
        ownerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder confirm = new AlertDialog.Builder(AdminIndexActivity.this);
                confirm.setTitle("提示");
                confirm.setMessage("是否确认删除？");
                confirm.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteOwner().execute(deleteOwner, owners.get(position).getoName());
                        Toast.makeText(AdminIndexActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                confirm.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AdminIndexActivity.this, "未删除", Toast.LENGTH_SHORT).show();
                    }
                });
                confirm.setCancelable(false);
                AlertDialog dialog = confirm.create();
                dialog.show();

                return true;
            }
        });
        ownerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new SelectOwner().execute(selectOwner, owners.get(position).getoName());
            }
        });
        adminAddOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminIndexActivity.this, AdminAddOwnerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        ownerListView = (ListView) findViewById(R.id.owner_listView);
        adminAddOwner = (Button) findViewById(R.id.admin_add_owner);

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

            ownerAdapter = new OwnerAdapter(AdminIndexActivity.this, owners);
            ownerListView.setAdapter(ownerAdapter);


        }
    }

    class DeleteOwner extends AsyncTask<String, Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);

            ownersArray = new Gson().fromJson(result, Owner[].class);
            ownerList = Arrays.asList(ownersArray);
            owners = new ArrayList<>(ownerList);

            ownerAdapter.refreshData(owners);
        }
    }

    class SelectOwner extends AsyncTask<String, Integer ,String>{

        @Override
        protected String doInBackground(String... params) {
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);
            Intent intent = new Intent(AdminIndexActivity.this, AdminOwnerModifyActivity.class);
            intent.putExtra("owner", result);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_index, menu);
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
