package com.kg.vista.beeserviceclient.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.activity.DescriptionActivity;
import com.kg.vista.beeserviceclient.model.NewRequestModel;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private ArrayList<NewRequestModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(v.getContext(), DescriptionActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
           // this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
        }
    }

    public SearchAdapter(ArrayList<NewRequestModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_search, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;

       // textViewName.setText(dataSet.get(listPosition).getUser_approx_cash());

        //textViewVersion.setText(String.valueOf(dataSet.get(listPosition).getPrice()));


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}