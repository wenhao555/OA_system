package com.example.oa_system;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.entity.Owner;

public class BottomFragment extends Fragment{

    private View view;
    private TextView viewOwner;
    private TextView viewAdvertise;
    private TextView viewComplain;
    private TextView viewFix;
    private TextView viewPayment;
    private TextView viewLogout;
    private String oName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom, container, false);
        viewOwner = (TextView) view.findViewById(R.id.view_owner);
        viewAdvertise = (TextView) view.findViewById(R.id.view_advertise);
        viewComplain = (TextView) view.findViewById(R.id.view_complain);
        viewFix = (TextView) view.findViewById(R.id.view_fix);
        viewPayment = (TextView) view.findViewById(R.id.view_payment);
        viewLogout = (TextView) view.findViewById(R.id.view_logout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminIndexActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewAdvertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminAdvertiseActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminComplainActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminFixActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminPaymentActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}