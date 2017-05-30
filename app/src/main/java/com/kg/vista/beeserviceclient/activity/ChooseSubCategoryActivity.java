package com.kg.vista.beeserviceclient.activity;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.network.NetworkState;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseSubCategoryActivity extends AbstractActivity {

    @BindView(R.id.subcategory_lv)
    ListView mSubCategoryListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subcategory);

        ButterKnife.bind(this);

        initActionBar();


        Intent i = getIntent();
        String categoryName = i.getStringExtra("category");
        new SubCategoryTask().execute(categoryName);

        mSubCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubCategory = ((TextView) view).getText().toString();

                Intent i = new Intent(ChooseSubCategoryActivity.this, DrawerActivity.class);
                i.putExtra("subcategory", selectedSubCategory);
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
            ab.setTitle(getResources().getString(R.string.choose_subcategory_toolbar_title));
        }
    }

    public class SubCategoryTask extends AsyncTask<String, Void, String> implements AdapterView.OnItemSelectedListener {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        ProgressDialog pdLoading = new ProgressDialog(ChooseSubCategoryActivity.this);
        String categoryName;


        @Override
        protected String doInBackground(String... params) {

            categoryName = params[0];


            try {

                URL url = new URL("http://176.126.167.34/api/v1/subcategory/");


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
                List<String> subcategories = new ArrayList<>();

                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = (JSONObject) result.get(i);
                    JSONObject parentCategory = json_data.getJSONObject("parentcategory");
                    String parentCategoryName = parentCategory.getString("name");


                    if (categoryName.equals(parentCategoryName)) {
                        subcategories.add(json_data.getString("name"));
                    }

                }

                HashSet<String> hashSet = new HashSet<>();
                hashSet.addAll(subcategories);
                subcategories.clear();
                subcategories.addAll(hashSet);


                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<>(ChooseSubCategoryActivity.this, android.R.layout.simple_list_item_1, subcategories);
                mSubCategoryListView.setAdapter(arrayAdapter);


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


