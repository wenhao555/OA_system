package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.Owner;
import com.example.oa_system.R;

import java.util.ArrayList;
import java.util.List;

public class OwnerOnPayAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<Owner> ownerList = new ArrayList<>();

    public OwnerOnPayAdapter(Context context, List<Owner> owners){
        this.context = context;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ownerList = owners;
    }

    @Override
    public int getCount() {
        return ownerList.size();
    }

    @Override
    public Object getItem(int position) {
        return ownerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_owner_onpayment, null);

        TextView nameText = (TextView) convertView.findViewById(R.id.owner_name_text);
        TextView mobileText = (TextView) convertView.findViewById(R.id.owner_mobile_text);

        nameText.setText(ownerList.get(position).getoName());
        mobileText.setText(ownerList.get(position).getoMobile());

        return convertView;
    }
}