package com.kg.vista.beeservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kg.vista.beeservice.NewRequestData;
import com.kg.vista.beeservice.R;
import com.kg.vista.beeservice.adapter.SearchAdapter;
import com.kg.vista.beeservice.model.NewRequestModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Vista on 09.02.2017.
 */

public class SearchExecutorActivity extends AbstractActivity {

    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mRecyclerView;
    private static ArrayList<NewRequestModel> data;

    @BindView(R.id.user_search_request_desc)
    TextView mUserSearchRequestDesc;

    @BindView(R.id.user_search_request_approx_cash)
    TextView mUserSearchRequestApproxCash;

    @BindView(R.id.user_search_total_price)
    TextView mUserSearchTotalPrice;




    static View.OnClickListener myOnClickListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);


//        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
//
//        //mRecyclerView.setHasFixedSize(true);
//
//        mLayoutManager = new LinearLayoutManager(this);
//       mRecyclerView.setLayoutManager(mLayoutManager);
//


//        data = new ArrayList<NewRequestModel>();
//
//        Intent intent = getIntent();
//        String user_request_desc = intent.getStringExtra("user_request_desc");
//        String user_approx_cash = intent.getStringExtra("user_approx_cash");
//        String user_request_address = intent.getStringExtra("user_request_address");
//        String user_request_phone_number = intent.getStringExtra("user_request_phone_number");
//
//
//
//
//
//        for (int i = 0; i < NewRequestData.descriptionArray.length; i++) {
//            data.add(new NewRequestModel(
//                    user_request_desc,
//                    user_approx_cash,
//                    user_request_address,
//                    user_request_phone_number
//            ));
//        }
//
//        mAdapter = new SearchAdapter(data);
//        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        String user_request_desc = intent.getStringExtra("user_request_desc");
        String user_approx_cash = intent.getStringExtra("user_approx_cash");

        int leave_the_city = 300;
        int user_approx_value = Integer.parseInt(user_approx_cash);

        int score = user_approx_value + leave_the_city;

        mUserSearchRequestDesc.setText(user_request_desc);
        mUserSearchRequestApproxCash.setText(user_approx_cash);
        mUserSearchTotalPrice.setText(String.valueOf(score));

        initActionBar();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getResources().getString(R.string.search_executor_toolbar_title));
        }

    }

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
        }

    }
}
