package com.example.oa_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.adapter.AdvertiseAdapter;
import com.adapter.PaymentAdapter;
import com.entity.Advertise;
import com.entity.Owner;
import com.entity.Payment;
import com.google.gson.Gson;
import com.utils.UrlConnectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OwnerPaymentActivity extends Activity {

    private UrlConnectionUtil urlConnectionUtil = new UrlConnectionUtil();
    private String getPaymentUrl = UrlConnectionUtil.IP + "/oaServer/owner/getPaymentUrl";

    private String doPayUrl = UrlConnectionUtil.IP + "/oaServer/admin/doPayUrl";
    private Payment[] paymentArray;
    private List<Payment> paymentList;
    private ArrayList<Payment> payments;
    private PaymentAdapter paymentAdapter;
    private ListView paymentListView;
    private Payment payment;
    private String oName = "oo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_payment);
        initView();
        initListener();
        new RequestNetworkDataTasks().execute(getPaymentUrl, oName);
        //Intent intent = getIntent();


    }

    private void initListener() {
        paymentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                payment = payments.get(position);
                if(payment.getpRealCost()!=0){
                    Toast.makeText(OwnerPaymentActivity.this, "已缴费，无需重复缴费", Toast.LENGTH_SHORT).show();
                    return true;
                }
                AlertDialog.Builder confirm = new AlertDialog.Builder(OwnerPaymentActivity.this);
                confirm.setTitle("提示");
                confirm.setMessage("是否确认缴费？");
                confirm.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Double realCost = payment.getpCost();
                        if(payment.getpStatus().trim().equals("已逾期")){
                            realCost *= 1.2;
                        }
                        payment.setpRealCost(realCost);
                        payment.setpStatus("已缴费");
                        new doPay().execute(doPayUrl, payment.toString());
                        Toast.makeText(OwnerPaymentActivity.this, "缴费成功", Toast.LENGTH_SHORT).show();
                    }
                });
                confirm.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OwnerPaymentActivity.this, "未缴费", Toast.LENGTH_SHORT).show();
                    }
                });
                confirm.setCancelable(false);
                AlertDialog dialog = confirm.create();
                dialog.show();
                return true;
            }

        });
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
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           try{
               paymentArray = new Gson().fromJson(result, Payment[].class);
               paymentList = Arrays.asList(paymentArray);
               payments = new ArrayList<>(paymentList);

               oName = payments.get(0).getoName();
               paymentAdapter = new PaymentAdapter(OwnerPaymentActivity.this, payments);
               paymentListView.setAdapter(paymentAdapter);
           }catch (Exception e){
                oName = result.trim();
            }

        }
    }

    class doPay extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return urlConnectionUtil.sendRequest(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);

            paymentArray = new Gson().fromJson(result, Payment[].class);
            paymentList = Arrays.asList(paymentArray);
            payments = new ArrayList<>(paymentList);

            paymentAdapter.refreshData(payments);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owner_payment, menu);
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
