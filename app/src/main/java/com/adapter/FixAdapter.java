package com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.Fix;
import com.entity.Payment;
import com.example.oa_system.R;

import java.util.ArrayList;
import java.util.List;

public class FixAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<Fix> fixList = new ArrayList<>();

    public FixAdapter(Context context, List<Fix> fixs){
        this.context = context;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        fixList = fixs;
    }

    @Override
    public int getCount() {
        return fixList.size();
    }

    @Override
    public Object getItem(int position) {
        return fixList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_fix, null);

        TextView projectText = (TextView) convertView.findViewById(R.id.fix_name_text);
        TextView statusText = (TextView) convertView.findViewById(R.id.fix_status_text);
        TextView nameText = (TextView) convertView.findViewById(R.id.fix_username_text);

        String status = fixList.get(position).getfStatus();

        projectText.setText(fixList.get(position).getfName());
        statusText.setText(fixList.get(position).getfStatus());
        nameText.setText(fixList.get(position).getoName());

        if(status.equals("已完成")){
            statusText.setTextColor(Color.GREEN);
        }else if(status.equals("待处理")){
            statusText.setTextColor(Color.RED);
        }
        return convertView;
    }
    public void refreshData(List<Fix> fixs){
        fixList = fixs;
        notifyDataSetChanged();
    }
}