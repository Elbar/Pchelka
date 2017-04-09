package com.kg.vista.beeserviceclient.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.activity.SearchExecutorActivity;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vista on 05.02.2017.
 */

public class NewRequestFragment extends Fragment{


    @BindView(R.id.user_new_select_category)
    EditText mUserSelectCategory;

    @BindView(R.id.user_new_request_desc)
    EditText mUserRequestDesc;


    @BindView(R.id.user_new_approx_cash)
    EditText mUserApproxCash;

    @BindView(R.id.user_request_address)
    EditText mUserRequestAddress;

    @BindView(R.id.user_new_request_phone_number)
    EditText mUserRequestPhoneNumber;


    @BindView(R.id.user_new_request_send_button)
    Button mUserRequestSendButton;


    AlertDialogManager alert = new AlertDialogManager();


    public NewRequestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_request, container, false);

        ButterKnife.bind(this, view);



        mUserRequestSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true );
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setMessage("Загрузка");
                mProgressDialog.show();


                String user_select_category = mUserSelectCategory.getText().toString();
                String user_request_desc = mUserRequestDesc.getText().toString();
                String user_approx_cash = mUserApproxCash.getText().toString();
                String user_request_address = mUserRequestAddress.getText().toString();
                String user_request_phone_number = mUserRequestPhoneNumber.getText().toString();




                if (user_request_desc.trim().length() > 0 && user_approx_cash.trim().length() > 0 && user_request_address.trim().length() >0 && user_request_phone_number.trim().length()>0) {
                    if (user_request_desc.trim().length()>0) {

                        Intent intent = new Intent(getActivity(), SearchExecutorActivity.class);
                        intent.putExtra("user_request_desc", user_request_desc);
                        intent.putExtra("user_approx_cash", user_approx_cash);
                        intent.putExtra("user_request_address", user_request_address);
                        intent.putExtra("user_request_phone_number", user_request_phone_number);

                        startActivity(intent);
                        mProgressDialog.dismiss();

                    } else {
                        alert.showAlertDialog(getActivity(), "...", "Заполните все поля", false);
                        mProgressDialog.hide();
                    }
                } else {

                    alert.showAlertDialog(getActivity(), "Ошибка ", "Пожалуйста заполните все поля", false);
                    mProgressDialog.hide();

                }

            }
        });

        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);




    }
}