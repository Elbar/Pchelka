package com.kg.vista.beeservice.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kg.vista.beeservice.R;

/**
 * Created by Vista on 05.02.2017.
 */

public class MyRequestFragment extends Fragment{

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;


    public MyRequestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_request, container, false);


    }






}