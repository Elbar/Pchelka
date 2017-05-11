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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.activity.DrawerActivity;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vista on 05.02.2017.
 */

public class NewRequestFragment extends Fragment implements View.OnClickListener {


    public static final String KEY_DESC = "description";
    public static final String KEY_CAT = "category";
    public static final String KEY_CASH = "price";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "number";


    private static final String REQUEST_URL = "http://176.126.167.34/get_application/";

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

        Intent i = getActivity().getIntent();
        String subcategory = i.getStringExtra("subcategory");

        mUserSelectCategory.setText(subcategory);



        mUserRequestSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setMessage("Загрузка");
                mProgressDialog.show();


                String user_select_category = mUserSelectCategory.getText().toString();
                String user_request_desc = mUserRequestDesc.getText().toString();
                String user_approx_cash = mUserApproxCash.getText().toString();
                String user_request_address = mUserRequestAddress.getText().toString();
                String user_request_phone_number = mUserRequestPhoneNumber.getText().toString();


                if (user_request_desc.trim().length() > 0 && user_approx_cash.trim().length() > 0 && user_request_address.trim().length() > 0 && user_request_phone_number.trim().length() > 0) {
                    if (user_request_desc.trim().length() > 0) {
//
//                        Intent intent = new Intent(getActivity(), MyRequestFragment.class);
//                        intent.putExtra("user_request_desc", user_request_desc);
//                        intent.putExtra("user_approx_cash", user_approx_cash);
//                        intent.putExtra("user_request_address", user_request_address);
//                        intent.putExtra("user_request_phone_number", user_request_phone_number);
//
//                        startActivity(intent);
//                        mProgressDialog.dismiss();
                        sendData();

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

    private void sendData() {
        final String desc = mUserRequestDesc.getText().toString().trim();
        final String cash = mUserApproxCash.getText().toString().trim();
        final String address = mUserRequestAddress.getText().toString().trim();
        final String phone_number = mUserRequestPhoneNumber.getText().toString().trim();
        final String category = "Мастер на вызов";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUEST_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_DESC, desc);
                params.put(KEY_CASH, cash);
                params.put(KEY_ADDRESS, address);
                params.put(KEY_PHONE, phone_number);
                params.put(KEY_CAT, category);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == mUserRequestSendButton) {
            sendData();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);


    }
}