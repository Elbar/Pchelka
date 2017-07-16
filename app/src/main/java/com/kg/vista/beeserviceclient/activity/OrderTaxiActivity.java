package com.kg.vista.beeserviceclient.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kg.vista.beeserviceclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderTaxiActivity extends AbstractActivity {
    Intent callIntent;
    @BindView(R.id.first_number)
    Button mFirstTaxiNumber;
    @BindView(R.id.second_number)
    Button mSecondTaxiNumber;
    @BindView(R.id.third_number)
    Button mThirdTaxiNumber;
    @BindView(R.id.fourth_number)
    Button mFourthTaxiNumber;
    String firstNumber = "1203";
    String secondNumber = "0772001201";
    String thirdNumber = "0558001201";
    String fourthNumber = "0707001201";


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_taxi);
        ButterKnife.bind(this);

        mFirstTaxiNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makeCall(firstNumber);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
        mSecondTaxiNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    makeCall(secondNumber);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        mThirdTaxiNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    makeCall(thirdNumber);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        mFourthTaxiNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    makeCall(fourthNumber);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        initActionBar();

    }

    public void makeCall(String phoneNumber) {

        callIntent = new Intent();
        callIntent.setAction(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(phoneListener,
                PhoneStateListener.LISTEN_CALL_STATE);
    }


    @Override
    public void onBackPressed() {
        this.finish();

    }

    private void initActionBar() {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(getResources().getString(R.string.order_taxi));
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


    private class PhoneCallListener extends PhoneStateListener {

        String LOG_TAG = "LOGGING 123";

        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);

            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                Log.i(LOG_TAG, "OFFHOOK");


                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {

                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    isPhoneCalling = false;

                }

            }
        }
    }


}
