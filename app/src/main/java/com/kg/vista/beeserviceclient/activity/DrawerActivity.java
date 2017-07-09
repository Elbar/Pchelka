package com.kg.vista.beeserviceclient.activity;


import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.fragment.MyRequestFragment;
import com.kg.vista.beeserviceclient.fragment.NewRequestFragment;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;
import com.kg.vista.beeserviceclient.network.NetworkState;


import java.util.ArrayList;

import java.util.List;

import butterknife.ButterKnife;

import static java.security.AccessController.getContext;


public class DrawerActivity extends AbstractActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewRequestFragment(), "Новая заявка");
//        adapter.addFragment(new MyRequestFragment(), "Мои заявки");
        viewPager.setAdapter(adapter);
    }


    public void chooseCategory(View view) {

        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        AlertDialogManager alertDialogManager = new AlertDialogManager();

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {

            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            startActivity(intent);
        }else {
            alertDialogManager.showAlertDialog(this, "Ошибка", "Пожалуйста проверьте ваше соединение с интернетом", false);
        }

    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);

        }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DrawerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_user_settings:
                Intent second_intent = new Intent(this, MyRequestActivity.class);
                second_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(second_intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Toast.makeText(DrawerActivity.this, "first", Toast.LENGTH_SHORT).show();
                    return new NewRequestFragment();
                case 1:
                    Toast.makeText(DrawerActivity.this, "second", Toast.LENGTH_SHORT).show();

                    return new MyRequestFragment();
            }

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