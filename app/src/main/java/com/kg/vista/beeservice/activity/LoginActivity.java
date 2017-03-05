package com.kg.vista.beeservice.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;


import com.github.pinball83.maskededittext.MaskedEditText;
import com.kg.vista.beeservice.R;
import com.kg.vista.beeservice.fragment.NewRequestFragment;
import com.kg.vista.beeservice.manager.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AbstractActivity {

//    @BindView(R.id.login_button) Button mLoginButton;
//    @BindView(R.id.input_phone_number)
//    MaskedEditText mSendTelephoneNumber;
    SessionManager session;
    public String telephoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);


//        telephoneNumber = String.valueOf(mSendTelephoneNumber.getUnmaskedText());
//        telephoneNumber = telephoneNumber.replaceAll("\\D", "");

//        session = new SessionManager(getApplicationContext());
//
//        session.checkLogin();


//        if(session.isLoggedIn()){
//
//            Intent intent = new Intent(this, DrawerActivity.class);
//            startActivity(intent);
//
//        } else {
//
//
//            Intent intent = new Intent(this, LoginActivity.class);
//
//      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//
//            startActivity(intent);
//            finish();
//
//        }

    }


    public void checkAndSendTelephoneNumber(View view) {

        Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
        startActivity(intent);


    }


}


