package com.kg.vista.beeserviceclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kg.vista.beeserviceclient.R;

import butterknife.BindView;

/**
 * Created by Vista on 10.02.2017.
 */

public class ChooseCategoryActivity extends AppCompatActivity {

    @BindView(R.id.category_lv)
    ListView mCategoryLV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);




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
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getResources().getString(R.string.choose_category_toolbar_title));
        }
    }

    public void chooseSubCategory(View view) {
        Intent intent = new Intent(this, ChooseSubCategoryActivity.class);
        startActivity(intent);

    }

}
