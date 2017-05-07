package com.kg.vista.beeserviceclient.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.adapter.UsersAdapter;
import com.kg.vista.beeserviceclient.model.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kg.vista.beeserviceclient.R.id.category_name_tv;


public class ChooseCategoryActivity extends AppCompatActivity {

    @BindView(R.id.category_lv)
    ListView mCategoryListView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        ButterKnife.bind(this);

        initActionBar();


        ArrayList<Category> arrayOfCategories = new ArrayList<Category>();
// Create the adapter to convert the array to views
        UsersAdapter adapter = new UsersAdapter(this, arrayOfCategories);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.category_lv);
        listView.setAdapter(adapter);

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

    public class CategoryTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        ProgressDialog pdLoading = new ProgressDialog(ChooseCategoryActivity.this);


        @Override
        protected String doInBackground(Void... params) {


            try {

                URL url = new URL("http://176.126.167.34/api/v1/application/");


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


        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            List<Category> data = new ArrayList<>();

            JSONObject dataJsonObj = null;
            ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;


            try {

                dataJsonObj = new JSONObject(strJson);
                JSONArray result = dataJsonObj.getJSONArray("objects");

                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = (JSONObject) result.get(i);
                    String category_name = json_data.getString("name");

                    map = new HashMap<String, String>();
                    map.put("name", category_name);
                    myArrList.add(map);


//                    session.createLoginSession(user_phone, user_pass);

                }
                Toast.makeText(ChooseCategoryActivity.this, myArrList.toString(), Toast.LENGTH_SHORT).show();


                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), myArrList, android.R.layout.simple_list_item_2,
                        new String[]{"Name"},
                        new int[]{category_name_tv});
                mCategoryListView.setAdapter(adapter);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}


