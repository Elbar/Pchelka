package com.kg.vista.beeservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kg.vista.beeservice.R;

import butterknife.BindView;

import static android.support.design.widget.Snackbar.LENGTH_LONG;


/**
 * Created by Vista on 11.02.2017.
 */

public class MiddleWareActivity extends AbstractActivity {


    @BindView(R.id.middleware_name)
    EditText mUserName;
    @BindView(R.id.verification_code)
    EditText mVerificationCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_middleware);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void checkCredentials(View view) {
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);

    }

}