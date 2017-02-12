package com.kg.vista.beeservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

import com.kg.vista.beeservice.R;
import com.kg.vista.beeservice.fragment.MyRequestFragment;
import com.kg.vista.beeservice.fragment.NewRequestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vista on 08.02.2017.
 */

public class DrawerActivity extends AbstractActivity  {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @BindView(R.id.get_phone_number) EditText mReceiveTelephoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        receiveTelephoneNumber();


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewRequestFragment(), "Новая заявка");
        adapter.addFragment(new MyRequestFragment(), "Мои заявки");
        viewPager.setAdapter(adapter);
    }

    public void chooseCategory(View view) {

        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        startActivity(intent);


    }

    public void receiveTelephoneNumber() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            String tel_number  = (String) bundle.get("number");
            mReceiveTelephoneNumber.setText(tel_number);
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