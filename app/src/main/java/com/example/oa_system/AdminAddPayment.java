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

import com.adapter.OwnerOnPayAdapter;
import com.entity.Owner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class AdminAddPayment extends Activity {
    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String addPaymentUrl = UrlConnectionUtil.IP + "/oaServer/admin/addPaymentUrl";
    private EditText addPaymentUsername;
    private EditText addPaymentCost;
    private EditText addPaymentDate;
    private Button btnDoAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_payment);
        initView();
        initListener();
        String oName = getIntent().getStringExtra("oName");
        addPaymentUsername.setText(oName);

    }

    private void initListener() {
        btnDoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentUsername = addPaymentUsername.getText().toString();
                String paymentCost = addPaymentCost.getText().toString();
                String paymentDate = addPaymentDate.getText().toString();

                if(TextUtils.isEmpty(paymentCost)){
                    addPaymentCost.setError("物业费用不能为空");
                    return;
                }
                if(TextUtils.isEmpty(paymentDate)){
                    addPaymentDate.setError("缴费日期不能为空");
                    return;
                }else{

                    Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
                    if(!p.matcher(paymentDate).matches()){
                        addPaymentDate.setError("注意日期格式");
                        return;
                    }
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("pCost", paymentCost);
                jsonObject.addProperty("pDate", paymentDate);
                jsonObject.addProperty("pStatus", "未缴费");
                jsonObject.addProperty("oName", paymentUsername);

                new RequestNetworkDataTasks().execute(addPaymentUrl, jsonObject.toString());
            }
        });
    }

    private void initView() {
        addPaymentUsername = (EditText) findViewById(R.id.add_payment_username);
        addPaymentCost = (EditText) findViewById(R.id.add_payment_cost);
        addPaymentDate = (EditText) findViewById(R.id.add_payment_date);

        btnDoAdd = (Button)findViewById(R.id.do_add_payment);
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
            System.out.println(result);

            Intent intent = new Intent(AdminAddPayment.this, AdminPaymentActivity.class);
            startActivity(intent);
            AdminAddPayment.this.finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_add_payment, menu);
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
