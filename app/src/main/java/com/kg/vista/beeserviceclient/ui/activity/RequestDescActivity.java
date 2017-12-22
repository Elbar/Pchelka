package com.kg.vista.beeserviceclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 7/7/17.
 */

public class RequestDescActivity extends AbstractActivity {
    int id;
    String subcategory;
    String desc;
    String price;
    String address;
    String phone;

    @BindView(R.id.price)
    TextView mRequestPrice;
    @BindView(R.id.address)
    TextView mRequestAddress;
    @BindView(R.id.phone)
    TextView mRequestPhone;
    @BindView(R.id.description_tv)
    TextView mRequestDesc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_desc);
        ButterKnife.bind(this);

        try {
            Intent i = getIntent();
            id = i.getIntExtra("id", 0);
            subcategory = i.getStringExtra("subcategory");
            desc = i.getStringExtra("desc");
            price = i.getStringExtra("price");
            address = i.getStringExtra("address");
            phone = i.getStringExtra("phone");

            mRequestDesc.setText(desc);
            mRequestPrice.setText(price);
            mRequestAddress.setText(address);
            mRequestPhone.setText(phone);


        } catch (Exception e) {
            e.printStackTrace();
        }

        initActionBar();

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Заявка #" + id);
            ab.setDisplayHomeAsUpEnabled(true);

        }
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
}
