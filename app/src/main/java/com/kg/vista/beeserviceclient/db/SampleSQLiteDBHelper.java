package com.kg.vista.beeserviceclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.activity.RequestDescActivity;
import com.kg.vista.beeserviceclient.adapter.RequestAdapter;
import com.kg.vista.beeserviceclient.model.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by root on 6/21/17.
 */

public class SampleSQLiteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "requests_database";

    public static final String REQUEST_TABLE_NAME = "request";

    public static final String REQUEST_COLUMN_ID = "_id";

    public static final String REQUEST_COLUMN_SUBCATEGORY = "subcategory_name";

    public static final String REQUEST_COLUMN_DESC = "desc";

    public static final String REQUEST_COLUMN_CASH = "approx_cash";

    public static final String REQUEST_COLUMN_ADDRESS = "address";

    public static final String REQUEST_COLUMN_PHONE = "phone";

    public SampleSQLiteDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + REQUEST_TABLE_NAME + " (" +

                REQUEST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                REQUEST_COLUMN_SUBCATEGORY + " TEXT, " +

                REQUEST_COLUMN_DESC + " TEXT, " +

                REQUEST_COLUMN_CASH + " INT UNSIGNED, " +

                REQUEST_COLUMN_ADDRESS + " TEXT, " +

                REQUEST_COLUMN_PHONE + " TEXT" + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME);

        onCreate(db);


    }


    public static class DataBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        SampleSQLiteDBHelper helper;

        List<Request> data = Collections.emptyList();

        int currentPos = 0;
        private Context context;
        private LayoutInflater inflater;
        private CardView mCVDescription;

        public DataBaseAdapter(Context context, List<Request> data) {
            helper = new SampleSQLiteDBHelper(context);
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        public DataBaseAdapter(Context context) {
            this.context = context;

        }

        public void insertData(String selectedSubcategory, String desc, String approxCash, String requestAddress, String phoneNumber) {

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_SUBCATEGORY, selectedSubcategory);
            values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_DESC, desc);
            values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_CASH, approxCash);
            values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_ADDRESS, requestAddress);
            values.put(SampleSQLiteDBHelper.REQUEST_COLUMN_PHONE, phoneNumber);
            long id = db.insert(SampleSQLiteDBHelper.REQUEST_TABLE_NAME, null, values);

            Toast.makeText(context, values.toString(), Toast.LENGTH_SHORT).show();

        }

        public String getData(String id) {
            //select _id,Name,Card,Code
            SQLiteDatabase db = helper.getWritableDatabase();
            String[] columns = {SampleSQLiteDBHelper.REQUEST_COLUMN_SUBCATEGORY, SampleSQLiteDBHelper.REQUEST_COLUMN_DESC, SampleSQLiteDBHelper.REQUEST_COLUMN_CASH};
            Cursor cursor = db.query(SampleSQLiteDBHelper.REQUEST_TABLE_NAME, columns, SampleSQLiteDBHelper.REQUEST_COLUMN_ID + " = '" + id + "'", null, null, null, null);
            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                int index2 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_SUBCATEGORY);
                int index3 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_DESC);
                int index4 = cursor.getColumnIndex(SampleSQLiteDBHelper.REQUEST_COLUMN_CASH);

                String subcategory = cursor.getString(index2);
                String desc = cursor.getString(index3);
                String cash = cursor.getString(index4);
                buffer.append(subcategory + " " + desc + " " + cash + "\n");

            }
            return buffer.toString();
        }

        public List<Request> getAllData() {
            List<Request> list = new ArrayList<>();
            SQLiteDatabase db = helper.getWritableDatabase();
            String[] columns = {SampleSQLiteDBHelper.REQUEST_COLUMN_ID, SampleSQLiteDBHelper.REQUEST_COLUMN_SUBCATEGORY, SampleSQLiteDBHelper.REQUEST_COLUMN_DESC, SampleSQLiteDBHelper.REQUEST_COLUMN_CASH, REQUEST_COLUMN_ADDRESS, REQUEST_COLUMN_PHONE};

            Cursor cursor = db.query(SampleSQLiteDBHelper.REQUEST_TABLE_NAME, columns, null, null, null, null, null);
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
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

                list.add(request);

            }
            return list;


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.container_request, parent, false);
            MyHolder holder = new MyHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


            final MyHolder myHolder = (MyHolder) holder;

            final Request current = data.get(position);


            myHolder.requestId.setText("Заявка: #" + current.requestId);
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
                long time = format.parse(current.requestOrderTime).getTime();
                long now = System.currentTimeMillis();
                CharSequence ago =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
                myHolder.requestOrderTime.setText(ago);


            } catch (Exception e) {
                e.printStackTrace();
            }


            myHolder.mCVDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), RequestDescActivity.class);

                    intent.putExtra("id", current.requestId);
                    intent.putExtra("phone_number", current.requestPhoneNumber);
                    intent.putExtra("price", current.requestPrice);
                    intent.putExtra("desc", current.requestDescription);


                    v.getContext().startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class MyHolder extends RecyclerView.ViewHolder {

            TextView requestId;
            TextView requestOrderTime;

            CardView mCVDesc;

            public MyHolder(View itemView) {
                super(itemView);
                requestId = (TextView) itemView.findViewById(R.id.request_id);

                mCVDesc = (CardView) itemView.findViewById(R.id.request_cv);


            }


        }
    }
}

