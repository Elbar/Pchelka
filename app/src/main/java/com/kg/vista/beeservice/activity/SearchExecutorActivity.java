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
import android.widget.Toast;

import com.kg.vista.beeservice.NewRequestData;
import com.kg.vista.beeservice.R;
import com.kg.vista.beeservice.adapter.SearchAdapter;
import com.kg.vista.beeservice.model.NewRequestModel;

import java.util.ArrayList;


/**
 * Created by Vista on 09.02.2017.
 */

public class SearchExecutorActivity extends AbstractActivity {

    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mRecyclerView;
    private static ArrayList<NewRequestModel> data;
    static View.OnClickListener myOnClickListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myOnClickListener = new MyOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        data = new ArrayList<NewRequestModel>();


        for (int i = 0; i < NewRequestData.descriptionArray.length; i++) {
            data.add(new NewRequestModel(
                    NewRequestData.descriptionArray[i],
                    NewRequestData.priceArray[i],
                    NewRequestData.id_[i]
            ));
        }

        mAdapter = new SearchAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

        initActionBar();
        Intent intent = getIntent();
        String id = intent.getStringExtra("user_request_desc");
        Toast.makeText(SearchExecutorActivity.this, id, Toast.LENGTH_LONG).show();


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
