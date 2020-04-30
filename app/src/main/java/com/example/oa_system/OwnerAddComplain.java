package com.example.oa_system;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;


public class OwnerAddComplain extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String addComplainUrl = UrlConnectionUtil.IP + "/oaServer/admin/addComplainUrl";
    private Button doAddComplain;
    private EditText complainContent;
    private EditText complainUsername;
    private EditText complainFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_complain);

        String oName = getIntent().getStringExtra("oName");
        initView();
        complainUsername.setText(oName);
        initListener();

    }

    private void initListener() {
        doAddComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oName = complainUsername.getText().toString();
                String cContent = complainContent.getText().toString();

                if(TextUtils.isEmpty(cContent)){
                    complainContent.setError("项目描述不能为空");
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("oName", oName);
                jsonObject.addProperty("cContent", cContent);
                jsonObject.addProperty("cFeedback", "");

                new RequestNetworkDataTasks().execute(addComplainUrl,jsonObject.toString());
            }
        });
    }


    private void initView() {

        doAddComplain = (Button) findViewById(R.id.do_add_complain);
        complainContent = (EditText) findViewById(R.id.complain_content);
        complainUsername = (EditText) findViewById(R.id.complain_username);
        complainFeedback = (EditText)findViewById(R.id.complain_feedback);
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
            Intent intent = new Intent(OwnerAddComplain.this, OwnerComplainActivity.class);
            startActivity(intent);
            OwnerAddComplain.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owner_add_complain, menu);
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
