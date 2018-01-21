package com.kg.vista.pchelka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kg.vista.pchelka.R;
import com.kg.vista.pchelka.adapter.CustomListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseCategoryActivity extends AbstractActivity {
    @BindView(R.id.category_lv)
    ListView mCategoryListView;
    @BindView(R.id.myFab)
    FloatingActionButton mMyFAB;
    Integer[] imageArray = {
            R.drawable.group1,
            R.drawable.group2,
            R.drawable.group3,
            R.drawable.group4,
            R.drawable.group5,
            R.drawable.group6,
            R.drawable.group7,
            R.drawable.group8,
            R.drawable.group9,
            R.drawable.group10,
            R.drawable.group11,
            R.drawable.group12};

    String[] categoryArray = {
            "Мастер на вызов",
            "Красота и здоровье",
            "Клининг и помощь по хозяйству",
            "Установка и ремонт оборудования",
            "Образование и обучение",
            "Перевозки",
            "Служба доставки",
            "Компьютер и сети",
            "Юридическая помощь",
            "Техническая помощь",
            "Виртуальный помощник",
            "Отдых, Досуг и прочее"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        ButterKnife.bind(this);

        initActionBar();


        CustomListAdapter arrayAdapter = new CustomListAdapter(this, categoryArray, imageArray);

        mCategoryListView.setAdapter(arrayAdapter);

        mCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String selectedCategory = categoryArray[position];
                Intent i = new Intent(getApplicationContext(), ChooseSubCategoryActivity.class);
                i.putExtra("category", selectedCategory);

                startActivity(i);
            }
        });

        mMyFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChooseCategoryActivity.this, OrderTaxiActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_request:
                Intent second_intent = new Intent(this, MyRequestActivity.class);
                second_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(second_intent);
                return true;
            case R.id.about:
                Intent i = new Intent(this, AboutActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            ab.setCustomView(R.layout.category_actionbar);
        }
    }

}


