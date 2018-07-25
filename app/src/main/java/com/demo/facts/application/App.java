package com.demo.facts.application;

import android.app.Application;

import com.demo.facts.R;
import com.demo.facts.data.source.remote.ServiceClient;
import com.demo.facts.data.source.remote.ServiceInterface;

public class App extends Application {

    private static App sInstance;
    private static ServiceInterface sServiceInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sServiceInterface = ServiceClient.getRestService(getString(R.string.BASE_API_URL));
    }

    public static App getInstance() {
        return sInstance;
    }

    public ServiceInterface getApiEndpointInterface() {
        return sServiceInterface;
    }
}

