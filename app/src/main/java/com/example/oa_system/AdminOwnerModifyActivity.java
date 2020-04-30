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
import android.widget.TextView;

import com.entity.Owner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;


public class AdminOwnerModifyActivity extends Activity {

    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String doModifyUrl = UrlConnectionUtil.IP + "/oaServer/admin/doModify";
    private EditText modifyPassword;
    private EditText modifyIdcard;
    private EditText modifyMobile;
    private Button btnDoModify;
    private EditText modifyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_owner_modify);
        initView();
        initListener();
        Intent intent = getIntent();
        String result = intent.getStringExtra("owner");
        Owner owner = new Gson().fromJson(result, Owner.class);

        modifyName.setText(owner.getoName());
        modifyPassword.setText(owner.getoPassword());
        modifyIdcard.setText(owner.getoIdcard());
        modifyMobile.setText(owner.getoMobile());

    }

    private void initListener() {

        btnDoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = modifyName.getText().toString();
                String password = modifyPassword.getText().toString();
                String idcard = modifyIdcard.getText().toString();
                String mobile = modifyMobile.getText().toString();

                String telRegex = "[1][3578]\\d{9}";

                if(TextUtils.isEmpty(username)){
                    modifyName.setError("用户名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    modifyPassword.setError("密码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(idcard)){
                    modifyIdcard.setError("身份证不能为空");
                    return;
                }else{
                    if(idcard.length() != 18){
                        modifyIdcard.setError("请正确填写18位身份证");
                        return;
                    }
                }
                if(TextUtils.isEmpty(mobile)){
                    modifyMobile.setError("手机号不能为空");
                    return;
                }else{
                    if(!mobile.matches(telRegex)){
                        modifyMobile.setError("请填写合法手机号");
                        return;
                    }
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("oName", username);
                jsonObject.addProperty("oPassword", password);
                jsonObject.addProperty("oMobile", mobile);
                jsonObject.addProperty("oIdcard", idcard);

                new ModifyRequest().execute(doModifyUrl,jsonObject.toString());
            }
        });
    }

    private void initView() {
        modifyName = (EditText) findViewById(R.id.modify_username);
        modifyPassword = (EditText) findViewById(R.id.modify_password);
        modifyIdcard = (EditText) findViewById(R.id.modify_idcard);
        modifyMobile = (EditText) findViewById(R.id.modify_mobile);

        btnDoModify = (Button) findViewById(R.id.do_modify);
    }

    class ModifyRequest extends AsyncTask<String, Integer ,String> {

        @Override
        protected String doInBackground(String... params) {
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);
            Intent intent = new Intent(AdminOwnerModifyActivity.this, AdminIndexActivity.class);
            startActivity(intent);
            AdminOwnerModifyActivity.this.finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_owner_modify, menu);
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
