package com.kg.vista.beeserviceclient.fragment;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.kg.vista.beeserviceclient.R;

import butterknife.ButterKnife;

import static com.kg.vista.beeserviceclient.fragment.NewRequestFragment.MyPREFERENCES;


public class MyRequestFragment extends Fragment {



    public MyRequestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_request, container, false);

        ButterKnife.bind(this, view);




        return view;


    }



}