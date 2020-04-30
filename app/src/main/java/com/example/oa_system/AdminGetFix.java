package com.example.oa_system;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entity.Fix;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;


public class AdminGetFix extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String adminUpdateFixUrl = UrlConnectionUtil.IP + "/oaServer/admin/adminUpdateFixUrl";
    private String adminGetOneFixUrl = UrlConnectionUtil.IP + "/oaServer/admin/adminGetOneFixUrl";
    private Button doUpdateFix;
    private EditText fixName;
    private EditText fixUsernaem;
    private EditText fixContent;
    private String fId;
    private String fStatus;
    private EditText fixStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_get_fix);
        fId = getIntent().getStringExtra("fId");
        fStatus = getIntent().getStringExtra("fStatus");
        initView();
        initListener();

        new GetOneFix().execute(adminGetOneFixUrl, fId);
    }

    private void initListener() {
        doUpdateFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fStatus.equals("处理中") || fStatus.equals("已完成")){
                    Toast.makeText(AdminGetFix.this, "无需重复审核", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fName = fixName.getText().toString();
                String oName = fixUsernaem.getText().toString();
                String fContent = fixContent.getText().toString();
                if(TextUtils.isEmpty(fName)){
                    fixName.setError("报修项目不能为空");
                    return;
                }
                if(TextUtils.isEmpty(fContent)){
                    fixContent.setError("项目描述不能为空");
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("fId", fId);
                jsonObject.addProperty("oName", oName);
                jsonObject.addProperty("fName", fName);
                jsonObject.addProperty("fDescription", fContent);
                jsonObject.addProperty("fStatus", "处理中");

                new RequestNetworkDataTasks().execute(adminUpdateFixUrl,jsonObject.toString());
            }
        });
    }


    private void initView() {

        doUpdateFix = (Button) findViewById(R.id.do_update_fix);
        fixName = (EditText) findViewById(R.id.admin_fix_name);
        fixUsernaem = (EditText) findViewById(R.id.admin_fix_username);
        fixContent = (EditText)findViewById(R.id.admin_fix_content);
        fixStatus = (EditText) findViewById(R.id.admin_fix_status);
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
            Intent intent = new Intent(AdminGetFix.this, AdminFixActivity.class);
            startActivity(intent);
            AdminGetFix.this.finish();
        }
    }

    class GetOneFix extends AsyncTask<String, Integer, String> {
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
            Fix fix = new Gson().fromJson(result, Fix.class);

            fixName.setText(fix.getfName());
            fixContent.setText(fix.getfDescription());
            fixUsernaem.setText(fix.getoName());
            fixStatus.setText(fix.getfStatus());


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_get_fix, menu);
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
