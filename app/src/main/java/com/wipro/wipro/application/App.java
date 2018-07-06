package com.wipro.wipro.application;

import android.app.Application;

import com.wipro.wipro.R;
import com.wipro.wipro.data.source.remote.ServiceClient;
import com.wipro.wipro.data.source.remote.ServiceInterface;

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

