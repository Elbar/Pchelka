package com.kg.vista.beeserviceclient.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.activity.ChooseSubCategoryActivity;
import com.kg.vista.beeserviceclient.activity.DrawerActivity;
import com.kg.vista.beeserviceclient.classes.UserAgreement;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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

        mUserSelectCategory.setText(subcategory);

        mUserAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserAgreement(getActivity()).show();

            }
        });


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
    public void onClick(View v) {
        if (v == mUserRequestSendButton) {
            new SendData().execute();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);


    }


    public class SendData extends AsyncTask<Object, Object, String> {

        final String description = mUserRequestDesc.getText().toString().trim();
        final String cash = mUserApproxCash.getText().toString().trim();
        final String address = mUserRequestAddress.getText().toString().trim();
        final String phone_number = mUserRequestPhoneNumber.getText().toString().trim();
        final String category = mUserSelectCategory.getText().toString().trim();

        ProgressDialog pdLoading;

        @Override
        protected String doInBackground(Object... params) {
            try {
                URL url = new URL("http://176.126.167.34/get_application/");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("description", description)
                        .appendQueryParameter("price", cash)
                        .appendQueryParameter("address", address)
                        .appendQueryParameter("number", phone_number)
                        .appendQueryParameter("subcategory", category);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("Загрузка...");
            pdLoading.setIndeterminate(true);
            pdLoading.setCancelable(false);
            pdLoading.show();


        }



        private void dismissProgressDialog() {

            try {

                if (pdLoading != null && pdLoading.isShowing()) {
                    pdLoading.dismiss();
                }
            } catch (Exception e) {

                e.printStackTrace();
            } finally {
                pdLoading = null;
            }

        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            Toast.makeText(getActivity(), strJson, Toast.LENGTH_SHORT).show();

            dismissProgressDialog();


        }
    }
}
