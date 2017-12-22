package com.kg.vista.beeserviceclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.ui.activity.RequestDescActivity;
import com.kg.vista.beeserviceclient.model.Request;

import java.util.Collections;
import java.util.List;

/**
 * Created by root on 7/9/17.
 */
public class AdapterRequest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Request> data = Collections.emptyList();

    int currentPos = 0;
    private Context context;
    private LayoutInflater inflater;
    private CardView mCVDescription;

    public AdapterRequest(Context context, List<Request> data) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = inflater.inflate(R.layout.container_request, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        final MyHolder myHolder = (MyHolder) holder;

        final Request current = data.get(position);


        myHolder.requestId.setText("Заявка: #" + current.requestId);
        myHolder.subcategory.setText(current.requestSubcategory);


        myHolder.mCVDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RequestDescActivity.class);

                intent.putExtra("id", current.requestId);
                intent.putExtra("subcategory", current.requestSubcategory);
                intent.putExtra("desc", current.requestDescription);
                intent.putExtra("price", current.requestPrice);
                intent.putExtra("address", current.requestAddress);
                intent.putExtra("phone", current.requestPhoneNumber);
                v.getContext().startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView requestId;
        TextView subcategory;

        CardView mCVDesc;

        public MyHolder(View itemView) {
            super(itemView);
            requestId = (TextView) itemView.findViewById(R.id.request_id);
            subcategory = (TextView) itemView.findViewById(R.id.subcategory_tv);

            mCVDesc = (CardView) itemView.findViewById(R.id.request_cv);


        }
    }
}