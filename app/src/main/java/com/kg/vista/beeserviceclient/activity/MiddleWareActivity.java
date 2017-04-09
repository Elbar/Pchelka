package com.kg.vista.beeserviceclient.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;
import com.kg.vista.beeserviceclient.manager.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


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

                final ProgressDialog mProgressDialog = new ProgressDialog(MiddleWareActivity.this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true );
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setMessage("Загрузка");
                mProgressDialog.show();


                String username = mUserName.getText().toString();
                String user_pass = mVerificationCode.getText().toString();




                if (username.trim().length() > 0 && user_pass.trim().length() > 0) {
                    if (user_pass.equals("test")) {

                        session.createLoginSession(username, "beeservice");

                        Intent i = new Intent(MiddleWareActivity.this, DrawerActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        alert.showAlertDialog(MiddleWareActivity.this, "...", "Введеный вами код не действителен", false);
                        mProgressDialog.hide();
                    }
                } else {

                    alert.showAlertDialog(MiddleWareActivity.this, "Ошибка регистрации", "Пожалуйста введите имя и код", false);
                    mProgressDialog.hide();

                }
            }
            });
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }






}