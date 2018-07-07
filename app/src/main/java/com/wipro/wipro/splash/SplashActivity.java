package com.wipro.wipro.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.wipro.wipro.getfacts.FactsListActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 1500;
    private Timer mTimer;

    /**
     * Start timer when splash activity becomes visible to user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, FactsListActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    /**
     * Cancel timer when splash activity becomes in-visible to user.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}