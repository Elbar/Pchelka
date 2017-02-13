package com.kg.vista.beeservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kg.vista.beeservice.R;
import com.kg.vista.beeservice.manager.AlertDialogManager;
import com.kg.vista.beeservice.manager.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.Snackbar.LENGTH_LONG;


/**
 * Created by Vista on 11.02.2017.
 */

public class MiddleWareActivity extends AbstractActivity {


    @BindView(R.id.middleware_name)
    EditText mUserName;
    @BindView(R.id.verification_code)
    EditText mVerificationCode;

    @BindView(R.id.middleware_button)
    Button mSendButtonFromMiddleware;


    AlertDialogManager alert = new AlertDialogManager();

    SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_middleware);
        session = new SessionManager(getApplicationContext());


        ButterKnife.bind(this);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        //session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();


        final String name = user.get(SessionManager.KEY_NAME);
        final String verification_code = user.get(SessionManager.KEY_VERIFICATION_CODE);

        Toast.makeText(getApplicationContext(), "User " + name + "Password" + verification_code, Toast.LENGTH_SHORT).show();
        mSendButtonFromMiddleware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String username = mUserName.getText().toString();
                String user_pass = mVerificationCode.getText().toString();


                if (username.trim().length() > 0 && user_pass.trim().length() > 0) {
                    if (user_pass.equals(verification_code)) {

                        session.createLoginSession(username, "e1l2b3a4r5");

                        Intent i = new Intent(MiddleWareActivity.this, DrawerActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        alert.showAlertDialog(MiddleWareActivity.this, "Login failed...", "Username/Password is incorrect", false);
                    }
                } else {

                    alert.showAlertDialog(MiddleWareActivity.this, "Login failed...", "Please enter username and password", false);
                }
            }
            });
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