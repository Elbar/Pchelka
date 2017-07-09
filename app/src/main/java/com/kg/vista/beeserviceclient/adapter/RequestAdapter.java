package com.kg.vista.beeserviceclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.activity.RequestDescActivity;
import com.kg.vista.beeserviceclient.model.Request;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by root on 7/7/17.
 */


    public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Request> data = Collections.emptyList();

        int currentPos = 0;
        private Context context;
        private LayoutInflater inflater;
        private CardView mCVDescription;

        public RequestAdapter(Context context, List<Request> data) {

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
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
                long time = format.parse(current.requestOrderTime).getTime();
                long now = System.currentTimeMillis();
                CharSequence ago =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
                myHolder.requestOrderTime.setText(ago);


            } catch (Exception e) {
                e.printStackTrace();
            }


            myHolder.mCVDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), RequestDescActivity.class);

                    intent.putExtra("id", current.requestId);
                    intent.putExtra("phone_number", current.requestPhoneNumber);
                    intent.putExtra("price", current.requestPrice);
                    intent.putExtra("desc", current.requestDescription);


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
            TextView requestOrderTime;

            CardView mCVDesc;

            public MyHolder(View itemView) {
                super(itemView);
                requestId = (TextView) itemView.findViewById(R.id.request_id);

                mCVDesc = (CardView) itemView.findViewById(R.id.request_cv);


            }


        }
    }