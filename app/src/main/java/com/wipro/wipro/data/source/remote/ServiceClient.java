package com.wipro.wipro.data.source.remote;

import com.wipro.wipro.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceClient {

    // No need to instantiate this class.
    private ServiceClient() {
    }

    public static ServiceInterface getRestService(String newApiBaseUrl, int... timeOutDuration) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        if (timeOutDuration.length > 0) {
            int readTimeOut = timeOutDuration[0];
            httpClient.readTimeout(readTimeOut, TimeUnit.SECONDS);
            httpClient.connectTimeout(readTimeOut, TimeUnit.SECONDS);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(newApiBaseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ServiceInterface.class);
    }
}