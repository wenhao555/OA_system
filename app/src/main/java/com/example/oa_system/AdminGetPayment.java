package com.example.oa_system;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;

import com.adapter.OwnerAdapter;
import com.adapter.OwnerOnPayAdapter;
import com.adapter.PaymentAdapter;
import com.entity.Owner;
import com.entity.Payment;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminGetPayment extends Activity {

    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String getPaymentUrl = UrlConnectionUtil.IP + "/oaServer/admin/getPaymentUrl";
    private Payment[] paymentArray;
    private List<Payment> paymentList;
    private ArrayList<Payment> payments;
    private PaymentAdapter paymentAdapter;
    private ListView paymentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_get_payment);

        initView();
        initListener();

        String oName = getIntent().getStringExtra("oName");

        new RequestNetworkDataTasks().execute(getPaymentUrl, oName);
    }

    private void initListener() {

    }

    private void initView() {
        paymentListView = (ListView) findViewById(R.id.payment_listView);
    }

    class RequestNetworkDataTasks extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String[] params) {
            return urlConnectionUtil.sendRequest(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try{
                paymentArray = new Gson().fromJson(result, Payment[].class);
                paymentList = Arrays.asList(paymentArray);
                payments = new ArrayList<>(paymentList);

                paymentAdapter = new PaymentAdapter(AdminGetPayment.this, payments);
                paymentListView.setAdapter(paymentAdapter);
            }catch (Exception e){
                System.out.println("列表为空，无法渲染");
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_get_payment, menu);
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
