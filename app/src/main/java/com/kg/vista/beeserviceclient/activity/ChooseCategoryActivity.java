package com.kg.vista.beeserviceclient.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.manager.AlertDialogManager;
import com.kg.vista.beeserviceclient.network.NetworkState;

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

    @BindView(R.id.category_lv)
    ListView mCategoryListView;
    final AlertDialogManager alert = new AlertDialogManager();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        ButterKnife.bind(this);

        initActionBar();

        NetworkState networkState = new NetworkState(this);
        if (networkState.checkInternetConnection()) {
            Log.d("Internet", "Success");
        } else {

            this.finish();

        }

        new CategoryTask().execute();
        mCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = ((TextView) view).getText().toString();

                Intent i = new Intent(getApplicationContext(), ChooseSubCategoryActivity.class);
                i.putExtra("category", selectedCategory);
                startActivity(i);


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getResources().getString(R.string.choose_category_toolbar_title));
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


                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(ChooseCategoryActivity.this, android.R.layout.simple_list_item_1, categories);
                mCategoryListView.setAdapter(arrayAdapter);


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


