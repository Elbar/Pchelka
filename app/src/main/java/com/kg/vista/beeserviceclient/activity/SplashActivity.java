package com.kg.vista.beeserviceclient.activity;

import android.content.Intent;
import android.os.Bundle;

import com.kg.vista.beeserviceclient.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fen on 07.05.2017.
 */

public class SplashActivity extends AbstractActivity {
    private static final long DELAY = 3000;
    private boolean scheduled = false;
    private Timer splashTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashTimer = new Timer();
        splashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SplashActivity.this.finish();
                startActivity(new Intent(SplashActivity.this, DrawerActivity.class));
            }
        }, DELAY);
        scheduled = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scheduled)
            splashTimer.cancel();
        splashTimer.purge();
    }
}