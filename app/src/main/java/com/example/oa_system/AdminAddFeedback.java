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

import com.entity.Complain;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;


public class AdminAddFeedback extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String addFeedbackUrl = UrlConnectionUtil.IP + "/oaServer/admin/addFeedbackUrl";
    private String getOneComplainUrl = UrlConnectionUtil.IP + "/oaServer/admin/getOneComplainUrl";
    private Button doAddComplain;
    private EditText complainContent;
    private EditText complainUsername;
    private EditText complainFeedback;
    private Complain complain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_feedback);
        initView();
        initListener();

        new GetOneComplain().execute(getOneComplainUrl, getIntent().getStringExtra("cId"));

    }

    private void initListener() {
        doAddComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cFeedback = complainFeedback.getText().toString();
                String oName = complainUsername.getText().toString();
                String cContent = complainContent.getText().toString();
                String cId = getIntent().getStringExtra("cId");

                if(TextUtils.isEmpty(cContent)){
                    complainContent.setError("项目描述不能为空");
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("cId", cId);
                jsonObject.addProperty("oName", oName);
                jsonObject.addProperty("cContent", cContent);
                jsonObject.addProperty("cFeedback", cFeedback);

                new RequestNetworkDataTasks().execute(addFeedbackUrl,jsonObject.toString());
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
            Intent intent = new Intent(AdminAddFeedback.this, AdminComplainActivity.class);
            startActivity(intent);
            AdminAddFeedback.this.finish();
        }
    }

    class GetOneComplain extends AsyncTask<String, Integer, String> {
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

            complain = new Gson().fromJson(result, Complain.class);

            complainContent.setText(complain.getcContent());
            complainUsername.setText(complain.getoName());
            complainFeedback.setText(complain.getcFeedback());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_add_feedback, menu);
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
