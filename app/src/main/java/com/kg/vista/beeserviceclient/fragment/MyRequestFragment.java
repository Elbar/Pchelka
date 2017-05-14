package com.kg.vista.beeserviceclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kg.vista.beeserviceclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyRequestFragment extends Fragment {

    @BindView(R.id.cardList)
    RecyclerView mRecyclerView;


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


        //TODO make cards in myrequestfragment


        ButterKnife.bind(this, view);


        return view;
    }


}