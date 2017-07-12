package com.kg.vista.beeserviceclient.activity;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.classes.UserAgreement;
import com.kg.vista.beeserviceclient.db.SampleSQLiteDBHelper;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AbstractActivity {


    private static final int READ_TIMEOUT = 10000;

    @BindView(R.id.user_new_select_category)
    EditText mUserSelectSubCategory;

    @BindView(R.id.user_new_request_desc)
    EditText mUserRequestDesc;


    @BindView(R.id.user_new_approx_cash)
    EditText mUsercash;

    @BindView(R.id.user_request_address)
    EditText mUserRequestAddress;

    @BindView(R.id.user_new_request_phone_number)
    EditText mUserRequestPhoneNumber;

    @BindView(R.id.user_new_request_send_button)
    Button mUserRequestSendButton;

    @BindView(R.id.user_agreement)
    TextView mUserAgreement;


    @BindView(R.id.fragment_new_request_checkbox)
    CheckBox mNewRequestCheckbox;
    
    String selectedSubcategory;
    String desc;
    String cash;
    String address;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);


        initActionBar();



        final AlertDialogManager alert = new AlertDialogManager();


        Intent i = getIntent();
        final String subcategory = i.getStringExtra("subcategory");

        mUserSelectSubCategory.setText(subcategory);


        mUserRequestSendButton.setBackgroundResource(R.drawable.btn_style_gray);
        mUserRequestSendButton.setEnabled(false);

        mNewRequestCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNewRequestCheckbox.isChecked()) {

                    mUserRequestSendButton.setBackgroundResource(R.drawable.btn_style_primary);
                    mUserRequestSendButton.setEnabled(true);

                } else {

                    mUserRequestSendButton.setBackgroundResource(R.drawable.btn_style_gray);
                    mUserRequestSendButton.setEnabled(false);

                }
            }
        });


        mUserAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserAgreement(DrawerActivity.this).show();

            }
        });


        mUserRequestSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectedSubcategory = mUserSelectSubCategory.getText().toString();
                desc = mUserRequestDesc.getText().toString();
                cash = mUsercash.getText().toString();
                address = mUserRequestAddress.getText().toString();
                phone = mUserRequestPhoneNumber.getText().toString();


                String phoneNumberCode = phone.substring(0, 3);


                if (isNetworkAvailable()) {

                    final ProgressDialog mProgressDialog = new ProgressDialog(DrawerActivity.this);


                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.setMessage("Отправка");
                    mProgressDialog.show();


                    if (selectedSubcategory.trim().length() > 0 && desc.trim().length() > 0
                            && address.trim().length() > 0
                            && phone.trim().length() > 0) {

                        if (cash.equals("")) {
                            cash = "0";
                        }

                        if ((phoneNumberCode.equals("070") || phoneNumberCode.equals("055") || phoneNumberCode.equals("077")) && phone.trim().length() == 10) {

                            new PostDataTask().execute(selectedSubcategory, desc, cash, address, phone);


                        } else {
                            alert.showAlertDialog(DrawerActivity.this, "Ошибка", "Номер указан не верно", false);

                            Toast.makeText(DrawerActivity.this, "Номер должен быть в полном формате. Например: 0709107091", Toast.LENGTH_LONG).show();

                        }
                        mProgressDialog.hide();

                    } else {

                        alert.showAlertDialog(DrawerActivity.this, "Ошибка ", "Пожалуйста заполните все поля", false);
                        mProgressDialog.hide();

                    }

                } else {
                    alert.showAlertDialog(DrawerActivity.this, "Ошибка ", "Пожалуйста проверьте ваше соединение с интернетом", false);
                }


            }


        });
    }

    public void chooseCategory(View view) {

        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        AlertDialogManager alertDialogManager = new AlertDialogManager();

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {

            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            startActivity(intent);
        }else {
            alertDialogManager.showAlertDialog(this, "Ошибка", "Пожалуйста проверьте ваше соединение с интернетом", false);
        }

    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            ab.setCustomView(R.layout.actionbar);

        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();




        return   activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_request:
                Intent second_intent = new Intent(this, MyRequestActivity.class);
                second_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(second_intent);
                return true;
            case R.id.about:
                Intent i = new Intent(this, AboutActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void saveToDB() {

        SQLiteDatabase database = new SampleSQLiteDBHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();

        String selectedSubcategory = mUserSelectSubCategory.getText().toString();
        String desc = mUserRequestDesc.getText().toString();
        String cash = mUsercash.getText().toString();
        String requestAddress = mUserRequestAddress.getText().toString();
        String phoneNumber = mUserRequestPhoneNumber.getText().toString();

        values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_SUBCATEGORY, selectedSubcategory);
        values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_DESC, desc);
        values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_CASH, cash);
        values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_ADDRESS, requestAddress);
        values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_PHONE, phoneNumber);


        long newRowId = database.insert(SampleSQLiteDBHelper.REQUEST_TABLE_NAME, null, values);

    }




    private class PostDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog pdLoading = new ProgressDialog(DrawerActivity.this);

        HttpURLConnection urlConnection = null;
        String subcategory;
        String description;
        String price;
        String address;
        String number;

        BufferedReader reader = null;
        String resultJson = "";

        AlertDialogManager postErrorAlert = new AlertDialogManager();

        protected String doInBackground(String... params) {

            try {

                subcategory = URLEncoder.encode(params[0].trim(), "UTF-8");
                description = URLEncoder.encode(params[1].trim(), "UTF-8");
                price = URLEncoder.encode(params[2].trim(), "UTF-8");
                address = URLEncoder.encode(params[3].trim(), "UTF-8");
                number = URLEncoder.encode(params[4].trim(), "UTF-8");


                URL url = new URL("http://176.126.167.34/get_application/?subcategory=" + subcategory + "&description=" + description + "&price=" + price + "&address=" + address + "&number=" + number);


                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(READ_TIMEOUT);

                urlConnection.setRequestMethod("GET");
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
                    saveToDB();

                    postErrorAlert.showAlertDialog(DrawerActivity.this, "Заявка принята", "Ждите звонка исполнителя", false);
                    Toast.makeText(DrawerActivity.this, "Ваша заявка перемещена в Мои заявки", Toast.LENGTH_LONG).show();


                    mUserSelectSubCategory.setText("");
                    mUserRequestDesc.setText("");
                    mUsercash.setText("");
                    mUserRequestAddress.setText("");
                    mUserRequestPhoneNumber.setText("");





                } else {
                    postErrorAlert.showAlertDialog(DrawerActivity.this, "...", "Ошибка при отправлении заявки", false);

                    pdLoading.dismiss();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}