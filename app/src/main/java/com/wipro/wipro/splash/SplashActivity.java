package com.wipro.wipro.splash;

import android.support.v7.app.AppCompatActivity;

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
                //Launch activity after splash and finish
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