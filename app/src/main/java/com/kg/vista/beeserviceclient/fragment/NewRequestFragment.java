package com.kg.vista.beeserviceclient.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;

import com.kg.vista.beeserviceclient.activity.DrawerActivity;
import com.kg.vista.beeserviceclient.classes.UserAgreement;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;

import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.HttpURLConnection;
import java.net.URL;


import butterknife.BindView;
import butterknife.ButterKnife;


public class NewRequestFragment extends Fragment {


    public static final int READ_TIMEOUT = 15000;


    @BindView(R.id.user_new_select_category)
    EditText mUserSelectSubCategory;

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

    @BindView(R.id.user_agreement)
    TextView mUserAgreement;


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

        mUserSelectSubCategory.setText(subcategory);

        mUserAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserAgreement(getActivity()).show();

            }
        });


        mUserRequestSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());


                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setMessage("Отправка");
                mProgressDialog.show();


                String user_select_subcategory = mUserSelectSubCategory.getText().toString();
                String user_request_desc = mUserRequestDesc.getText().toString();
                String user_approx_cash = mUserApproxCash.getText().toString();
                String user_request_address = mUserRequestAddress.getText().toString();
                String user_request_phone_number = mUserRequestPhoneNumber.getText().toString();


                if (user_select_subcategory.trim().length() > 0 && user_request_desc.trim().length() > 0 && user_approx_cash.trim().length() > 0 && user_request_address.trim().length() > 0 && user_request_phone_number.trim().length() > 0) {
                    if (user_request_desc.trim().length() > 0) {


                        new PostDataTask().execute(user_select_subcategory, user_request_desc, user_approx_cash, user_request_address, user_request_phone_number);


                        mProgressDialog.hide();


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

    private class PostDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog pdLoading = new ProgressDialog(getContext());

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";


        protected String doInBackground(String... params) {

            try {

                String subcategory = params[0];
                String description = params[1];
                String price = params[2];
                String address = params[3];
                String number = params[4];


                URL url = new URL("http://176.126.167.34/get_application/?subcategory=" + subcategory + "&description=" + description + "&price=" + price + "&address=" + address + "&number=" + number);


                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(READ_TIMEOUT);

                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("Загрузка...");
            pdLoading.setIndeterminate(true);
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            if (pdLoading != null && pdLoading.isShowing()) {
                pdLoading.dismiss();
            }
            JSONObject dataJsonObj = null;


            try {

                dataJsonObj = new JSONObject(strJson);
                String resultJson = dataJsonObj.getString("result");


                if (resultJson.equals("true")) {

                    alert.showAlertDialog(getContext(), "", "Данные успешно отправлены", false);




                } else {
                    alert.showAlertDialog(getContext(), "...", "Ошибка", false);
                    pdLoading.dismiss();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}





