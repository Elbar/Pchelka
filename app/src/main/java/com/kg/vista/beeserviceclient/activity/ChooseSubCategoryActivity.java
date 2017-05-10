package com.kg.vista.beeserviceclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import com.kg.vista.beeserviceclient.R;

import java.util.Arrays;


public class ChooseSubCategoryActivity extends AbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subcategory);
        initActionBar();

        Intent i = getIntent();
        String category = i.getStringExtra("category");
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

        String[] categories = getResources().getStringArray(R.array.cleaning);

        Toast.makeText(this, Arrays.toString(categories), Toast.LENGTH_SHORT).show();

        //TODO Must make to finish sorting by categories arrays

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
            ab.setTitle(getResources().getString(R.string.choose_subcategory_toolbar_title));
        }
    }
}
