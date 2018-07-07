package com.wipro.wipro.application;

import android.app.Application;

import com.wipro.wipro.data.source.remote.ServiceClient;
import com.wipro.wipro.data.source.remote.ServiceInterface;

public class App extends Application {

    private static App sInstance;
    private static ServiceInterface sServiceInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sServiceInterface = ServiceClient.getRestService("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json/");
    }

    public static App getInstance() {
        return sInstance;
    }

    public ServiceInterface getApiEndpointInterface() {
        return sServiceInterface;
    }
}
