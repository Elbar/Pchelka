package com.kg.vista.beeserviceclient.activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;


import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.manager.SessionManager;


public class LoginActivity extends AbstractActivity {

    SessionManager session;
    public String telephoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void checkAndSendTelephoneNumber(View view) {

        Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}


