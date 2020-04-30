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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;


public class MainActivity extends Activity {

    private TextView etUserName;
    private TextView etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private RadioGroup rgId;
    private String id = null;
    private RadioButton owner;
    private RadioButton admin;
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String url = UrlConnectionUtil.IP + "/oaServer/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        try{
            Intent intent = getIntent();
            String registerMsg = intent.getStringExtra("registerMsg");
            Toast.makeText(MainActivity.this, registerMsg, Toast.LENGTH_LONG).show();
        }catch (Exception e){

        }

    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if(TextUtils.isEmpty(username)){
                    etUserName.setError("用户名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("密码不能为空");
                    return;
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("username", username);
                jsonObject.addProperty("password", password);

                switch (rgId.getCheckedRadioButtonId()){
                    case R.id.owner:
                        id = "owner";break;
                    case R.id.admin:
                        id = "admin";break;
                    case R.id.worker:
                        id = "worker";break;
                }
                jsonObject.addProperty("id", id);

                try{
                    new RequestNetworkDataTasks().execute(url, jsonObject.toString());
                }catch (Exception e){
                    Log.e("超时：","连接失败....");
                }

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Register.class);
                startActivity(intent1);
            }
        });
    }

    private void initView() {
        etUserName = (TextView) findViewById(R.id.username);
        etPassword = (TextView) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);

        rgId = (RadioGroup) findViewById(R.id.rgId);
        owner = (RadioButton) findViewById(R.id.owner);
        admin = (RadioButton) findViewById(R.id.admin);
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
            if(!result.equals("")){
                Toast.makeText(MainActivity.this, "登陆成功!",Toast.LENGTH_SHORT).show();
                if(id.equals("owner")){
                    Intent intent = new Intent(MainActivity.this, OwnerIndexActivity.class);
                    intent.putExtra("userInfo", result);
                    startActivity(intent);
                    MainActivity.this.finish();
                }else if (id.equals("admin")){
                    Intent intent = new Intent(MainActivity.this, AdminIndexActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }else {
                    Intent intent = new Intent(MainActivity.this, WorkerIndexActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }else{
                Toast.makeText(MainActivity.this, "密码或用户名不正确，请重新输入。",Toast.LENGTH_LONG).show();
            }
            System.out.println(result);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
