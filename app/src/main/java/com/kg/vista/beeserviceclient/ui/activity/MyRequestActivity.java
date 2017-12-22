package com.kg.vista.beeserviceclient.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.adapter.AdapterRequest;
import com.kg.vista.beeserviceclient.db.SampleSQLiteDBHelper;
import com.kg.vista.beeserviceclient.model.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/9/17.
 */

public class MyRequestActivity extends AbstractActivity {
    private RecyclerView mRVRequest;
    private AdapterRequest mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_request_activity);

        initActionBar();

        try {
            List<Request> requestList = new ArrayList<Request>();
            String query = "SELECT * FROM " + SampleSQLiteDBHelper.REQUEST_TABLE_NAME;
            SQLiteDatabase database = new SampleSQLiteDBHelper(this).getWritableDatabase();

            Cursor cursor = database.rawQuery(query, null);


            if (cursor.moveToFirst()) {

                do {

                    int index1 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_ID);

                    int index2 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_SUBCATEGORY);
                    int index3 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_DESC);
                    int index4 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_CASH);
                    int index5 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_ADDRESS);
                    int index6 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_PHONE);


                    int requestID = cursor.getInt(index1);
                    String subcategory = cursor.getString(index2);
                    String desc = cursor.getString(index3);
                    String cash = cursor.getString(index4);
                    String address = cursor.getString(index5);
                    String phone = cursor.getString(index6);

                    Request request = new Request(requestID, subcategory, desc, cash, address, phone);

                    requestList.add(request);
                } while (cursor.moveToNext());
            }
            cursor.close();

            if (requestList.isEmpty()) {
                Toast.makeText(this, "У вас пока нет отправленных заявок", Toast.LENGTH_SHORT).show();
            } else {

                mRVRequest = (RecyclerView) findViewById(R.id.my_recycler_view);
                mAdapter = new AdapterRequest(MyRequestActivity.this, requestList);
                mRVRequest.setLayoutManager(new LinearLayoutManager(MyRequestActivity.this));

                mRVRequest.setAdapter(mAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(getResources().getString(R.string.my_request));
            ab.setDisplayHomeAsUpEnabled(true);

        }
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


}

