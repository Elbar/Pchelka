package com.kg.vista.beeservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kg.vista.beeservice.R;
import com.kg.vista.beeservice.manager.AlertDialogManager;
import com.kg.vista.beeservice.manager.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AbstractActivity {

    @BindView(R.id.login_button) Button mLoginButton;
    @BindView(R.id.input_phone_number) EditText mSendTelephoneNumber;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        if(session.isLoggedIn()){

            Intent intent = new Intent(this, DrawerActivity.class);
            startActivity(intent);

        }







    }

    public void checkTelephoneNumber(View view) {

        Intent intent = new Intent(this, MiddleWareActivity.class);
        startActivity(intent);

    }


}





