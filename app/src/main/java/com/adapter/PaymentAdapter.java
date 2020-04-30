package com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.Owner;
import com.entity.Payment;
import com.example.oa_system.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<Payment> paymentList = new ArrayList<>();

    public PaymentAdapter(Context context, List<Payment> payments){
        this.context = context;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        paymentList = payments;
    }

    @Override
    public int getCount() {
        return paymentList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_payment, null);

        TextView dateText = (TextView) convertView.findViewById(R.id.payment_date_text);
        TextView statusText = (TextView) convertView.findViewById(R.id.payment_status_text);
        TextView nameText = (TextView) convertView.findViewById(R.id.payment_username_text);
        TextView costText = (TextView) convertView.findViewById(R.id.payment_cost_text);
        String status = paymentList.get(position).getpStatus();

        dateText.setText(paymentList.get(position).getpDate());
        statusText.setText(paymentList.get(position).getpStatus());
        nameText.setText(paymentList.get(position).getpRealCost().toString());
        costText.setText(paymentList.get(position).getpCost().toString());

        if(status.equals("已逾期")){
            statusText.setTextColor(Color.RED);
        }else if(status.equals("已缴费")){
            statusText.setTextColor(Color.GREEN);
        }
        return convertView;
    }
    public void refreshData(List<Payment> payments){
        paymentList = payments;
        notifyDataSetChanged();
    }
}