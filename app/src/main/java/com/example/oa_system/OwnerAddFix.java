package com.example.oa_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.FixAdapter;
import com.entity.Fix;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;


public class OwnerAddFix extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String addFixUrl = UrlConnectionUtil.IP + "/oaServer/owner/addFixUrl";
    private Button doAddFix;
    private EditText fixName;
    private EditText fixUsernaem;
    private EditText fixContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_fix);

        initView();
        initListener();
        String oName = getIntent().getStringExtra("oName");
        fixUsernaem.setText(oName);
    }

    private void initListener() {
        doAddFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                jsonObject.addProperty("oName", oName);
                jsonObject.addProperty("fName", fName);
                jsonObject.addProperty("fDescription", fContent);
                jsonObject.addProperty("fStatus", "待处理");

                new RequestNetworkDataTasks().execute(addFixUrl,jsonObject.toString());
            }
        });
    }


    private void initView() {

        doAddFix = (Button) findViewById(R.id.do_add_fix);
        fixName = (EditText) findViewById(R.id.fix_name);
        fixUsernaem = (EditText) findViewById(R.id.fix_username);
        fixContent = (EditText)findViewById(R.id.fix_content);
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
            Intent intent = new Intent(OwnerAddFix.this, OwnerFixActivity.class);
            startActivity(intent);
            OwnerAddFix.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owner_add_fix, menu);
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
