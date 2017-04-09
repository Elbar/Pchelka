package com.kg.vista.beeserviceclient.activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;


import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.manager.SessionManager;


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


