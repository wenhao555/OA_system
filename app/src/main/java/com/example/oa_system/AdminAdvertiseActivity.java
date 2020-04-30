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
import android.widget.Toast;

import com.utils.UrlConnectionUtil;


public class AdminAdvertiseActivity extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String insertAdvertiseUrl = UrlConnectionUtil.IP + "/oaServer/admin/insertAdvertiseUrl";
    private Button btnDoUpload;
    private EditText advertiseContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_advertise);

        initView();
        initListener();
    }

    private void initListener() {

        btnDoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ae = advertiseContent.getText().toString();
                if(TextUtils.isEmpty(ae)){
                    advertiseContent.setError("上传内容不能为空");
                    return;
                }
                new UploadAdvertise().execute(insertAdvertiseUrl, ae);
                advertiseContent.setText("");
            }
        });
    }

    private void initView() {
        advertiseContent = (EditText) findViewById(R.id.advertise_content);
        btnDoUpload = (Button) findViewById(R.id.do_upload);
    }

    class UploadAdvertise extends AsyncTask<String, Integer ,String> {

        @Override
        protected String doInBackground(String... params) {
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);
            if(!result.equals("1")){
                Toast.makeText(AdminAdvertiseActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AdminAdvertiseActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_advertise, menu);
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
