package com.kg.vista.beeserviceclient.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.adapter.CustomListAdapter;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseCategoryActivity extends AbstractActivity {

    final AlertDialogManager alert = new AlertDialogManager();
    @BindView(R.id.category_lv)
    ListView mCategoryListView;
    Integer[] imageArray = {
            R.drawable.group1,
            R.drawable.group2,
            R.drawable.group3,
            R.drawable.group4,
            R.drawable.group5,
            R.drawable.group6,
            R.drawable.group7,
            R.drawable.group8,
            R.drawable.group9,
            R.drawable.group10,
            R.drawable.group11,
            R.drawable.group12};

    String[] categoryArray = {
            "Мастер на вызов",
            "Красота и здоровье",
            "Клининг и помощь по хозяйству",
            "Установка и ремонт оборудования",
            "Образование и обучение",
            "Перевозки",
            "Служба доставки",
            "Компьютер и сети",
            "Юридическая помощь",
            "Техническая помощь",
            "Виртуальный помощник",
            "Отдых, Досуг и прочее"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        ButterKnife.bind(this);

        initActionBar();


        CustomListAdapter arrayAdapter = new CustomListAdapter(this, categoryArray, imageArray);

        mCategoryListView.setAdapter(arrayAdapter);

        mCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String selectedCategory = categoryArray[position];
                Intent i = new Intent(getApplicationContext(), ChooseSubCategoryActivity.class);
                i.putExtra("category", selectedCategory);

                startActivity(i);
            }
        });
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
                return true;
            case R.id.order_taxi:
                Intent orderIntent = new Intent(this, OrderTaxiActivity.class);
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(orderIntent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            ab.setCustomView(R.layout.category_actionbar);
        }
    }

    public class CategoryTask extends AsyncTask<Void, Void, String> implements OnItemSelectedListener {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        ProgressDialog pdLoading = new ProgressDialog(ChooseCategoryActivity.this);


        @Override
        protected String doInBackground(Void... params) {


            try {

                URL url = new URL("http://176.126.167.34/api/v1/category/");

                urlConnection = (HttpURLConnection) url.openConnection();
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

            dismissProgressDialog();

            JSONObject dataJsonObj;

            try {

                dataJsonObj = new JSONObject(strJson);
                JSONArray result = dataJsonObj.getJSONArray("objects");
                List<String> categories = new ArrayList<>();


                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = (JSONObject) result.get(i);


                    String cat_name = json_data.getString("name");

                    categories.add(cat_name);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}


