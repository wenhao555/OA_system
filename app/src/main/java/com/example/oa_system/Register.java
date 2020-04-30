package com.example.oa_system;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;


public class Register extends Activity {

    private EditText registerUsername;
    private EditText registerPassword;
    private RadioGroup registerRgId;
    private EditText registerIdcard;
    private EditText registerMobile;
    private Button doRegister;
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String url = UrlConnectionUtil.IP + "/oaServer/register";
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();

    }

    private void initView() {
        registerUsername = (EditText) findViewById(R.id.register_username);
        registerPassword = (EditText) findViewById(R.id.register_password);
        registerRgId = (RadioGroup) findViewById(R.id.register_rgId);
        registerIdcard = (EditText) findViewById(R.id.register_idcard);
        registerMobile = (EditText) findViewById(R.id.register_mobile);

        doRegister = (Button) findViewById(R.id.do_register);

    }

    private void initListener() {
        doRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                String idcard = registerIdcard.getText().toString();
                String mobile = registerMobile.getText().toString();

                String telRegex = "[1][3578]\\d{9}";

                if(TextUtils.isEmpty(username)){
                    registerUsername.setError("用户名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    registerPassword.setError("密码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(idcard)){
                    registerIdcard.setError("身份证不能为空");
                    return;
                }else{
                    if(idcard.length() != 18){
                        registerIdcard.setError("请正确填写18位身份证");
                        return;
                    }
                }
                if(TextUtils.isEmpty(mobile)){
                    registerMobile.setError("手机号不能为空");
                    return;
                }else{
                    if(!mobile.matches(telRegex)){
                        registerMobile.setError("请填写合法手机号");
                        return;
                    }
                }
                switch (registerRgId.getCheckedRadioButtonId()){
                    case R.id.register_owner:
                        id = "owner";break;
                    case R.id.register_admin:
                        id = "admin";break;
                    case R.id.register_worker:
                        id = "worker";break;
                }
                String p = "";
                if(id.equals("owner")){
                    p = "o";
                }else if (id.equals("admin")){
                    p = "a";
                }else {
                    p = "w";
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(p+"Name", username);
                jsonObject.addProperty(p+"Password", password);
                jsonObject.addProperty(p+"Mobile", mobile);
                jsonObject.addProperty(p+"Idcard", idcard);

                System.out.println(jsonObject.toString());

                new RequestNetworkDataTasks().execute(url, jsonObject.toString());
            }
        });
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
            String registerMsg = "";
            if(!result.equals("")){
                registerMsg = "注册成功";
            }else{
                registerMsg = "用户名重复，注册失败，请重新注册";
            }
            Intent intent = new Intent(Register.this, MainActivity.class);
            intent.putExtra("registerMsg", registerMsg);
            startActivity(intent);
            System.out.println(result);

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
