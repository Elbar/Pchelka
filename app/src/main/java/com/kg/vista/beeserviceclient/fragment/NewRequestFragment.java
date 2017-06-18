package com.kg.vista.beeserviceclient.fragment;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kg.vista.beeserviceclient.R;

import com.kg.vista.beeserviceclient.activity.DrawerActivity;
import com.kg.vista.beeserviceclient.classes.UserAgreement;
import com.kg.vista.beeserviceclient.db.DBHelper;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;

import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;


import butterknife.BindView;
import butterknife.ButterKnife;


public class NewRequestFragment extends Fragment {

    public static final int READ_TIMEOUT = 15000;
    DBHelper dbHelper;
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


    @BindView(R.id.fragment_new_request_scrollview)
    ScrollView mNewRequestSV;
    @BindView(R.id.fragment_new_request)
    RelativeLayout mNewRequestRL;


    public NewRequestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_new_request, container, false);

        ButterKnife.bind(this, view);

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        final AlertDialogManager alert = new AlertDialogManager();


        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        Intent i = getActivity().getIntent();
        final String subcategory = i.getStringExtra("subcategory");

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

                    if (isConnected) {

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


                        String selectedSubcategory = mUserSelectSubCategory.getText().toString();
                        String desc = mUserRequestDesc.getText().toString();
                        String approxCash = mUserApproxCash.getText().toString();
                        String requestAddress = mUserRequestAddress.getText().toString();
                        String phoneNumber = mUserRequestPhoneNumber.getText().toString();


                        if (selectedSubcategory.trim().length() > 0 && desc.trim().length() > 0
                                && approxCash.trim().length() > 0 && requestAddress.trim().length() > 0
                                && phoneNumber.trim().length() > 0) {

                            new PostDataTask().execute(selectedSubcategory, desc, approxCash, requestAddress, phoneNumber);

                            mProgressDialog.hide();
                        } else {

                            alert.showAlertDialog(getActivity(), "Ошибка ", "Пожалуйста заполните все поля", false);
                            mProgressDialog.hide();

                        }

                    } else {
                        alert.showAlertDialog(getActivity(), "Ошибка ", "Пожалуйста проверьте ваше соединение с интернетом", false);
                    }
                }
            });



//        mOrderTaxi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                mNewRequestRL.removeAllViews();
//                Button callButton = new Button(getActivity());
//                EditText address = new EditText(getActivity());
//                EditText phone = new EditText(getActivity());
//                LinearLayout ll = new LinearLayout(getActivity());
//                ll.setOrientation(LinearLayout.VERTICAL);
//


//                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
////                buttonLayoutParams.setMargins(50,10,50,10);
//                callButton.setLayoutParams(buttonLayoutParams);
//                callButton.setText("Отправить");
//
//
//                final String address_str = address.getText().toString();
//                final String phone_str = phone.getText().toString();
//
//
//
//
//
//
//                ll.addView(address);
//                ll.addView(phone);
//                ll.addView(callButton);
//
//                mNewRequestRL.addView(ll);
//
//                callButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
//                        mNewRequestRL.refreshDrawableState();
//
////                        new PostTaxiOrder().execute(address_str, phone_str);
//                    }
//                });
//


//
//                editText1.setHint("Адрес");
//
//                mNewRequestRL.addView(callButton);
//
//                mNewRequestRL.addView(editText);
//                mNewRequestRL.addView(editText1);


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
        int statusCode;
        AlertDialogManager postErrorAlert = new AlertDialogManager();

        protected String doInBackground(String... params) {

            try {

                String subcategory = URLEncoder.encode(params[0].trim(), "UTF-8");
                String description = URLEncoder.encode(params[1].trim(), "UTF-8");
                String price = URLEncoder.encode(params[2].trim(), "UTF-8");
                String address = URLEncoder.encode(params[3].trim(), "UTF-8");
                String number = URLEncoder.encode(params[4].trim(), "UTF-8");


                URL url = new URL("http://1203.kg/get_application/?subcategory=" + subcategory + "&description=" + description + "&price=" + price + "&address=" + address + "&number=" + number);


                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(READ_TIMEOUT);

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                statusCode = urlConnection.getResponseCode();

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

                if (resultJson.equals("true") && statusCode == 200) {

                    postErrorAlert.showAlertDialog(getContext(), "Заявка принята", "Ждите звонка исполнителя", false);
                    mUserSelectSubCategory.setText("");
                    mUserRequestDesc.setText("");
                    mUserApproxCash.setText("");
                    mUserRequestAddress.setText("");
                    mUserRequestPhoneNumber.setText("");

                } else {
                    postErrorAlert.showAlertDialog(getContext(), "...", "Ошибка", false);

                    pdLoading.dismiss();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}






