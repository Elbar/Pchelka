package com.kg.vista.beeserviceclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.fragment.MyRequestFragment;
import com.kg.vista.beeserviceclient.fragment.NewRequestFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vista on 08.02.2017.
 */

public class DrawerActivity extends AbstractActivity implements View.OnClickListener {


    public static final String KEY_DESC = "desc";
    public static final String KEY_CASH = "cash";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    private static final String REQUEST_URL = "http://176.126.167.34/get_application/";
    @BindView(R.id.user_new_request_desc)
    EditText mUserNewRequestDesc;
    @BindView(R.id.user_new_approx_cash)
    EditText mUserNewApproxCash;
    @BindView(R.id.user_request_address)
    EditText mUserRequestAddress;
    @BindView(R.id.user_new_request_phone_number)
    EditText mUserNewRequestPhone;
    @BindView(R.id.user_new_request_send_button)
    Button mUserNewRequestButton;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

        mUserNewRequestButton.setOnClickListener(this);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewRequestFragment(), "Новая заявка");
        adapter.addFragment(new MyRequestFragment(), "Мои заявки");
        viewPager.setAdapter(adapter);
    }

    private void sendData() {
        final String desc = mUserNewRequestDesc.getText().toString().trim();
        final String cash = mUserNewApproxCash.getText().toString().trim();
        final String address = mUserRequestAddress.getText().toString().trim();
        final String phone_number = mUserNewRequestPhone.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DrawerActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DrawerActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_DESC, desc);
                params.put(KEY_CASH, cash);
                params.put(KEY_ADDRESS, address);
                params.put(KEY_PHONE, phone_number);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == mUserNewRequestButton) {
            sendData();
        }
    }

    public void chooseCategory(View view) {

        Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }
}