package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.Advertise;
import com.entity.Payment;
import com.example.oa_system.R;

import java.util.ArrayList;
import java.util.List;

public class AdvertiseAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<Advertise> advertiseList = new ArrayList<>();

    public AdvertiseAdapter(Context context, List<Advertise> advertises){
        this.context = context;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        advertiseList = advertises;
    }

    @Override
    public int getCount() {
        return advertiseList.size();
    }

    @Override
    public Object getItem(int position) {
        return advertiseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_advertise, null);

        TextView advertiseContent = (TextView) convertView.findViewById(R.id.advertise_content);

        advertiseContent.setText(advertiseList.get(position).getAeContent());

        return convertView;
    }
}