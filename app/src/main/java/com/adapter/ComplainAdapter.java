package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.Complain;
import com.entity.Fix;
import com.example.oa_system.R;

import java.util.ArrayList;
import java.util.List;

public class ComplainAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<Complain> complainList = new ArrayList<>();

    public ComplainAdapter(Context context, List<Complain> complainLists){
        this.context = context;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        complainList = complainLists;
    }

    @Override
    public int getCount() {
        return complainList.size();
    }

    @Override
    public Object getItem(int position) {
        return complainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_complain, null);

        TextView contentText = (TextView) convertView.findViewById(R.id.complain_content_text);
        TextView feedBackText = (TextView) convertView.findViewById(R.id.complain_feedback_text);
        TextView nameText = (TextView) convertView.findViewById(R.id.complain_username_text);
        String feedback = complainList.get(position).getcFeedback();

        if(feedback.equals("")){
            feedback = "未反馈";
        }
        feedBackText.setText(feedback);
        contentText.setText(complainList.get(position).getcContent());

        nameText.setText(complainList.get(position).getoName());

        return convertView;
    }
    public void refreshData(List<Complain> complains){
        complainList = complains;
        notifyDataSetChanged();
    }
}