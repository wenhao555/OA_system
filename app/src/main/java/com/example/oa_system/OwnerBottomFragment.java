package com.example.oa_system;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OwnerBottomFragment extends Fragment {


    private View view;
    private TextView viewIndex;
    private TextView viewComplain;
    private TextView viewFix;
    private TextView viewPayment;
    private TextView viewLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_owner_bottom, container, false);
        viewIndex = (TextView) view.findViewById(R.id.owner_index);
        viewComplain = (TextView) view.findViewById(R.id.owner_complain);
        viewFix = (TextView) view.findViewById(R.id.owner_fix);
        viewPayment = (TextView) view.findViewById(R.id.owner_payment);
        viewLogout = (TextView) view.findViewById(R.id.owner_logout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OwnerIndexActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OwnerComplainActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OwnerFixActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        viewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OwnerPaymentActivity.class);
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
